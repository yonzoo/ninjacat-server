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
  @Bean
  public CommandLineRunner test(WarehouseService warehouseService, SaleService saleService, ApplicationUserService applicationUserService, PasswordEncoder passwordEncoder) {
    return args -> {
//      ApplicationUser applicationUser = new ApplicationUser("admin", "21RoOm7_");
//      applicationUserService.registerUser(applicationUser, ApplicationUserRole.ADMIN);
//      warehouseService.addWarehouse(new Warehouse("kek", 21, 21));
//      saleService.addSale(new Sale(21, 21, new Date(), warehouseService.loadWarehouseById(3L)));
//      saleService.updateSaleById(2L, new Sale(5, 1, new Date(), warehouseService.loadWarehouseById(1L)));
//      saleService.deleteSaleById(2L);
//      warehouseService.loadWarehouseById(1L).getSales().forEach(sale -> System.out.println(sale.getAmount()));
//      System.out.println(warehouseService.loadAllWarehouses());
//      saleService.loadAllSales().forEach(sale -> System.out.println(sale.getId()));
//      System.out.println(saleService.loadAllSales());

//      warehouseService.loadWarehouseById(1L).getSales().forEach(System.out::println);
//      System.out.println(saleService.loadAllSales().get(0).getWarehouse());
    };
  }
}
