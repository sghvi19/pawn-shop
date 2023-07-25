package com.example.repository;

import com.example.entities.Branch;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepo extends PagingAndSortingRepository<Branch,Long> {
}
