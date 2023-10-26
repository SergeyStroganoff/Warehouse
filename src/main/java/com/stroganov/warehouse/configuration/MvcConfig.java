package com.stroganov.warehouse.configuration;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.utils.parser.DataItemParserImpl;
import com.stroganov.warehouse.utils.parser.DataParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DecimalFormat;

@org.springframework.context.annotation.Configuration
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DecimalFormat decimalFormatPointTwoSigns() {
        return new DecimalFormat("#.00");
    }


    @Bean
    @Primary
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }

    @Bean
    public DataParser<Item> dataParser() {
        return new DataItemParserImpl();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/").setViewName("hello");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/support").setViewName("mail-form");
    }
}
