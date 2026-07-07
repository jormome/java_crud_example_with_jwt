package com.jmorell.spring.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("es"));
        return localeResolver;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        return localeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirige automáticamente de http://localhost:8080/ a
        // http://localhost:8080/personas
        registry.addRedirectViewController("/", "/personas");
        // Cuando se pida esta URL, renderiza la vista templates/errores/403.html.
        registry.addViewController("/errores/403").setViewName("errores/403");
    }
}
