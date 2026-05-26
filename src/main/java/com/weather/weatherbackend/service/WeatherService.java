package com.weather.weatherbackend.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.weatherbackend.Exception.WeatherException;
import com.weather.weatherbackend.dto.WeatherResponseDto;

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

        if (response == null) {
            throw new WeatherException("No response from weather API");
        }

        JSONObject json = new JSONObject(response);

        if (json.has("cod") && !json.get("cod").toString().equals("200")) {
            String message = json.has("message") ? json.getString("message") : "City not found";
            throw new WeatherException(message);
        }

        WeatherResponseDto dto = new WeatherResponseDto();

        dto.setCity(json.optString("name", city));

        dto.setTemperature(
                json.getJSONObject("main").optDouble("temp", 0.0)
        );

        dto.setDescription(
                json.getJSONArray("weather")
                        .getJSONObject(0)
                        .optString("description", "N/A")
        );

        return dto;
    }
}