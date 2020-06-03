package com.server.ninjacat.data.model.users;

import com.server.ninjacat.security.roles.ApplicationUserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "application_user")
public class ApplicationUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Column(name = "login")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "granted_authorities")
  private ApplicationUserRole applicationUserRole;

  @Column(name = "is_account_non_expired")
  private boolean isAccountNonExpired;

  @Column(name = "is_account_non_locked")
  private boolean isAccountNonLocked;

  @Column(name = "is_credentials_non_expired")
  private boolean isCredentialsNotExpired;

  @Column(name = "is_account_enabled")
  private boolean isEnabled;

  public ApplicationUser() {

  }

  public ApplicationUser(String username,
               String password,
               ApplicationUserRole applicationUserRole,
               boolean isAccountNonExpired,
               boolean isAccountNonLocked,
               boolean isCredentialsNotExpired,
               boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.applicationUserRole = applicationUserRole;
    this.isAccountNonExpired = isAccountNonExpired;
    this.isAccountNonLocked = isAccountNonLocked;
    this.isCredentialsNotExpired = isCredentialsNotExpired;
    this.isEnabled = isEnabled;
  }

  public ApplicationUser(String username,
                         String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return applicationUserRole.getGrantedAuthorities();
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNotExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setApplicationUserRole(ApplicationUserRole applicationUserRole) {
    this.applicationUserRole = applicationUserRole;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    isAccountNonExpired = accountNonExpired;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    isAccountNonLocked = accountNonLocked;
  }

  public void setCredentialsNotExpired(boolean credentialsNotExpired) {
    isCredentialsNotExpired = credentialsNotExpired;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }
}
