package de.clique.westwood.example.examplemultitenancy.controller;

import de.clique.westwood.example.examplemultitenancy.service.DatabaseService;
import de.clique.westwood.example.examplemultitenancy.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/protected")
@PreAuthorize("@subtenantSecurityExpression.hasAuthority('user:view')")
public class ProtectedRestController {

  private final UserService userService;
  private final DatabaseService databaseService;

  public ProtectedRestController(UserService userService, DatabaseService databaseService) {
    this.userService = userService;
    this.databaseService = databaseService;
  }

  @GetMapping("/tenant")
  public String getTenant() {
    Optional<String> tenant = userService.getTenant();
    if (tenant.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return tenant.get();
  }

  @GetMapping("/userid")
  public String getUserId() {
    return userService.getUserId();
  }

  @GetMapping("/userdetails")
  public String getUserdetails() {
    return userService.getUserdetails().collect(Collectors.joining(","));
  }

  @GetMapping("/scopes")
  public String getScopes() {
    return userService.getScopes().collect(Collectors.joining(","));
  }

  @GetMapping("/data")
  public String getData() {
    return databaseService.getData().stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));
  }

}
