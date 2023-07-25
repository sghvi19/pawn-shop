package com.example.repository;

import com.example.entities.Material;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepo extends PagingAndSortingRepository<Material,Long> {
}
