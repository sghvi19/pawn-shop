package com.example.repository;

import com.example.entities.Jewelry;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JewelryRepo extends PagingAndSortingRepository<Jewelry,Long> {
}
