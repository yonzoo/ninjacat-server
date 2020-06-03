package com.server.ninjacat.controller.client;

import com.server.ninjacat.controller.WarehouseResponse;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.service.things.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/warehouses")
public class WarehouseController {

  private final WarehouseService warehouseService;

  @Autowired
  public WarehouseController(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('warehouse:read')")
  public List<WarehouseResponse> getAllWarehouses() {
    List<Warehouse> warehouses = warehouseService.loadAllWarehouses();
    return warehouses.stream().map(warehouse ->
        new WarehouseResponse(
            warehouse.getId(),
            warehouse.getName(),
            warehouse.getQuantity(),
            warehouse.getAmount())
    ).collect(Collectors.toList());
  }

  @GetMapping(path = "{warehouseName}")
  @PreAuthorize("hasAuthority('warehouse:read')")
  public WarehouseResponse getWarehouseByName(@NotNull @PathVariable("warehouseName") String warehouseName) {
    Warehouse warehouse = warehouseService.loadWarehouseByName(warehouseName);
    return new WarehouseResponse(
        warehouse.getId(),
        warehouse.getName(),
        warehouse.getQuantity(),
        warehouse.getAmount()
    );
  }

  @PostMapping(path = "/add")
  @PreAuthorize("hasAuthority('warehouse:read')")
  public WarehouseResponse addWarehouse(@NotNull @RequestBody Warehouse warehouse) {
    warehouseService.addWarehouse(warehouse);
    Warehouse savedWarehouse = warehouseService.loadWarehouseByName(warehouse.getName());
    return new WarehouseResponse(
        savedWarehouse.getId(),
        savedWarehouse.getName(),
        savedWarehouse.getQuantity(),
        savedWarehouse.getAmount()
    );
  }

  @PutMapping(path = "/edit")
  @PreAuthorize("hasAuthority('warehouse:read')")
  public WarehouseResponse editWarehouse(@NotNull @RequestBody Warehouse warehouse) {
    warehouseService.updateWarehouseById(warehouse.getId(), warehouse);
    Warehouse savedWarehouse = warehouseService.loadWarehouseById(warehouse.getId());
    return new WarehouseResponse(
        savedWarehouse.getId(),
        savedWarehouse.getName(),
        savedWarehouse.getQuantity(),
        savedWarehouse.getAmount()
    );
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No warehouses were found")
  public static class NoWarehousesException extends RuntimeException {
  }

  @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Warehouse exists")
  public static class WarehouseExistsException extends RuntimeException {
  }
}
