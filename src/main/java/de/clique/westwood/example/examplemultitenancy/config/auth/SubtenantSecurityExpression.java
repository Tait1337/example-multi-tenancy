package de.clique.westwood.example.examplemultitenancy.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SubtenantSecurityExpression {

  /**
   * Check if a scope e.g. "SCOPE_SUBTENANT1:USER:VIEW" ends with specific scope e.g. "USER:VIEW"
   * @param authority the specific scope to check for
   * @return returns true if the scope ends with the specific scope.
   */
  public boolean hasAuthority(String authority) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null){
      return authentication.getAuthorities().stream()
          .anyMatch(scope -> scope.getAuthority().toLowerCase().endsWith(authority.toLowerCase()));
    }
    return false;
  }
}
