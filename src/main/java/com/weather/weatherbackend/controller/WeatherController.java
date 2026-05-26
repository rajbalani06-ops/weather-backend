package com.weather.weatherbackend.controller;

import com.weather.weatherbackend.dto.WeatherResponseDto;
import com.weather.weatherbackend.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@CrossOrigin("*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public WeatherResponseDto getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }
}