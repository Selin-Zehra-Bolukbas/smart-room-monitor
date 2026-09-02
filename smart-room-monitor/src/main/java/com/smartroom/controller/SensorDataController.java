package com.smartroom.controller;

import com.smartroom.dto.request.SensorDataRequest;
import com.smartroom.dto.response.SensorDataResponse;
import com.smartroom.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // "*" tüm adreslerden gelen isteklere izin ver (test için)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/smart-room-monitor/sensor-data")
public class SensorDataController {

    private final SensorDataService sensorDataService;

    @PostMapping
    public ResponseEntity<SensorDataResponse> createSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
        SensorDataResponse sensorDataResponse = sensorDataService.createSensorData(sensorDataRequest);
        return ResponseEntity.ok(sensorDataResponse);
    }

    @GetMapping("/room/{roomId}/latest")
    public ResponseEntity<SensorDataResponse> getLatestDataForRoom(@PathVariable Long roomId) {
        SensorDataResponse latestData = sensorDataService.getLatestDataByRoom(roomId);
        return ResponseEntity.ok(latestData);
    }

    @GetMapping("/room/{roomId}/history")
    public ResponseEntity<List<SensorDataResponse>> getDataHistoryForRoom(@PathVariable Long roomId) {
        List<SensorDataResponse> historyData = sensorDataService.getDataHistoryForRoom(roomId);
        return ResponseEntity.ok(historyData);
    }

}
