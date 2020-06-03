package com.server.ninjacat.data.model.things;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "charge")
public class Charge {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "amount")
  private Integer amount;

  @Column(name = "charge_date")
  @Temporal(javax.persistence.TemporalType.DATE)
  private Date chargeDate;

  @ManyToOne
  @JoinColumn(name = "expense_item_id")
  private ExpenseItem expenseItem;

  public Charge() {

  }

  public Charge(Long id, Integer amount, Date chargeDate, ExpenseItem expenseItem) {
    this.id = id;
    this.amount = amount;
    this.chargeDate = chargeDate;
    this.expenseItem = expenseItem;
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

  public Date getChargeDate() {
    return chargeDate;
  }

  public void setChargeDate(Date chargeDate) {
    this.chargeDate = chargeDate;
  }

  public ExpenseItem getExpenseItem() {
    return expenseItem;
  }

  public void setExpenseItem(ExpenseItem expenseItem) {
    this.expenseItem = expenseItem;
  }
}
