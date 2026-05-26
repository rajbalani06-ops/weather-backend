package com.weather.weatherbackend.service;

import com.weather.weatherbackend.dto.WeatherResponseDto;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String API_KEY = "218f877cab7ced253553ff4b4b502cb2";

    public WeatherResponseDto getWeather(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid="
                + API_KEY
                + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();

        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);

        WeatherResponseDto dto = new WeatherResponseDto();

        dto.setCity(json.getString("name"));

        dto.setTemperature(
                json.getJSONObject("main")
                        .getDouble("temp")
        );

        dto.setDescription(
                json.getJSONArray("weather")
                        .getJSONObject(0)
                        .getString("description")
        );

        return dto;
    }
}