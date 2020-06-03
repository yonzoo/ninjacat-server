package com.server.ninjacat.controller.management;

import com.server.ninjacat.data.model.things.ExpenseItem;
import com.server.ninjacat.data.service.things.ExpenseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/expenses")
public class ExpenseItemManagementController {

  private final ExpenseItemService expenseItemService;

  @Autowired
  public ExpenseItemManagementController(ExpenseItemService expenseItemService) {
    this.expenseItemService = expenseItemService;
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('expense_item:read')")
  public List<ExpenseItem> getAllExpenseItems() {
      return expenseItemService.loadAllExpenseItems();
  }

  @DeleteMapping(path = "/delete/{id}")
  @PreAuthorize("hasAuthority('expense_item:write')")
  public MessageResponse deleteExpenseItem(@NotNull @PathVariable("id") Long expenseItemId) {
      expenseItemService.deleteExpenseItemById(expenseItemId);
    return new MessageResponse("Expense was successfully deleted!");
  }

  @PutMapping(path = "{id}")
  @PreAuthorize("hasAuthority('expense_item:write')")
  public ExpenseItem updateExpenseItem(@NotNull @PathVariable("id") Long expenseItemId, @RequestBody ExpenseItem expenseItem) {
      return expenseItemService.updateExpenseItemById(expenseItemId, expenseItem);
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No expense items were found")
  public static class NoExpenseItemsException extends RuntimeException {
  }
}