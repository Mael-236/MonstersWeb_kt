package org.ldv.webmonsters.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // TODO: Retirer cette ligne après les tests
            // Restriction des endpoints en fonction du rôle
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/",
                    "/login",
                    "/inscription",
                    "/a-propos",
                    "/contact",
                    "/rgpd",
                    "/css/**",
                    "/js/**",
                    "/img/**",
                    "/favicon.ico"
                ).permitAll()
                    // Autoriser l'accès pour les ADMIN
                    .requestMatchers("/webmonsters/admin/**").hasRole("ADMIN")
                    // Autoriser l'accès pour les JOUEUR
                    .requestMatchers("/webmonsters/joueur/**").hasRole("JOUEUR")
                    // Toutes les autres requêtes doivent être authentifiées
                    .anyRequest().authenticated()
            }
            // Configuration du formulaire de connexion
            .formLogin { form ->
                form
                    .loginPage("/login")
                    .defaultSuccessUrl("/profil")
                    .failureUrl("/login?error=true")
                    .loginProcessingUrl("/login-process")
                    .permitAll()
            }
            // Configuration du mécanisme de déconnexion
            .logout { logout ->
                logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
            }

        return http.build()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}