package com.server.ninjacat.controller;

public class ChargeResponse {
  private Long id;
  private Long expenseId;
  private Integer amount;
  private String chargeDate;

  public ChargeResponse(Long id, Long expenseId, Integer amount, String chargeDate) {
    this.id = id;
    this.expenseId = expenseId;
    this.amount = amount;
    this.chargeDate = chargeDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(Long expenseId) {
    this.expenseId = expenseId;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getChargeDate() {
    return chargeDate;
  }

  public void setChargeDate(String chargeDate) {
    this.chargeDate = chargeDate;
  }
}
