package com.server.ninjacat.security.permissions;

public enum ApplicationUserPermission {
  WAREHOUSE_READ("warehouse:read"),
  WAREHOUSE_WRITE("warehouse:write"),
  SALE_READ("sale:read"),
  SALE_WRITE("sale:write"),
  CLIENT_READ("client:read"),
  CLIENT_WRITE("client:write"),
  MODERATOR_READ("moderator:read"),
  MODERATOR_WRITE("moderator:write"),
  EXPENSE_ITEM_READ("expense_item:read"),
  EXPENSE_ITEM_WRITE("expense_item:write"),
  CHARGE_READ("charge:read"),
  CHARGE_WRITE("charge:write");

  private final String permission;

  ApplicationUserPermission(String permission) {
    this.permission = permission;
  }

  public String getPermission() {
    return permission;
  }
}