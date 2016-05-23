package com.spring.boot.test.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Based on https://github.com/royclarkson/spring-rest-service-oauth
 */
@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "myproject-rest-service";

    @Configuration
    @EnableResourceServer
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http
                    .authorizeRequests()
                    .antMatchers("/admin", "/admin/", "/index.html", "/", "/logout", "/assets/**", "/app/**", "/dashboard.html",
                                 "/app/components/login/views/login.html"
                    )
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/management/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private DataSource dataSource;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                   .withClient("mobileapp")
                   .authorizedGrantTypes("password", "refresh_token")
                   .scopes("read", "write")
                   .resourceIds(RESOURCE_ID)
                   .secret(passwordEncoder.encode("123456"))
                   .and()
                   .withClient("angularapp")
                   .authorizedGrantTypes("password", "refresh_token")
                   .scopes("read", "write")
                   .resourceIds(RESOURCE_ID)
                   .secret(passwordEncoder.encode("123456"));

        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore())
                     .authenticationManager(authenticationManager)
                     .userDetailsService(userDetailsService);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.passwordEncoder(passwordEncoder);
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource) {

                // Workaround for https://github.com/spring-projects/spring-boot/issues/5071

                @Override
                protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
                    ObjectInputStream input = null;
                    try {
                        input = new ConfigurableObjectInputStream(
                                new ByteArrayInputStream(authentication),
                                Thread.currentThread().getContextClassLoader());
                        return (OAuth2Authentication) input.readObject();
                    } catch (Exception ex) {
                        throw new IllegalArgumentException(ex);
                    } finally {
                        if (input != null) {
                            try {
                                input.close();
                            } catch (IOException ex) {
                                // Continue
                            }
                        }
                    }
                }
            };
        }
    }
}
