package fr.formation.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fr.formation.app.security.jwt.JwtAuthEntryPoint;
import fr.formation.app.security.jwt.JwtAuthTokenFilter;

@Configuration // indique à Spring qu'il s'agit d'une classe de configuration.
@EnableWebSecurity // active la sécurité Web dans l'application
public class SecurityConfig {

	// Utilisée pour charger les informations sur l'utilisateur lors de
	// l'authentification
	@Autowired
	private UserDetailsService userDetailsService;

	// Utilisée pour gérer les erreurs d'authentification JWT
	@Autowired
	private JwtAuthEntryPoint unauthorizedHandler;

	// Filtre pour intercepter les requêtes HTTP et valider les jetons JWT
	@Bean
	JwtAuthTokenFilter authenticationJwtTokenFilter() {
		return new JwtAuthTokenFilter();
	}

	// Nécessaire pour gérer l'authentification des utilisateurs.
	@Bean
	AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// Configure un bean pour l'encodeur de mot de passe
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
		http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				// Autoriser l'accès à certaines URL sans authentification
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/api/auth/**")
                        .permitAll()
                         // Toutes les autres URL nécessitent une authentification
                        .anyRequest()
                        .authenticated())
                // Gestion des exceptions liées à l'authentification
                // ..
                // Configuration de la gestion des sessions
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Ajout du filtre JwtAuthTokenFilter avant UsernamePasswordAuthenticationFilter
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
	}
}