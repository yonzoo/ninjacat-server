package com.server.ninjacat.data.model.things;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sale")
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "sale_date")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date saleDate;

  @ManyToOne
  @JoinColumn(name = "warehouse_id")
  private Warehouse warehouse;

  public Sale() {
  }

  public Sale(Integer quantity, Integer amount, Date saleDate, Warehouse warehouse) {
    this.amount = amount;
    this.quantity = quantity;
    this.saleDate = saleDate;
    this.warehouse = warehouse;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Date getSaleDate() {
    return saleDate;
  }

  public Date getJavaUtilsSaleDate() {
    return new Date(saleDate.getTime());
  }

  public void setSaleDate(Date saleDate) {
    this.saleDate = saleDate;
  }
}
