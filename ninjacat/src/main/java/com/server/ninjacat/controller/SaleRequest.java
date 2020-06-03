package com.server.ninjacat.controller;

import java.util.Date;

public class SaleRequest {
  private String name;
  private Integer quantity;
  private Integer amount;
  private Date date;

  public SaleRequest(String name, Integer quantity, Integer amount, Date date) {
    this.date = date;
    this.name = name;
    this.quantity = quantity;
    this.amount = amount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}
