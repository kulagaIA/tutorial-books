package com.tutorial.books.configuration;

import com.tutorial.books.dto.UserCreate;
import com.tutorial.books.dto.UserUpdate;
import com.tutorial.books.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(UserUpdate.class, User.class,
                (UserUpdate userUpdateDTO) -> modelMapper.map(userUpdateDTO, User.class));
        registry.addConverter(UserCreate.class, User.class,
                (UserCreate userCreateDTO) -> modelMapper.map(userCreateDTO, User.class));
    }

}
