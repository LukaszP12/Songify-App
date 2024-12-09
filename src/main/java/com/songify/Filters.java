package com.songify;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@AllArgsConstructor
public class Filters implements CommandLineRunner {

    private final ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        // List all registered Filter beans
        Map<String, Filter> filters = applicationContext.getBeansOfType(Filter.class);
        filters.forEach((name,filter) -> {
            System.out.println("Filter name: " + name + " , Filter class: " + filter.getClass().getName());
        });

        // List all FilterRegistrationBeans
        Map<String, FilterRegistrationBean> filterRegistrationBeans = applicationContext.getBeansOfType(FilterRegistrationBean.class);
        filterRegistrationBeans.forEach((name,bean) -> {
            System.out.println("FilterRegistrationBean name: " + name + ", Filter class: " + bean.getFilter().getClass().getName());
        });
    }
}
