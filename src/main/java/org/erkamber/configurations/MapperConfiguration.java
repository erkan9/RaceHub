package org.erkamber.configurations;

import org.hibernate.collection.internal.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<PersistentBag, List<?>> persistentBagToListConverter = context ->
                context.getSource() != null ? new ArrayList<>(context.getSource()) : null;

        modelMapper.addConverter(persistentBagToListConverter);

        return modelMapper;
    }
}
