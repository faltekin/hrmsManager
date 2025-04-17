
package dev.patika.hrmsManager.core.config;

import dev.patika.hrmsManager.jwt.AuthEntryPoint;
import dev.patika.hrmsManager.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String REGISTER = "/v1/auth/register";
    public static final String AUTHENTICATE = "/v1/auth/authenticate";
    public static final String REFRESH_TOKEN = "/v1/auth/refreshToken";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF'i devre dışı bırak
                .authorizeHttpRequests(request ->
                        request.requestMatchers(REGISTER, AUTHENTICATE, REFRESH_TOKEN).permitAll() // Bu yollar herkese açık
                                .anyRequest().authenticated()) // Diğer tüm yollar kimlik doğrulaması gerektirir
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and() // Hata durumunda özel giriş noktası
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless oturum yönetimi
                .authenticationProvider(authenticationProvider) // Özel AuthenticationProvider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JWT filtreyi ekle

        return http.build();
    }
}
