package com.metanet.intern.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "/css/**", "/js/**", "/img/**", "/vendor/**",  "/scss/**" };

	//정적 자원에 대해서는 security 설정을 하지 않는다.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(CLASSPATH_RESOURCE_LOCATIONS);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/**").authenticated();
		
		http.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/index")
		.permitAll();
		
		http.exceptionHandling().accessDeniedPage("/login");
	}
}
