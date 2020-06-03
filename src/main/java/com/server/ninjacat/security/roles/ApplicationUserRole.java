package com.server.ninjacat.security.roles;

import com.google.common.collect.Sets;
import com.server.ninjacat.security.permissions.ApplicationUserPermission;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.server.ninjacat.security.permissions.ApplicationUserPermission.*;

public enum ApplicationUserRole {
  ADMIN(Sets.newHashSet(
      CLIENT_READ,
      CLIENT_WRITE,
      MODERATOR_READ,
      MODERATOR_WRITE,
      WAREHOUSE_READ,
      WAREHOUSE_WRITE,
      SALE_READ,
      SALE_WRITE,
      EXPENSE_ITEM_READ,
      EXPENSE_ITEM_WRITE,
      CHARGE_READ,
      CHARGE_WRITE)),
  WAREHOUSE_MODERATOR(Sets.newHashSet(
      WAREHOUSE_READ,
      WAREHOUSE_WRITE,
      SALE_READ,
      EXPENSE_ITEM_READ,
      CHARGE_READ)
  ),
  SALES_MODERATOR(Sets.newHashSet(
      SALE_READ,
      SALE_WRITE,
      WAREHOUSE_READ,
      EXPENSE_ITEM_READ,
      CHARGE_READ
  )),
   EXPENSE_ITEM_MODERATOR(
       Sets.newHashSet(
           EXPENSE_ITEM_READ,
           EXPENSE_ITEM_WRITE,
           WAREHOUSE_READ,
           SALE_READ,
           CHARGE_READ
       )
  ),
  CHARGE_MODERATOR(
      Sets.newHashSet(
          CHARGE_READ,
          CHARGE_WRITE,
          WAREHOUSE_READ,
          SALE_READ,
          EXPENSE_ITEM_READ
      )
  ),
  CLIENT(
      Sets.newHashSet(
          WAREHOUSE_READ,
          SALE_READ
      )
  );

  private final Set<ApplicationUserPermission> permissions;

  ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
    this.permissions = permissions;
  }

  public Set<ApplicationUserPermission> getPermissions() {
    return permissions;
  }

  public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
    Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toSet());
    permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return permissions;
  }
}
