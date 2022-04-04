package de.clique.westwood.example.examplemultitenancy.config.db;

import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import de.clique.westwood.example.examplemultitenancy.service.UserService;
import java.util.Optional;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

class TenantDataSource extends AbstractRoutingDataSource {

  private TenantConfiguration tenantConfiguration;
  private UserService userService;

  public TenantDataSource(TenantConfiguration tenantConfiguration, UserService userService){
    this.tenantConfiguration = tenantConfiguration;
    this.userService = userService;
  }

  @Override
  protected Object determineCurrentLookupKey() {
    Optional<String> tenant = userService.getTenant();
    if (tenant.isEmpty()){
      // no user context available / no user logged on
      return tenantConfiguration.getTenants().get(0);
    }
    return tenant.get();
  }

}
