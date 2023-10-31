package com.capgemini.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.capgemini.biblioteca.services.UserDetailsServiceImpl;
 
@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
 
    /**
     * Configura el encriptador de contraseñas a utilizar en la aplicación.
     * 
     * @return el encriptador de contraseñas BCryptPasswordEncoder.
     */
    @Bean
    PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
     
        return authProvider;
    }
    
    
	/**
	 * Configura el AuthenticationManager a utilizar en la autenticación.
	 * 
	 * @param auth la configuración de autenticación.
	 * @return el AuthenticationManager configurado.
	 * @throws Exception si ocurre algún error al obtener el AuthenticationManager.
	 */
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
 
	/**
	 * Configura las reglas de autorización para las rutas de la aplicación.
	 * 
	 * @param http el objeto HttpSecurity a configurar.
	 * @return el SecurityFilterChain configurado.
	 * @throws Exception si ocurre algún error al configurar las reglas de
	 *                   autorización.
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().ignoringRequestMatchers("/login");
		http
		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/").hasAnyRole("ADMIN", "LECTOR")
			.requestMatchers("/libros").hasAnyRole("ADMIN", "LECTOR")
			.requestMatchers("/libros/create").hasAnyRole("ADMIN")
			.requestMatchers("/libros/**").hasAnyRole("ADMIN")
			.requestMatchers("/lectores").hasAnyRole("ADMIN")
			.requestMatchers("/lectores/**").hasAnyRole("ADMIN")
			.requestMatchers("/autores/**").hasAnyRole("ADMIN")
			.requestMatchers("/libros/{id}").hasAnyRole("ADMIN")
			.requestMatchers("/signup").permitAll()
			.requestMatchers("/prestamos/crear").hasAnyRole("ADMIN", "LECTOR")
			.requestMatchers("/prestamos/crear/{id}").hasAnyRole("LECTOR")
			.requestMatchers("/prestamos/{id}").hasAnyRole("LECTOR")
			.requestMatchers("/prestamos/devolver/{lector_id}/{prestamo_id}").hasAnyRole("LECTOR")
			.anyRequest().authenticated()
		)
		.formLogin((form) -> form
			.loginPage("/login")
			.permitAll()
		)
		.logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login").permitAll());
		
		http.authenticationProvider(authenticationProvider());

	return http.build();
	}
 
	/**
	 * Configura el objeto WebMvcConfigurer para permitir todas las peticiones CORS.
	 * 
	 * @return el WebMvcConfigurer configurado.
	 */
//	@Bean
//	WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedMethods("*");
//			}
//		};
//	}
	
}