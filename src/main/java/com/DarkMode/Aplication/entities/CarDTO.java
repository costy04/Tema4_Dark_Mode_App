package com.DarkMode.Aplication.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CarDTO {
    private List<Car> cars;
    private boolean darkMode;
}
