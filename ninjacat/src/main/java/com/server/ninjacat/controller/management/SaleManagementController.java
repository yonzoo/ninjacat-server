package com.server.ninjacat.controller.management;

import com.server.ninjacat.controller.SaleRequest;
import com.server.ninjacat.controller.SaleResponse;
import com.server.ninjacat.data.model.things.Sale;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.service.things.SaleService;
import com.server.ninjacat.data.service.things.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@RestController
@RequestMapping("management/api/v1/sales")
public class SaleManagementController {

  private final SaleService saleService;
  private final WarehouseService warehouseService;

  @Autowired
  public SaleManagementController(SaleService saleService, WarehouseService warehouseService) {
    this.saleService = saleService;
    this.warehouseService = warehouseService;
  }

  @PostMapping("/add")
  @PreAuthorize("hasAuthority('sale:write')")
  public SaleResponse addNewSale(@NotNull @RequestBody SaleRequest saleRequest) {
    Warehouse warehouse = warehouseService.loadWarehouseByName(saleRequest.getName());
    int reqQuantity = saleRequest.getQuantity();
    int newQuantity = warehouse.getQuantity() - reqQuantity;
    if (newQuantity < 0) {
      newQuantity = 0;
      reqQuantity = warehouse.getQuantity();
    }
    Sale addedSale = saleService.addSale(new Sale(
        reqQuantity,
        saleRequest.getAmount(),
        new Date(),
        warehouse)
    );
    warehouse.setQuantity(newQuantity);
    warehouseService.updateWarehouseById(warehouse.getId(), warehouse);
    return new SaleResponse(
        addedSale.getId(),
        addedSale.getWarehouse().getName(),
        addedSale.getQuantity(),
        addedSale.getAmount(),
        addedSale.getSaleDate().toInstant().toString()
    );
  }

  @DeleteMapping(path = "/delete/{id}")
  @PreAuthorize("hasAuthority('sale:write')")
  public MessageResponse deleteSale(@NotNull @PathVariable("id") Long saleId) {
    saleService.deleteSaleById(saleId);
    return new MessageResponse("Sale was successfully deleted!");
  }

  @PutMapping(path = "/edit/{id}")
  @PreAuthorize("hasAuthority('sale:write')")
  public SaleResponse updateSale(@NotNull @PathVariable("id") Long saleId,
                                 @RequestBody SaleRequest saleRequest) {
    Warehouse warehouse = warehouseService.loadWarehouseByName(saleRequest.getName());
    Sale sale = new Sale(
        saleRequest.getQuantity(),
        saleRequest.getAmount(),
        saleRequest.getDate(),
        warehouse
    );
    Sale oldSale = saleService.loadSaleById(saleId);
    Sale updatedSale = saleService.updateSaleById(saleId, sale);
    Warehouse w = warehouseService.loadWarehouseById(updatedSale.getWarehouse().getId());
    w.setQuantity(w.getQuantity() + oldSale.getQuantity() - updatedSale.getQuantity());
    warehouseService.updateWarehouseById(w.getId(), w);
    return new SaleResponse(
        updatedSale.getId(),
        updatedSale.getWarehouse().getName(),
        updatedSale.getQuantity(),
        updatedSale.getAmount(),
        updatedSale.getSaleDate().toInstant().toString());
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No sales were found")
  public static class NoSalesException extends RuntimeException {
  }
}
