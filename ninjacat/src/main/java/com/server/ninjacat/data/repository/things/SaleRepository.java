package com.server.ninjacat.data.repository.things;

import com.server.ninjacat.data.model.things.Sale;
import com.server.ninjacat.data.model.things.Warehouse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends CrudRepository<Sale, Long> {
  Optional<List<Sale>> findSalesByWarehouse(Warehouse warehouse);
}
