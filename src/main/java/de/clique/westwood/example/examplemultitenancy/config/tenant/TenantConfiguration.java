package de.clique.westwood.example.examplemultitenancy.config.tenant;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantConfiguration {

  private final List<String> tenants;

  public TenantConfiguration(@Value("#{'${tenants}'.split(',')}") List<String> tenants){
    this.tenants = tenants;
  }

  public List<String> getTenants(){
    return tenants;
  }

}
