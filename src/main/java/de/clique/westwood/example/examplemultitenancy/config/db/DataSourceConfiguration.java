package de.clique.westwood.example.examplemultitenancy.config.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import de.clique.westwood.example.examplemultitenancy.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {

  private Map<String, DataSource> dataSources = new HashMap<>();

  public DataSourceConfiguration(TenantConfiguration tenantConfiguration,
      @Value("${spring.datasource.url}") String url,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      @Value("${spring.datasource.driver-class-name}") String driverClassName,
      @Value("${spring.jpa.hibernate.properties.hibernate.default_schema}") String defaultSchema
  ){
    tenantConfiguration.getTenants().forEach(tenant -> {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(url);
      config.setSchema(defaultSchema+"_"+tenant);
      config.setDriverClassName(driverClassName);
      config.setUsername(username);
      config.setPassword(password);
      this.dataSources.put(tenant, new HikariDataSource(config));
    });
  }

  @Primary
  @Bean
  public DataSource dataSource(TenantConfiguration tenantConfiguration, UserService userService) {
    TenantDataSource customDataSource = new TenantDataSource(tenantConfiguration, userService);
    customDataSource.setTargetDataSources((Map)dataSources);
    return customDataSource;
  }

}
