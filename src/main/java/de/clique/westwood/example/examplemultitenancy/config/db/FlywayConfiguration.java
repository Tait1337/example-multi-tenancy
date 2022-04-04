package de.clique.westwood.example.examplemultitenancy.config.db;

import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfiguration {

  @Bean
  public Flyway flyway(DataSource dataSource, TenantConfiguration tenantConfiguration,
      @Value("${spring.flyway.default-schema}") String defaultSchema,
      @Value("${spring.flyway.locations:classpath:db/migration}") String locations
      ) {
    Flyway flyway = null;
    for (String tenant : tenantConfiguration.getTenants()){
      flyway = Flyway.configure()
          .locations(locations) // you could also add additional tenant-specific schemas here
          .dataSource(dataSource)
          .schemas(defaultSchema + "_" +  tenant)
          .load();
      flyway.migrate();
    }
    return flyway;
  }

}
