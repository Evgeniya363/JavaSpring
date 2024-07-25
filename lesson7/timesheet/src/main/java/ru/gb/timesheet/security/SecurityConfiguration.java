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

/*
1. Переделать строки RoleName в сущность Role:
1.1 Создать отдельную таблицу Role(id, name)
1.2 Связать User <-> Role отношением ManyToMany
2. После п.1 подправить формирование ролей в MyCustomUserDetailsService
В SecurityFilterChain настроить:
3.1 Стандартная форма логина
3.2 Страницы с проектами доступы пользователям с ролью admin
3.2 Страницы с таймшитами доступы пользователям с ролью user
3.3 REST-ресурсы доступны пользователям с ролью rest
**** Для rest-ресурсов НЕ показывать форму логина.
Т.е. если пользователь не авторизован, то его НЕ редиректит на форму логина, а сразу показывается 401.
Для авторизации нужно отдельно получить JSESSIONID и подставить в запрос.
 */

@Configuration
public class SecurityConfiguration {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(requests -> requests
                        //          .requestMatchers("/home/projects/**").hasAuthority(Role.ADMIN.getName())
                        //        .requestMatchers("/home/projects/**").hasRole("admin") // MY_ROLE_PREFIX_admin
                        .requestMatchers("/home/timesheets/**").hasAnyAuthority("user")
                        .requestMatchers("/home/projects/**").hasAuthority("admin")
                        .requestMatchers("/projects/**").hasAuthority("rest")
                        .requestMatchers("/timesheets/**").hasAuthority("rest")
                        .requestMatchers("/employees/**").hasAuthority("rest")
                        .anyRequest().denyAll()
                )
                .formLogin(Customizer.withDefaults())
                .build();
//        return http
//                .authorizeHttpRequests(requests -> requests
//                                .requestMatchers("/home/projects/**").hasRole("admin")
//                                .requestMatchers("/home/timesheets/**").hasRole("user")
//                                .requestMatchers("/timesheets/**").hasRole("rest")
//                                .requestMatchers("/projects/**").hasRole("rest")
//                                .requestMatchers("/employees/**").hasRole("rest")
//                )
//                .formLogin(Customizer.withDefaults())
//                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // Обычно используют md5 sha265
        return new BCryptPasswordEncoder();
    }

}
