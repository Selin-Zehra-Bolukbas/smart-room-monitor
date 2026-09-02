package com.smartroom.service.impl;

import com.smartroom.dto.request.SensorDataRequest;
import com.smartroom.dto.response.SensorDataResponse;
import com.smartroom.entity.Room;
import com.smartroom.entity.SensorData;
import com.smartroom.repository.RoomRepository;
import com.smartroom.repository.SensorDataRepository;
import com.smartroom.service.SensorDataService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SensorDataServiceImpl implements SensorDataService {

    private final SensorDataRepository sensorDataRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorDataServiceImpl(SensorDataRepository sensorDataRepository, RoomRepository roomRepository
            , ModelMapper modelMapper) {
        this.sensorDataRepository = sensorDataRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SensorDataResponse createSensorData(SensorDataRequest sensorDataRequest) {
        Room room = roomRepository.findById(sensorDataRequest.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + sensorDataRequest.getRoomId()));

        SensorData sensorData = modelMapper.map(sensorDataRequest, SensorData.class);
        sensorData.setRoom(room);

        SensorData savedSensorData = sensorDataRepository.save(sensorData);

        SensorDataResponse response = modelMapper.map(savedSensorData, SensorDataResponse.class);

        response.setRoomName(room.getName());
        response.setRoomId(room.getId());

        return response;
    }

    @Override
    public List<SensorDataResponse> getSensorDataByRoomId(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room not found with id: " + roomId);
        }
        List<SensorData> sensorDataList = sensorDataRepository.findByRoom_Id(roomId);

        return sensorDataList.stream()
                .map(data -> {
                    SensorDataResponse response = modelMapper.map(data, SensorDataResponse.class);
                    response.setRoomName(data.getRoom().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SensorDataResponse getSensorDataById(Long dataId) {
        SensorData sensorData = sensorDataRepository.findById(dataId)
                .orElseThrow(() -> new EntityNotFoundException("Sensor data not found with id: " + dataId));

        SensorDataResponse response=modelMapper.map(sensorData, SensorDataResponse.class);
        response.setRoomName(sensorData.getRoom().getName());
        return response;
    }

    @Override
    public List<SensorDataResponse> getAllSensorData() {
        return sensorDataRepository.findAll().stream()
                .map(data->{
                    SensorDataResponse response=modelMapper.map(data, SensorDataResponse.class);
                    response.setRoomName(data.getRoom().getName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public SensorDataResponse getLatestDataByRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room not found with id: " + roomId);
        }

        SensorData latestData = sensorDataRepository.findFirstByRoomIdOrderByTimestampDesc(roomId)
                .orElseThrow(() -> new EntityNotFoundException("No sensor data found for room: " + roomId));

        SensorDataResponse response = modelMapper.map(latestData, SensorDataResponse.class);
        response.setRoomName(latestData.getRoom().getName());
        response.setRoomId(latestData.getRoom().getId());

        return response;
    }

    @Override
    public List<SensorDataResponse> getDataHistoryForRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room not found with id: " + roomId);
        }

        List<SensorData> historyData = sensorDataRepository.findTop50ByRoomIdOrderByTimestampDesc(roomId);

        return historyData.stream()
                .map(data -> {
                    SensorDataResponse response = modelMapper.map(data, SensorDataResponse.class);
                    response.setRoomName(data.getRoom().getName());
                    response.setRoomId(data.getRoom().getId());
                    return response;
                })
                .collect(Collectors.toList());
    }
}
