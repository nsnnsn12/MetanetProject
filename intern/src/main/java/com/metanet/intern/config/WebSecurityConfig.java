package com.metanet.intern.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.metanet.intern.service.ManagerServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "/css/**", "/js/**", "/img/**", "/vendor/**",  "/scss/**" };

	@Autowired
	ManagerServiceImpl managerService;
	@Autowired
	AuthFailureHandler authFailureHandler;
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	//정적 자원에 대해서는 security 설정을 하지 않는다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(CLASSPATH_RESOURCE_LOCATIONS);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/deniedPage").permitAll()
		.antMatchers("/account/**").permitAll()
		.anyRequest().authenticated();
		
		http.formLogin()
		.loginPage("/login")
		.failureHandler(authFailureHandler)
		.defaultSuccessUrl("/index")
		.permitAll();
		
		http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .invalidateHttpSession(true);
		
		http.exceptionHandling().accessDeniedPage("/deniedPage");
	}
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(managerService).passwordEncoder(passwordEncoder());
    }
}
