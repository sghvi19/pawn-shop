package com.example.repository;

import com.example.entities.HouseholdAppliance;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseholdApplianceRepo extends PagingAndSortingRepository<HouseholdAppliance,Long> {
}
