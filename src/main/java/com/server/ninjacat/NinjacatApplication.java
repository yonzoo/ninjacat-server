package com.server.ninjacat;

import com.server.ninjacat.data.model.things.Sale;
import com.server.ninjacat.data.model.things.Warehouse;
import com.server.ninjacat.data.model.users.ApplicationUser;
import com.server.ninjacat.data.service.things.SaleService;
import com.server.ninjacat.data.service.things.WarehouseService;
import com.server.ninjacat.data.service.users.ApplicationUserService;
import com.server.ninjacat.security.roles.ApplicationUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class NinjacatApplication {

  public static void main(String[] args) {
    SpringApplication.run(NinjacatApplication.class, args);
  }
}
