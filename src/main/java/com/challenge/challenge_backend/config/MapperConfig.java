package com.challenge.challenge_backend.config;

import com.challenge.challenge_backend.dto.ResponseDocumentRedDTO;
import com.challenge.challenge_backend.model.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }
}
