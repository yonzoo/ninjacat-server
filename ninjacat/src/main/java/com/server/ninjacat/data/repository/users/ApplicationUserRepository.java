package com.server.ninjacat.data.repository.users;

import com.server.ninjacat.data.model.users.ApplicationUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
  Optional<UserDetails> findUserByUsername(String username);
}
