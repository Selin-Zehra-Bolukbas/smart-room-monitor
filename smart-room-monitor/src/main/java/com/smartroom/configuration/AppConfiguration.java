package com.smartroom.configuration;

import com.smartroom.dto.request.SensorDataRequest;
import com.smartroom.dto.response.SensorDataResponse;
import com.smartroom.entity.SensorData;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(SensorDataRequest.class, SensorData.class)
                .addMappings(map -> map.skip(SensorData::setId));


        modelMapper.typeMap(SensorData.class, SensorDataResponse.class)
                .addMapping(
                        src -> src.getRoom().getId(),
                        SensorDataResponse::setRoomId
                )
                .addMapping(
                        src -> src.getRoom().getName(),
                        SensorDataResponse::setRoomName
                );

        return modelMapper;
    }
}
