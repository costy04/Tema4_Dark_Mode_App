package com.DarkMode.Aplication.services;

import com.DarkMode.Aplication.entities.Car;
import com.DarkMode.Aplication.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car saveCar (Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars () {
        return carRepository.findAll();
    }
}
