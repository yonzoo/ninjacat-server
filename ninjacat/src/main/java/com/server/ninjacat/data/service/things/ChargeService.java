package com.server.ninjacat.data.service.things;

import com.server.ninjacat.controller.management.ChargeManagementController;
import com.server.ninjacat.data.model.things.Charge;
import com.server.ninjacat.data.model.things.ExpenseItem;
import com.server.ninjacat.data.repository.things.ChargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChargeService {
  private final ChargeRepository chargeRepository;

  @Autowired
  ChargeService(ChargeRepository chargeRepository) {
    this.chargeRepository = chargeRepository;
  }

  public List<Charge> loadAllCharges() {
    List<Charge> chargeList = new ArrayList<>();
    chargeRepository.findAll().forEach(chargeList::add);
    if (chargeList.isEmpty()) {
      throw new EntityNotFoundException("No expense items found in database");
    } else {
      return chargeList;
    }
  }

  public Charge loadChargeById(Long id) {
    return chargeRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Charge with id %s not found", id)));
  }

  public Charge addCharge(Charge charge) {
    return chargeRepository.save(charge);
  }

  public void addAllCharges(List<Charge> charges) {
    chargeRepository.saveAll(charges);
  }

  public Charge updateChargeById(Long id, Charge updatedCharge) {
    Optional<Charge> chargeOptional = chargeRepository.findById(id);
    if (chargeOptional.isPresent()) {
      updatedCharge.setId(chargeOptional.get().getId());
      updatedCharge.setChargeDate(chargeOptional.get().getChargeDate());
      Charge charge = chargeRepository.save(updatedCharge);
      charge.setChargeDate(new Date(charge.getChargeDate().getTime()));
      return charge;
    } else {
      throw new ChargeManagementController.NoChargesException();
    }
  }

  public void deleteChargeById(Long id) {
    chargeRepository.deleteById(id);
  }
}
