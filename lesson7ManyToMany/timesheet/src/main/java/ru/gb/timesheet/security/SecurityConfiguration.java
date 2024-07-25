package ru.gb.timesheet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.timesheet.model.RoleEnum;
import ru.gb.timesheet.repository.UserRepository;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        //          .requestMatchers("/home/projects/**").hasAuthority(Role.ADMIN.getName())
                                .requestMatchers("/home/projects/**").hasRole("user") // MY_ROLE_PREFIX_admin
//                        .requestMatchers("/home/timesheets/**").hasAnyAuthority(RoleEnum.USER.getName())
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // Обычно используют md5 sha265
        return new BCryptPasswordEncoder();
    }

}
