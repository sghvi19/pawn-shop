package com.example.repository;

import com.example.entities.Car;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends PagingAndSortingRepository<Car,Long> {
}
