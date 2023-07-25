package com.example.repository;

import com.example.entities.Owner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepo extends PagingAndSortingRepository<Owner, Long> {

    boolean existsByPersonalNo(String personalNo);
    Owner findOwnerByPersonalNo(String personalNo);
}
