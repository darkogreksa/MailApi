package uns.ac.rs.MailApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import uns.ac.rs.MailApi.security.model.AuthenticationEntryLocation;
import uns.ac.rs.MailApi.service.UserService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationEntryLocation authenticationEntryLocation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		try {			
			auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		// Dopusti svim korisnicima da pristupe linku za autentifikaciju i registraciju
//		web.ignoring().antMatchers(HttpMethod.POST, "/authenticate");
//		web.ignoring().antMatchers(HttpMethod.POST, "/users/registration");
//		
//	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Komunikacija je stateless
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// Dopusti svim korisnicima da pristupe linku za autentifikaciju i registraciju
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/authenticate").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/users/registration").permitAll();
		// Zabrani pristup ostatku aplikacije ukoliko korisnik nije autentifikovan
        http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().antMatchers("/**").permitAll();
		// Za neautorizovane zahteve posalji 401 gresku
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryLocation);
        // presretni svaki zahtev filterom
        http.addFilterBefore(new AuthenticationFilter(tokenHelper, userService), BasicAuthenticationFilter.class);
        // enable cors
        http.cors();

		http.csrf().disable();
	}
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
//        web.ignoring().antMatchers(
//                HttpMethod.POST,
//                "/auth/login"
//        );
		web.ignoring().antMatchers(HttpMethod.POST, "/authenticate");
		web.ignoring().antMatchers(HttpMethod.POST, "/users/registration");
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
            );

    }
}
