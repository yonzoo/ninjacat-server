package com.server.ninjacat.data.model.things;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "expense_item")
public class ExpenseItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "expenseItem", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  @JsonIgnore
  private Set<Charge> charges;

  public ExpenseItem() {

  }

  public ExpenseItem(Long id, String name) {
    this.id = id;
    this.name = name;
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

  public Set<Charge> getCharges() {
    return charges;
  }

  public void setCharges(Set<Charge> charges) {
    this.charges = charges;
  }
}
