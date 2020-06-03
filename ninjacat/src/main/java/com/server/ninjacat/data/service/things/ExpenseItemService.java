package com.server.ninjacat.data.service.things;

import com.server.ninjacat.controller.management.ExpenseItemManagementController;
import com.server.ninjacat.data.model.things.ExpenseItem;
import com.server.ninjacat.data.repository.things.ExpenseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseItemService {
  private final ExpenseItemRepository expenseItemRepository;

  @Autowired
  ExpenseItemService(ExpenseItemRepository expenseItemRepository) {
    this.expenseItemRepository = expenseItemRepository;
  }

  public List<ExpenseItem> loadAllExpenseItems() {
    List<ExpenseItem> expenseItemList = new ArrayList<>();
    expenseItemRepository.findAll().forEach(expenseItemList::add);
    if (expenseItemList.isEmpty()) {
      throw new ExpenseItemManagementController.NoExpenseItemsException();
    } else {
      return expenseItemList;
    }
  }

  public ExpenseItem loadExpenseItemByName(String name) {
    return expenseItemRepository
        .findExpenseItemByName(name)
        .orElseThrow(ExpenseItemManagementController.NoExpenseItemsException::new);
  }

  public ExpenseItem loadExpenseItemById(Long id) {
    return expenseItemRepository
        .findById(id)
        .orElseThrow(ExpenseItemManagementController.NoExpenseItemsException::new);
  }

  public void addExpenseItem(ExpenseItem expenseItem) {
    expenseItemRepository.save(expenseItem);
  }

  public void addAllExpenseItems(List<ExpenseItem> expenseItems) {
    expenseItemRepository.saveAll(expenseItems);
  }

  public ExpenseItem updateExpenseItemById(Long id, ExpenseItem updatedExpenseItem) {
    Optional<ExpenseItem> expenseItemOptional = expenseItemRepository.findById(id);
    if (expenseItemOptional.isPresent()) {
      updatedExpenseItem.setId(expenseItemOptional.get().getId());
      return expenseItemRepository.save(updatedExpenseItem);
    } else {
      throw new ExpenseItemManagementController.NoExpenseItemsException();
    }
  }

  public void deleteExpenseItemById(Long id) {
    expenseItemRepository.deleteById(id);
  }
}
