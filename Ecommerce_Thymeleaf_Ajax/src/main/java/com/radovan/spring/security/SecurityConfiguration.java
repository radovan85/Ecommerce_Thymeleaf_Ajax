package com.radovan.spring.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.radovan.spring.security.handler.LoginSuccessHandler;
import com.radovan.spring.service.impl.UserDetailsImpl;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin()
				
				.loginPage("/login")
				.successHandler(new LoginSuccessHandler())
				.loginProcessingUrl("/login")
				.usernameParameter("email").passwordParameter("password")
				.permitAll();

		http.logout()
				.permitAll()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.and().csrf().disable();

		http
        .authorizeRequests()
        .antMatchers("/login").anonymous().
        antMatchers("/loginErrorPage","/products/**","/aboutUs","/suspensionPage").permitAll()
        .antMatchers("/","/home","/registerComplete","/registerFail","/loginPassConfirm","/suspensionChecker").permitAll()
        .antMatchers("/get*").hasAnyAuthority("ADMIN","ROLE_USER")
		.antMatchers("/admin/**").hasAuthority("ADMIN")
		.antMatchers("/cart/**","/order/**").hasAuthority("ROLE_USER")
		.antMatchers("/userRegistration").anonymous()
		.antMatchers("/register").anonymous()
        .anyRequest().authenticated();
        

	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/images/**", "/css/**","/js/**");
	}
}
