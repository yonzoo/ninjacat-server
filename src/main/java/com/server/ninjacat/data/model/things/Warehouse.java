package com.server.ninjacat.data.model.things;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "warehouse")
public class Warehouse {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "amount")
  private Integer amount;

  @OneToMany(mappedBy = "warehouse", fetch = FetchType.EAGER)
  private Set<Sale> sales;

  public Warehouse() {
  }

  public Warehouse(String name, Integer quantity, Integer amount) {
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

  public Set<Sale> getSales() {
    return sales;
  }

  public void setSales(Set<Sale> sales) {
    this.sales = sales;
  }

  @Override
  public String toString() {
    return "Warehouse{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", quantity=" + quantity +
        ", amount=" + amount +
        '}';
  }
}