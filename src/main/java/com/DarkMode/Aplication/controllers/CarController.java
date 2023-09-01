package com.DarkMode.Aplication.controllers;

import com.DarkMode.Aplication.entities.Car;
import com.DarkMode.Aplication.entities.CarDTO;
import com.DarkMode.Aplication.entities.DarkModeDTO;
import com.DarkMode.Aplication.services.CarService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class CarController {
    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.saveCar(car), HttpStatus.CREATED);
    }

    @GetMapping(value = "/cars", produces = "application/json")
    public ResponseEntity<CarDTO> getCars(
            @CookieValue(name = "darkMode", defaultValue = "default") String cookieHeader
    ) {

        boolean darkMode = false;

        System.out.println(cookieHeader);
        if (!Objects.equals(cookieHeader, "default")){
            darkMode = Boolean.parseBoolean(cookieHeader);
        }

        // All cars
        List<Car> cars = carService.getAllCars();

        CarDTO responseDTO = new CarDTO(cars, darkMode);

        return new ResponseEntity<>(responseDTO, HttpStatus.OK) ;
    }

    @PutMapping("/dark-mode")
    public ResponseEntity<String> setDarkMode(
            @RequestBody DarkModeDTO darkModeDTO
    ) {
        boolean darkModePreference = darkModeDTO.isDarkMode();

        // Create a new cookie to store the dark mode preference
        ResponseCookie cookie = ResponseCookie.from("darkMode", Boolean.toString(darkModeDTO.isDarkMode()))
                .httpOnly(true)     // true means that the cookie cannot be accessed by client-side script
                .secure(false)      // true means that the cookie will only be sent over secure connections like HTTPS
                .path("/")          // path of the cookie
                .maxAge(Duration.ofDays(7)) // expiration time. here 7 days
                .build();

        // Return a Response with SET_COOKIE header
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Cookie has been set");
    }

}
