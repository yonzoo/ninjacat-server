package com.server.ninjacat.data.repository.things;

import com.server.ninjacat.data.model.things.ExpenseItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExpenseItemRepository extends CrudRepository<ExpenseItem, Long> {
  Optional<ExpenseItem> findExpenseItemByName(String name);
}
