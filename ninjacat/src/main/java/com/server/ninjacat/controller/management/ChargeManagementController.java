package com.server.ninjacat.controller.management;

import com.server.ninjacat.controller.ChargeRequest;
import com.server.ninjacat.controller.ChargeResponse;
import com.server.ninjacat.data.model.things.Charge;
import com.server.ninjacat.data.model.things.ExpenseItem;
import com.server.ninjacat.data.service.things.ChargeService;
import com.server.ninjacat.data.service.things.ExpenseItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("management/api/v1/charges")
public class ChargeManagementController {

  private final ChargeService chargeService;
  private final ExpenseItemService expenseItemService;

  @Autowired
  public ChargeManagementController(ChargeService chargeService, ExpenseItemService expenseItemService) {
    this.chargeService = chargeService;
    this.expenseItemService = expenseItemService;
  }

  @GetMapping("{expenseItemId}")
  public List<ChargeResponse> getChargesByExpenseItemId(@NotNull @PathVariable("expenseItemId") Long expenseItemId) {
    ExpenseItem expenseItem = expenseItemService.loadExpenseItemById(expenseItemId);
    return expenseItem
        .getCharges()
        .stream()
        .map(charge -> {
          charge.setChargeDate(new Date(charge.getChargeDate().getTime()));
          return new ChargeResponse(
              charge.getId(),
              charge.getExpenseItem().getId(),
              charge.getAmount(),
              charge.getChargeDate().toInstant().toString()
          );
        })
        .collect(Collectors.toList());
  }

  @PostMapping("/add")
  @PreAuthorize("hasAuthority('charge:write')")
  public ChargeResponse addNewCharge(@NotNull @RequestBody ChargeRequest chargeRequest) {
    try {
      ExpenseItem expenseItem = expenseItemService.loadExpenseItemByName(chargeRequest.getName());
      return buildChargeResponseForAddNewCharge(chargeRequest, expenseItem);
    } catch (ExpenseItemManagementController.NoExpenseItemsException noExpenseItemsException) {
      return buildChargeResponseForAddNewCharge(chargeRequest, null);
    }
  }

  public ChargeResponse buildChargeResponseForAddNewCharge(ChargeRequest chargeRequest, ExpenseItem expenseItem) {
    Charge charge = new Charge();
    if (expenseItem != null) {
      charge.setAmount(chargeRequest.getAmount());
      charge.setExpenseItem(expenseItem);
    } else {
      ExpenseItem newExpenseItem = new ExpenseItem();
      newExpenseItem.setName(chargeRequest.getName());
      expenseItemService.addExpenseItem(newExpenseItem);
      charge.setAmount(chargeRequest.getAmount());
      charge.setExpenseItem(newExpenseItem);
    }
    charge.setChargeDate(new Date());
    Charge addedCharge = chargeService.addCharge(charge);
    return new ChargeResponse(
        addedCharge.getId(),
        addedCharge.getExpenseItem().getId(),
        addedCharge.getAmount(),
        addedCharge.getChargeDate().toInstant().toString()
    );
  }

  @DeleteMapping(path = "/delete/{chargeId}")
  @PreAuthorize("hasAuthority('charge:write')")
  public MessageResponse deleteCharge(@NotNull @PathVariable("chargeId") Long chargeId) {
    chargeService.deleteChargeById(chargeId);
    return new MessageResponse("Charge was successfully deleted!");
  }

  @PutMapping(path = "/edit/{chargeId}")
  @PreAuthorize("hasAuthority('charge:write')")
  public ChargeResponse updateCharge(@NotNull @PathVariable("chargeId") Long chargeId, @RequestBody ChargeRequest chargeRequest) {
    ExpenseItem expenseItem = expenseItemService.loadExpenseItemByName(chargeRequest.getName());
    Charge charge = new Charge();
    charge.setAmount(chargeRequest.getAmount());
    charge.setExpenseItem(expenseItem);
    Charge updatedCharge = chargeService.updateChargeById(chargeId, charge);
    return new ChargeResponse(
        updatedCharge.getId(),
        updatedCharge.getExpenseItem().getId(),
        updatedCharge.getAmount(),
        updatedCharge.getChargeDate().toInstant().toString()
    );
  }

  @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No charges were found")
  public static class NoChargesException extends RuntimeException {
  }
}
