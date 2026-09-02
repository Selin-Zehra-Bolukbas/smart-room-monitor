package com.smartroom.service.impl;

import com.smartroom.service.WeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Double getTomorrowTemperature() {

        String city = "Bilecik";
        String url = apiUrl + "?q=" + city + "&appid=" + apiKey + "&units=metric";

        Map response = restTemplate.getForObject(url, Map.class);

        List<Map> list = (List<Map>) response.get("list");
        Map firstForecast = list.get(0);
        Map main = (Map) firstForecast.get("main");

        return Double.parseDouble(main.get("temp").toString());
    }
}


