package de.clique.westwood.example.examplemultitenancy.config.db;

import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfiguration {

  @Bean
  public Flyway flyway(Environment env, DataSource dataSource, TenantConfiguration tenantConfiguration,
      @Value("${spring.flyway.default-schema}") String defaultSchema,
      @Value("${spring.flyway.locations:classpath:db/migration}") String locations
      ) {
    Flyway flyway = null;
    DataSource tenantDataSource = dataSource;
    for (String tenant : tenantConfiguration.getTenants()){
      String schema = env.getProperty(tenant+".spring.jpa.hibernate.properties.hibernate.default_schema", defaultSchema+"_"+tenant);
      if (dataSource instanceof TenantDataSource castedDataSource){
        tenantDataSource = castedDataSource.getResolvedDataSources().get(tenant);
      }
      flyway = Flyway.configure()
          .locations(locations) // you could also add additional tenant-specific schemas here
          .dataSource(tenantDataSource)
          .schemas( schema)
          .load();
      flyway.migrate();
    }
    return flyway;
  }

}
