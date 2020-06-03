package com.server.ninjacat.data.service.things;

import com.server.ninjacat.controller.management.SaleManagementController;
import com.server.ninjacat.data.model.things.Sale;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.repository.things.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

  private final SaleRepository saleRepository;

  @Autowired
  public SaleService(SaleRepository saleRepository) {
    this.saleRepository = saleRepository;
  }

  public Sale loadSaleById(Long id) {
    return saleRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Sale with id %s not found", id)));
  }

  public List<Sale> loadAllSales() {
    List<Sale> saleList = new ArrayList<>();
    saleRepository.findAll().forEach(sale -> {
      sale.setSaleDate(new Date(sale.getSaleDate().getTime()));
      saleList.add(sale);
    });
    if (saleList.isEmpty()) {
      throw new SaleManagementController.NoSalesException();
    } else {
      return saleList;
    }
  }

  public List<Sale> loadSalesByWarehouse(Warehouse warehouse) {
    Optional<List<Sale>> saleList = saleRepository.findSalesByWarehouse(warehouse);
    if (saleList.isPresent()) {
      return saleList.get();
    } else {
      throw new SaleManagementController.NoSalesException();
    }
  }

  public Sale addSale(Sale sale) {
    return saleRepository.save(sale);
  }

  public List<Sale> addAllSales(List<Sale> sales) {
    List<Sale> addedSales = new ArrayList<>();
    saleRepository.saveAll(sales).forEach(addedSales::add);
    return addedSales;
  }

  public Sale updateSaleById(Long id, Sale updatedSale) {
    Optional<Sale> saleOptional = saleRepository.findById(id);
    if (saleOptional.isPresent()) {
      updatedSale.setId(saleOptional.get().getId());
      updatedSale.setSaleDate(saleOptional.get().getSaleDate());
      Sale sale = saleRepository.save(updatedSale);
      sale.setSaleDate(new Date(sale.getSaleDate().getTime()));
      return sale;
    } else {
      throw new SaleManagementController.NoSalesException();
    }
  }

  public void deleteSaleById(Long id) {
    try {
      saleRepository.deleteById(id);
    } catch (Exception ex) {
      throw new SaleManagementController.NoSalesException();
    }
  }
}
