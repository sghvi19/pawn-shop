package com.example.service;

import com.example.entities.Branch;
import com.example.exception.BranchAlreadyExistsException;
import com.example.exception.BranchNotFoundException;
import com.example.repository.BranchRepo;
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
public class BranchService {

    private final BranchRepo repo;

    @Autowired
    public BranchService(BranchRepo repo) {
        this.repo = repo;
    }

    @Cacheable("branches")
    public Iterable<Branch> getAll(final int page, final int pageSize) {
        log.info("getting all branches");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Branch getById(final long id) {
        log.info("getting branch by id");
        return repo.findById(id).orElseThrow(() -> new BranchNotFoundException(String.format("Branch with id: %d not found!", id)));

    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch update(final long id, final Branch branch) {
        if (!repo.existsById(id)) {
            log.info("branch does not exist");
            throw new BranchNotFoundException(String.format("Branch with id: %d not found!", id));
        }
        log.info("updating branch");
        branch.setBranchId(id);
        repo.save(branch);
        return branch;
    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch create(final Branch branch) {
        log.info("creating branch");
        if (repo.existsById(branch.getBranchId())) {
            throw new BranchAlreadyExistsException(String.format("Branch with id: %d already exists!", branch.getBranchId()));
        }
        return repo.save(branch);
    }

    @CacheEvict(value = "branches", allEntries = true)
    public Branch remove(final long id) {
        log.info("removing branch");
        if (repo.existsById(id)) {
            Branch branch = repo.findById(id).get();
            repo.deleteById(id);
            return branch;
        }
        throw new BranchNotFoundException(String.format("Branch with id: %d not found!", id));
    }

}
