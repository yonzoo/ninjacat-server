package com.server.ninjacat.controller;

public class WarehouseResponse {
  private Long id;
  private String name;
  private Integer quantity;
  private Integer amount;

  public WarehouseResponse(Long id, String name, Integer quantity, Integer amount) {
    this.id = id;
    this.name = name;
    this.quantity = quantity;
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
