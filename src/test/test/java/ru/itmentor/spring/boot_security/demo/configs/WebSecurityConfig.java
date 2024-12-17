package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                             //obj позволяющий настраивать правила доступа к URL-адресам.
                .antMatchers("/", "/index")
                            // Указывает, что правила будут применяться к URL-адресам / и /index (используя шаблоны Ant).
                .permitAll()
                            //Разрешает доступ к указанным URL-адресам всем пользователям без необходимости аутентификации.
                .anyRequest()
                            //Применяет правило ко всем остальным запросам, которые не были указаны ранее.
                .authenticated()
                            //Требует, чтобы доступ к этим запросам был только для аутентифицированных пользователей.
                .and()
                            //Завершает настройку текущего раздела и возвращается к основному объекту HttpSecurity
                .formLogin().successHandler(successUserHandler)
                                //formLogin(): Включает поддержку стандартной формы аутентификации Spring Security.
                                //successHandler(successUserHandler): Устанавливает кастомный обработчик успешной аутентификации (SuccessUserHandler),
                                // который определяет поведение после успешного входа (например, перенаправление пользователя).
                .permitAll()
                //permitAll(): Разрешает доступ к странице логина всем пользователям.
                .and()
                .logout()
                .permitAll();
                             //logout(): Включает поддержку функции выхода из системы.
                             //permitAll(): Разрешает доступ к функции выхода всем пользователям.

        //ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //registry.antMatchers("/", "/index").permitAll();
        //registry.anyRequest().authenticated();
        //
        //FormLoginConfigurer<HttpSecurity> formLogin = http.formLogin();
        //formLogin.successHandler(successUserHandler);
        //formLogin.permitAll();
        //
        //LogoutConfigurer<HttpSecurity> logout = http.logout();
        //logout.permitAll();

        //@Override
        //protected void configure(HttpSecurity http) throws Exception {
        //    // Шаг 1: Инициализация конфигурации авторизации запросов
        //    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //
        //    // Шаг 2: Разрешить доступ к "/" и "/index" всем пользователям
        //    registry.antMatchers("/", "/index").permitAll();
        //
        //    // Шаг 3: Требовать аутентификацию для всех остальных запросов
        //    registry.anyRequest().authenticated();
        //
        //    // Шаг 4: Завершить настройку авторизации и вернуться к HttpSecurity
        //    HttpSecurity httpAfterAuth = registry.and();
        //
        //    // Шаг 5: Настроить форму логина с кастомным обработчиком успешной аутентификации
        //    FormLoginConfigurer<HttpSecurity> formLogin = httpAfterAuth.formLogin();
        //    formLogin.successHandler(successUserHandler).permitAll();
        //
        //    // Шаг 6: Завершить настройку формы логина и вернуться к HttpSecurity
        //    HttpSecurity httpAfterFormLogin = formLogin.and();
        //
        //    // Шаг 7: Настроить функцию выхода из системы и разрешить всем пользователям её использовать
        //    LogoutConfigurer<HttpSecurity> logout = httpAfterFormLogin.logout();
        //    logout.permitAll();
        //}
//        .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/news").hasRole("USER")

    }

    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}