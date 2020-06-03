package com.server.ninjacat.controller.authorization;

import com.server.ninjacat.data.model.users.ApplicationUser;
import com.server.ninjacat.security.roles.ApplicationUserRole;
import com.server.ninjacat.data.service.users.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

  private final ApplicationUserService applicationUserService;

  @Autowired
  public RegistrationController(ApplicationUserService applicationUserService) {
    this.applicationUserService = applicationUserService;
  }

  @PostMapping(path = "/create/client")
  public AccountResponse registerClient(@NotNull @RequestBody ApplicationUser applicationUser) {
    applicationUserService.registerUser(applicationUser, ApplicationUserRole.CLIENT);
    String CLIENT_ROLE = "CLIENT";
    return new AccountResponse(
        applicationUser.getId(),
        applicationUser.getUsername(),
        CLIENT_ROLE
    );
  }

  @ResponseStatus(code = HttpStatus.CONFLICT, reason = "User already exists")
  public static class UserAlreadyExistsException extends RuntimeException {
  }

  @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Provided data is invalid")
  public static class UserDataIsInvalidException extends RuntimeException {
  }
}
