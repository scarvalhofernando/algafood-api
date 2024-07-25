package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Filter shallowEtagHeaderFilter(){
        return (Filter) new ShallowEtagHeaderFilter();
    }
}
