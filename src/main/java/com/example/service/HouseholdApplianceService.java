package com.example.service;

import com.example.entities.HouseholdAppliance;
import com.example.exception.ItemNotFoundException;
import com.example.repository.HouseholdApplianceRepo;
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
public class HouseholdApplianceService {
    private final HouseholdApplianceRepo repo;

    @Autowired
    public HouseholdApplianceService(HouseholdApplianceRepo repo) {
        this.repo = repo;
    }

    @Cacheable("households")
    public Iterable<HouseholdAppliance> getAll(final int page, final int pageSize) {
        log.info("getting all households");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public HouseholdAppliance getById(final long id) {
        log.info("getting appliance by id");
        return repo.findById(id).orElseThrow(() -> new ItemNotFoundException(String.format("HouseholdAppliance with id: %d not found!", id)));
    }

    @CacheEvict(value = "households", allEntries = true)
    public HouseholdAppliance update(final long id, final HouseholdAppliance appliance) {
        if (!repo.existsById(id)) {
            log.info("appliance does not exist");
            throw new ItemNotFoundException(String.format("HouseholdAppliance with id: %d not found!", id));
        }
        log.info("updating appliance");
        appliance.setItemId(id);
        repo.save(appliance);
        return appliance;
    }

    @CacheEvict(value = "households", allEntries = true)
    public HouseholdAppliance create(final HouseholdAppliance appliance) {
        log.info("creating appliance");
        return repo.save(appliance);
    }

    @CacheEvict(value = "households", allEntries = true)
    public HouseholdAppliance remove(final long id) {
        log.info("removing appliance");
        if (repo.existsById(id)) {
            HouseholdAppliance appliance = repo.findById(id).get();
            repo.deleteById(id);
            return appliance;
        }
        throw new ItemNotFoundException(String.format("HouseholdAppliance with id: %d not found!", id));
    }
}
