package com.server.ninjacat.data.service.things;

import com.server.ninjacat.controller.client.WarehouseController;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.repository.things.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {

  private final WarehouseRepository warehouseRepository;

  @Autowired
  public WarehouseService(WarehouseRepository warehouseRepository) {
    this.warehouseRepository = warehouseRepository;
  }

  public Warehouse loadWarehouseById(Long id) {
    return warehouseRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Warehouse with id %s not found", id)));
  }

  public List<Warehouse> loadAllWarehouses() {
    List<Warehouse> warehouseList = new ArrayList<>();
    warehouseRepository.findAll().forEach(warehouseList::add);
    if (warehouseList.isEmpty()) {
      throw new WarehouseController.NoWarehousesException();
    } else {
      return warehouseList;
    }
  }

  public Warehouse loadWarehouseByName(String name) {
    return warehouseRepository
        .findWarehouseByName(name)
        .orElseThrow(WarehouseController.NoWarehousesException::new);
  }

  public Warehouse addWarehouse(Warehouse warehouse) {
    Optional<Warehouse> found = warehouseRepository.findWarehouseByName(warehouse.getName());
    if (found.isPresent()) {
      Warehouse foundWarehouse = found.get();
      foundWarehouse.setQuantity(foundWarehouse.getQuantity() + warehouse.getQuantity());
      return updateWarehouseById(foundWarehouse.getId(), foundWarehouse);
    }
    return warehouseRepository.save(warehouse);
  }

  public List<Warehouse> addAllWarehouses(List<Warehouse> warehouses) {
    List<Warehouse> addedWarehouses = new ArrayList<>();
    warehouseRepository.saveAll(warehouses).forEach(addedWarehouses::add);
    return addedWarehouses;
  }

  public Warehouse updateWarehouseById(Long id, Warehouse updatedWarehouse) {
    Optional<Warehouse> warehouseOptional = warehouseRepository.findById(id);
    if (warehouseOptional.isPresent()) {
      updatedWarehouse.setId(warehouseOptional.get().getId());
      return warehouseRepository.save(updatedWarehouse);
    } else {
      throw new WarehouseController.NoWarehousesException();
    }
  }

  public void deleteWarehouseById(Long id) {
    try {
      warehouseRepository.deleteById(id);
    } catch (Exception ex) {
      throw new WarehouseController.NoWarehousesException();
    }
  }
}
