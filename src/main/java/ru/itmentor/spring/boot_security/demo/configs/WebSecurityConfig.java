package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.itmentor.spring.boot_security.demo.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    private AccessDeniedHandler accessDeniedHandler;

    //Устанавливает сервис, который отвечает за загрузку деталей пользователя (например, имя пользователя, пароль, роли)
    // из источника данных (например, базы данных).
    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, CustomUserDetailsService userDetailsService,
                             CustomAccessDeniedHandler accessDeniedHandler) {
        this.successUserHandler = successUserHandler;
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
    }
    @SuppressWarnings("deprecation")
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                             //obj позволяющий настраивать правила доступа к URL-адресам.
                        .antMatchers("/", "/index").permitAll()
                        .antMatchers("/admin/**").hasRole("ADMIN") // Доступ только для ROLE_ADMIN
                        .antMatchers("/user/**").hasAnyRole("MANAGER", "ADMIN","USER") // Доступ для USER и ADMIN
                        .anyRequest().authenticated()
                            //Применяет правило ко всем остальным запросам, которые не были указаны ранее.
                            //Требует, чтобы доступ к этим запросам был только для аутентифицированных пользователей.
                .and()
                            //Завершает настройку текущего раздела и возвращается к основному объекту HttpSecurity
                        .formLogin().successHandler(successUserHandler).permitAll()
                //formLogin(): Включает поддержку стандартной формы аутентификации Spring Security.
                //successHandler: Устанавливает кастомный обработчик успешной аутентификации (SuccessUserHandler),
                // который определяет поведение после успешного входа (например, перенаправление пользователя).
                //permitAll(): Разрешает доступ к странице логина всем пользователям.
                .and()
                        .logout().permitAll()
                .and()
        .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler); // Используем кастомный Access Denied Handler


    }

}