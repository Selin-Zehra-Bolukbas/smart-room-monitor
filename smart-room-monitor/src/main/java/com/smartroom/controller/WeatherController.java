package com.smartroom.controller;

import com.smartroom.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/tomorrow")
    public Map<String, Double> getTomorrowWeather() {
        Double temp = weatherService.getTomorrowTemperature();
        return Map.of("temperature", temp);
    }
}

