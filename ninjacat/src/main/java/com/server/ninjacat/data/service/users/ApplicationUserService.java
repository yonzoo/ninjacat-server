package com.server.ninjacat.data.service.users;

import com.server.ninjacat.controller.authorization.RegistrationController;
import com.server.ninjacat.data.model.users.ApplicationUser;
import com.server.ninjacat.security.roles.ApplicationUserRole;
import com.server.ninjacat.data.repository.users.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

  private final ApplicationUserRepository applicationUserRepository;

  private final PasswordEncoder passwordEncoder;

  public ApplicationUserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
    this.applicationUserRepository = applicationUserRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void registerUser(ApplicationUser applicationUser, ApplicationUserRole role) throws InvalidParameterException {
    if (isDataValid(applicationUser.getUsername(), applicationUser.getPassword())) {
      Optional<UserDetails> user = applicationUserRepository.findUserByUsername(applicationUser.getUsername());
      if (user.isPresent()) {
        throw new RegistrationController.UserAlreadyExistsException();
      }
      applicationUser.setApplicationUserRole(role);
      applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
      applicationUser.setAccountNonExpired(true);
      applicationUser.setAccountNonLocked(true);
      applicationUser.setCredentialsNotExpired(true);
      applicationUser.setEnabled(true);
      applicationUserRepository.save(applicationUser);
    } else {
      throw new RegistrationController.UserDataIsInvalidException();
    }
  }

  public boolean isDataValid(String username, String password) {
    if (username.length() <= 3 || username.length() > 30 || password.length() <= 6 || password.length() > 30) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return applicationUserRepository
        .findUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
  }

  public UserDetails loadUserById(Long id) {
    return applicationUserRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found", id)));
  }
}
