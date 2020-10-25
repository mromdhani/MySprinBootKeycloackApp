package be.businesstraining.config;


import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import be.businesstraining.models.Task;


@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
{
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder)
    {
        SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
        simpleAuthorityMapper.setPrefix("ROLE_");

        KeycloakAuthenticationProvider keycloakAuthenticationProvider =
                keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(simpleAuthorityMapper);
        authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy ()
    {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager()
    {
        return new HttpSessionManager();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        super.configure(httpSecurity);
        httpSecurity.authorizeRequests()
                 .antMatchers("/tasks").hasRole("User")
                // .antMatchers("/h2-console/**").permitAll()
                 .anyRequest().permitAll()
                 // turns off CSRF only for /h2-console. CSRF protection breaks all POSTs 
                 // until you add the secret formula to each form. The console doesn’t have these, so it is not compatible with CSRF protection.
                 .and().csrf().ignoringAntMatchers("/h2-console/**")
                 // enables loading pages in frames as long as the frames come from our own site.
                 .and().headers().frameOptions().sameOrigin() ;
                 
               
                
    }
}
