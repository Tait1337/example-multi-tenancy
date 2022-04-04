package de.clique.westwood.example.examplemultitenancy.service;

import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private TenantConfiguration tenantConfiguration;

  public UserService(TenantConfiguration tenantConfiguration){
    this.tenantConfiguration = tenantConfiguration;
  }

  /**
   * Get the unique id of the logged-in user
   * @return the user uuid or "anonymousUser"
   */
  public String getUserId(){
    return getAuthentication().getName();
  }

  /**
   * Get the unique name of the tenant for the logged-in user
   * @return the tenant name
   */
  public Optional<String> getTenant(){
    Authentication authentication = getAuthentication();

    if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt){
      String issuerUri = jwt.getClaimAsString("iss");
      if (issuerUri != null){
        String realmName = issuerUri.substring(issuerUri.lastIndexOf("/")+1);
        if (tenantConfiguration.getTenants().contains(realmName)){
          return Optional.of(realmName);
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Get details about the logged-in user like name and email-address
   * @return the stream of user details
   */
  public Stream<String> getUserdetails(){
    Authentication authentication = getAuthentication();

    String userId = null;
    String username = null;
    String email = null;
    String firstname = null;
    String lastname = null;
    String fullname = null;

    Object principal = authentication.getPrincipal();
    if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt){
      userId = authentication.getName();
      username = (String) jwt.getClaims().get("preferred_username");
      email = (String) jwt.getClaims().get("email");
      firstname = (String) jwt.getClaims().get("given_name");
      lastname = (String) jwt.getClaims().get("family_name");
      fullname = (String) jwt.getClaims().get("name");
    } else if (principal instanceof String){
      userId = authentication.getName();
    }
    return Stream.of(userId, username, email, firstname, lastname, fullname);
  }

  /**
   * Get all granted scopes for the logged-in user
   * @return the stream of granted scopes. Each scope is prefixed with "SCOPE_"
   */
  public Stream<String> getScopes(){
    Authentication authentication = getAuthentication();

    Collection<? extends GrantedAuthority> scopes = authentication.getAuthorities();
    return scopes.stream()
        .map(GrantedAuthority::getAuthority);
  }

  private Authentication getAuthentication(){
    return SecurityContextHolder.getContext().getAuthentication();
//    if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
//      return SecurityContextHolder.getContext().getAuthentication();
//    }
//    throw new UsernameNotFoundException("Username not found");
  }

}
