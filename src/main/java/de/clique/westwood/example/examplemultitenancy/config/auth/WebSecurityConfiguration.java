package de.clique.westwood.example.examplemultitenancy.config.auth;

import de.clique.westwood.example.examplemultitenancy.config.tenant.TenantConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {

  private String issuerUri;
  private TenantConfiguration tenantConfiguration;

  public WebSecurityConfiguration(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri, TenantConfiguration tenantConfiguration){
    this.issuerUri = issuerUri;
    this.tenantConfiguration = tenantConfiguration;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .antMatchers("/unprotected/**").permitAll() // allow only unprotected urls
            .anyRequest().authenticated() // deny all others
        )
        .oauth2ResourceServer(oauth2 -> oauth2  // use OAuth2 authentication mechanism with JSON Web Tokens (JWT)
            .authenticationManagerResolver(new JwtIssuerAuthenticationManagerResolver(
                tenantConfiguration.getTenants()
                .stream()
                .map(tenant -> issuerUri+tenant)
                .toList()
            ))
        );
    return http.build();
  }

}
