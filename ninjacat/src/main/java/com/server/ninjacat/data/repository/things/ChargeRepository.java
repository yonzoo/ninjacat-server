package com.server.ninjacat.data.repository.things;

import com.server.ninjacat.data.model.things.Charge;
import com.server.ninjacat.data.model.things.ExpenseItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChargeRepository extends CrudRepository<Charge, Long> {
}
