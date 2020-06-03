package com.server.ninjacat.controller.authorization;

public class AccountResponse {
  private Long userId;
  private String username;
  private String userRole;

  public AccountResponse(Long userId, String username, String userRole) {
    this.userId = userId;
    this.username = username;
    this.userRole = userRole;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }
}
