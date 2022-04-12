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
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfiguration {

  private final Map<String, DataSource> dataSources = new HashMap<>();

  public DataSourceConfiguration(Environment env,
      TenantConfiguration tenantConfiguration,
      @Value("${spring.datasource.url}") String defaultUrl,
      @Value("${spring.datasource.username}") String defaultUsername,
      @Value("${spring.datasource.password}") String defaultPassword,
      @Value("${spring.datasource.driver-class-name}") String defaultDriverClassName,
      @Value("${spring.jpa.hibernate.properties.hibernate.default_schema}") String defaultSchema
  ){
    tenantConfiguration.getTenants().forEach(tenant -> {
      String url = env.getProperty(tenant+".spring.datasource.url", defaultUrl);
      String username = env.getProperty(tenant+".spring.datasource.username", defaultUsername);
      String password = env.getProperty(tenant+".spring.datasource.password", defaultPassword);
      String schema = env.getProperty(tenant+".spring.jpa.hibernate.properties.hibernate.default_schema", defaultSchema+"_"+tenant);
      String driverClassName = env.getProperty(tenant+".spring.datasource.driver-class-name", defaultDriverClassName);

      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(url);
      config.setSchema(schema);
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
