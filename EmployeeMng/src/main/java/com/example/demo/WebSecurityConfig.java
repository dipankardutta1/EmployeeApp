package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select username,password,true from user where username=?")
			.authoritiesByUsernameQuery("")
			.passwordEncoder(new BCryptPasswordEncoder());
		
		
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login")
		.defaultSuccessUrl("/dashboard",true)
		.failureUrl("/login?denied")
		.usernameParameter("username")
		.passwordParameter("password")
		.and()
		.logout().logoutSuccessUrl("/login?logout")
		.and()
		.exceptionHandling().accessDeniedPage("/login?denied")
		.and()
		.csrf().disable();
		
	}
	
	
	

}
