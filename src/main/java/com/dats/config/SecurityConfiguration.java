package com.dats.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
    private final UserDetailsService       userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    private static final String[] UNAUTHENTICATED_ROUTES = new String[] {
            //"/confirm-email?{.+}&{.+}",
           // "/result-report-print.html?" + request.getQueryString(), //{spring:[a-z]+}",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
		        http
		        .authorizeRequests()
		           //.antMatchers("/", "/demo.html", "/video.html","/lecture-file-list.html").permitAll()
		        	//.regexMatchers( UNAUTHENTICATED_ROUTES ).permitAll()
		           //.antMatchers("/result-report-print.html/**").permitAll()
		           .antMatchers("/report**").permitAll()
		           .antMatchers("/login").permitAll()
		           .antMatchers("/resources/**").permitAll()
		           //.regexMatchers("/report.*t=.*").permitAll()
		           .anyRequest().authenticated()
		           .and()
		        .formLogin()
		           .loginPage("/login.html")
		           .usernameParameter("username")
		           .passwordParameter("password")
		           .loginProcessingUrl("/login")
		           .defaultSuccessUrl("/")
		           .permitAll()
		           .and()
		        .logout()
			        .invalidateHttpSession(true)
	                .clearAuthentication(true)
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .logoutSuccessUrl("/login.html?logout")
	                .permitAll()
		           .and()
		        .headers()
		           .defaultsDisabled()
		           .frameOptions()
		           .sameOrigin()
		           .cacheControl();
		http
		        .csrf().disable();
    }
	/*
	 * @Autowired protected void configureGlobal(AuthenticationManagerBuilder auth)
	 * throws Exception {
	 * 
	 * auth.userDetailsService(userDetailsService).passwordEncoder(passen()); }
	 */
	   
	   @Bean
	   public PasswordEncoder passen() {
		   return new BCryptPasswordEncoder();
	   }
	   
	   @Bean
		public SpringSecurityDialect securityDialect() {
		    return new SpringSecurityDialect();
		}
	
}
