package ru.dstu.oop.laba5.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.dstu.oop.laba5.entities.Car;

public interface CarRepository extends CrudRepository<Car, Long> {
}
