package com.server.ninjacat.controller.management;

import com.server.ninjacat.controller.WarehouseResponse;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.service.things.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("management/api/v1/warehouses")
public class WarehouseManagementController {

  private final WarehouseService warehouseService;

  @Autowired
  public WarehouseManagementController(WarehouseService warehouseService) {
    this.warehouseService = warehouseService;
  }

  @PostMapping("/add")
  @PreAuthorize("hasAuthority('warehouse:write')")
  public WarehouseResponse addNewWarehouse(@NotNull @RequestBody Warehouse warehouse) {
    Warehouse addedWarehouse = warehouseService.addWarehouse(warehouse);
    return new WarehouseResponse(
        addedWarehouse.getId(),
        addedWarehouse.getName(),
        addedWarehouse.getQuantity(),
        addedWarehouse.getAmount());
  }

  @DeleteMapping(path = "/delete/{id}")
  @PreAuthorize("hasAuthority('warehouse:write')")
  public MessageResponse deleteWarehouse(@NotNull @PathVariable("id") Long warehouseId) {
    warehouseService.deleteWarehouseById(warehouseId);
    return new MessageResponse("Warehouse was successfully deleted!");
  }

  @PutMapping(path = "/edit/{id}")
  @PreAuthorize("hasAuthority('warehouse:write')")
  public WarehouseResponse updateWarehouse(@NotNull @PathVariable("id") Long warehouseId,
                                           @RequestBody Warehouse warehouse) {
    Warehouse updatedWarehouse = warehouseService.updateWarehouseById(warehouseId, warehouse);
    return new WarehouseResponse(
        updatedWarehouse.getId(),
        updatedWarehouse.getName(),
        updatedWarehouse.getQuantity(),
        updatedWarehouse.getAmount());
  }
}
