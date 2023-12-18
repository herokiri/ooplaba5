package ru.dstu.oop.laba5.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.dstu.oop.laba5.entities.Car;
import ru.dstu.oop.laba5.repositories.CarRepository;
import ru.dstu.oop.laba5.services.CarService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/moderation")
public class CarController {
    private final CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String getAllCars(Model model) {
        List<Car> cars = (List<Car>) carService.getAllCars();
        model.addAttribute("cars", cars);
        return "moderation";
    }

    @GetMapping("/{id}")
    public String getCarById(@PathVariable("id") Long id, Model model) {
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "carDetails";
    }

    @GetMapping("/create")
    public String showCreateCarForm(Model model) {
        model.addAttribute("car", new Car());  // передаем пустой объект Car в представление
        return "carForm.html";  // возвращаем имя Thymeleaf представления
    }

    @PostMapping("/rent-car")
    public String rentCar(@RequestParam Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setInArend(false);
            carRepository.save(car);
            return "main";
        }
        return "main";
    }

    @PostMapping("/unrent-car")
    public String unrentCar(@RequestParam Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setInArend(true);
            carRepository.save(car);
            return "main";
        }
        return "main";
    }


    @PostMapping
    public String createCar(Car car) {
        carService.createCar(car);
        return "redirect:/cars";
    }

    @PutMapping("/{id}")
    public String updateCar(@PathVariable("id") Long id, Car updatedCar) {
        Car car = carService.updateCar(id, updatedCar);
        if (car != null) {
            return "redirect:/cars";
        } else {
            return "errorPage";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable("id") Long id) {
        boolean result = carService.deleteCar(id);
        if (result) {
            return "redirect:/cars";
        } else {
            return "errorPage";
        }
    }
}

