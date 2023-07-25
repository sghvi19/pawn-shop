package com.example.service;

import com.example.entities.Car;
import com.example.exception.ItemNotFoundException;
import com.example.repository.CarRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {
    private final CarRepo repo;

    @Autowired
    public CarService(CarRepo repo) {
        this.repo = repo;
    }

    @Cacheable("cars")
    public Iterable<Car> getAll(final int page, final int pageSize) {
        log.info("getting all cars");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Car getById(final long id) {
        log.info("getting car by id");
        return repo.findById(id).orElseThrow(() -> new ItemNotFoundException(String.format("Car with id: %d not found!", id)));
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car update(final long id, final Car car) {
        if (!repo.existsById(id)) {
            log.info("car does not exist");
            throw new ItemNotFoundException(String.format("Car with id: %d not found!", id));
        }
        log.info("updating car");
        car.setItemId(id);
        repo.save(car);
        return car;
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car create(final Car car) {
        log.info("creating car");
        return repo.save(car);
    }

    @CacheEvict(value = "cars", allEntries = true)
    public Car remove(final long id) {
        log.info("removing car");
        if (repo.existsById(id)) {
            Car car = repo.findById(id).get();
            repo.deleteById(id);
            return car;
        }
        throw new ItemNotFoundException(String.format("Car with id: %d not found!", id));
    }
}
