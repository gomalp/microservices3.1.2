package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


@Configuration //Обозначает, что данный класс является источником определения бинов.
@EnableWebMvc //Включает поддержку Spring MVC.
@ComponentScan(basePackages = "ru.itmentor.spring.boot_security.demo") //пакет для поиска компонентов
public class WebConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /*SpringResourceTemplateResolver. Это специализированный класс Thymeleaf, который предназначен для интеграции с приложениями,
 использующими Spring Framework. Его основная задача — разрешение шаблонов для Thymeleaf, позволяя загружать HTML-шаблоны и другие ресурсы.
Происхождение: SpringResourceTemplateResolver наследует функциональность базового класса AbstractThymeleafViewResolver, специфицируя реализацию
для работы с ресурсами, управляемыми Spring.
*/

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/pages/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    //Этот метод настраивает разрешители представлений.
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine()); //Устанавливается движок шаблонов, используя метод templateEngine().
        registry.viewResolver(resolver);
    }
}