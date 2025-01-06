package com.Demo.journalApplication.service;

import com.Demo.journalApplication.api.response.weatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class weatherService {

	@Value("${open_weather.api_key}")
	private String API_KEY;

	@Value("${open_weather.api_url}")
	private String BASE_URL;

	@Autowired
	public RestTemplate restTemplate;

	public String getWeather() {
		String url = BASE_URL + "?lat=19.0760&lon=72.8777&units=metric&appid=" + API_KEY;
		log.info("URL: " + url );
		log.info("API_KEY: " + API_KEY);
		log.info("BASE_URL: " + BASE_URL);
		ResponseEntity<weatherResponse> response = restTemplate.exchange(url, HttpMethod.GET,null, weatherResponse.class);
		weatherResponse wtResponse = response.getBody();
		log.info("Response: " + wtResponse.getMain().getTemp());
		if(wtResponse != null) {
			return wtResponse.getMain().getTemp().toString();
		}
		else {
			return "";
		}
	}
}
