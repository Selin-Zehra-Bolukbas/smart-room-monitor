package com.smartroom.service;


import com.smartroom.dto.request.SensorDataRequest;
import com.smartroom.dto.response.SensorDataResponse;
import com.smartroom.entity.SensorData;

import java.util.List;

public interface SensorDataService {
    SensorDataResponse createSensorData(SensorDataRequest sensorDataRequest);
    List<SensorDataResponse> getSensorDataByRoomId(Long roomId);
    SensorDataResponse getSensorDataById(Long dataId);
    List<SensorDataResponse> getAllSensorData();
    SensorDataResponse getLatestDataByRoom(Long roomId);
    List<SensorDataResponse> getDataHistoryForRoom(Long roomId);

}
