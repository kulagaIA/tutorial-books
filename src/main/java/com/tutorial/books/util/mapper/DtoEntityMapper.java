package com.tutorial.books.util.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class DtoEntityMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <T> Collection<T> map(Collection<?> source, Class<T> type) {
        return source.stream()
                .map(entity -> modelMapper.map(entity, type))
                .collect(Collectors.toList());
    }

    public <T> T map(Object source, Class<T> type) {
        return  modelMapper.map(source, type);
    }

}
