package com.server.ninjacat.controller.client;

import com.server.ninjacat.controller.SaleResponse;
import com.server.ninjacat.data.model.things.Sale;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.service.things.SaleService;
import com.server.ninjacat.data.service.things.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sales")
public class SaleController {

  private final SaleService saleService;
  private final WarehouseService warehouseService;

  @Autowired
  public SaleController(WarehouseService warehouseService,
                        SaleService saleService) {
    this.warehouseService = warehouseService;
    this.saleService = saleService;
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('sale:read')")
  public List<SaleResponse> getAllSales() {
    return saleService.loadAllSales().stream().map(sale ->
        new SaleResponse(
            sale.getId(),
            sale.getWarehouse().getName(),
            sale.getQuantity(),
            sale.getAmount(),
            sale.getSaleDate().toInstant().toString())
    ).collect(Collectors.toList());
  }

  @GetMapping(path = "{saleId}")
  @PreAuthorize("hasAuthority('sale:read')")
  public Sale getSaleById(@NotNull @PathVariable("saleId") Long saleId) {
    return saleService.loadSaleById(saleId);
  }

  @GetMapping(path = "{warehouseId}")
  @PreAuthorize("hasAuthority('sale:read')")
  public List<Sale> getSalesByWarehouseId(@NotNull @PathVariable("warehouseId") Long warehouseId) {
    Warehouse warehouse = warehouseService.loadWarehouseById(warehouseId);
    return saleService.loadSalesByWarehouse(warehouse);
  }
}
