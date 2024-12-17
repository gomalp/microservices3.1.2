package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//используется для программной настройки диспетчера сервлетов.
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Метод, указывающий на класс конфигурации
    //Определяет конфигурационные классы для корневого контекста Spring.
    //Корневой контекст используется для bean-компонентов, общих для всего приложения
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{HibernateConfig.class};
    }


    // Добавление конфигурации, в которой инициализируем ViewResolver, для корректного отображения шаблонов
    //Определяет конфигурационные классы для контекста сервлета Spring MVC.
    //Контекст сервлета отвечает за веб-специфические компоненты.
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }


    /* Данный метод указывает url, на котором будет базироваться приложение */
    //Определяет URL-маппинг для DispatcherServlet. Указывает, какие URL будут обрабатываться Spring MVC.
    //"/" означает, что DispatcherServlet будет обрабатывать все входящие HTTP-запросы.
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}