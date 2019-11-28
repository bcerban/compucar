package com.cerbansouto.compucar.context;

import com.cerbansouto.compucar.api.BeanReporterService;
import com.cerbansouto.compucar.services.BeanReporterServiceImpl;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)
@EnableWebMvc
@ComponentScan(basePackages = {"com.cerbansouto.compucar"})
@EnableAspectJAutoProxy
public class WebContext implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**").addResourceLocations("/");
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/jsp/");
        viewResolver.setContentType("text/html; charset=UTF-8");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

//    @Bean
//    public BeanReporterService beanReporterService() {
//        BeanReporterService beanReporterService = new BeanReporterServiceImpl();
//        return beanReporterService;
//    }
}
