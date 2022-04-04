package de.clique.westwood.example.examplemultitenancy.controller;

import de.clique.westwood.example.examplemultitenancy.service.DatabaseService;
import de.clique.westwood.example.examplemultitenancy.service.UserService;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedRestController {

  private UserService userService;
  private DatabaseService databaseService;

  public ProtectedRestController(UserService userService, DatabaseService databaseService) {
    this.userService = userService;
    this.databaseService = databaseService;
  }

  @GetMapping("/tenant")
  public String getTenant() {
    return userService.getTenant().get();
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
