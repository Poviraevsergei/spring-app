package by.tms.config;

import by.tms.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        PathPatternRequestMatcher.Builder pathBuilder = PathPatternRequestMatcher.withDefaults();
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(pathBuilder.matcher(HttpMethod.POST, "/registration")).permitAll()
                        .requestMatchers(pathBuilder.matcher(HttpMethod.GET,"/user")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET,"/user/sort/**")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET,"/user/pagination/**")).hasRole(Role.ADMIN.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET,"/user/myself")).hasAnyRole(Role.USER.name(),Role.ADMIN.name(),Role.MODERATOR.name())
                                .requestMatchers(pathBuilder.matcher(HttpMethod.GET, "/user/**")).permitAll()
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    //IN MEMORY
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetailsUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles(Role.USER.name()).build();

        UserDetails userDetailsAdmin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.name()).build();

        return new InMemoryUserDetailsManager(userDetailsUser, userDetailsAdmin);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
