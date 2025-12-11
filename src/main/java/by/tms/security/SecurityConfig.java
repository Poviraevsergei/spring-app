package by.tms.security;

import by.tms.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        PathPatternRequestMatcher.Builder pathBuilder = PathPatternRequestMatcher.withDefaults();
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(pathBuilder.matcher(HttpMethod.POST, "/security/registration")).permitAll()
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/sort/**")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/pagination/**")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/myself")).hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MODERATOR.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/**")).permitAll()
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/myself")).hasAnyRole(Role.USER.name(), Role.ADMIN.name(), Role.MODERATOR.name())
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(customUserDetailService)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
