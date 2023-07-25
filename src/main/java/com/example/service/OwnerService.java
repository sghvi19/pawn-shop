package com.example.service;

import com.example.entities.Owner;
import com.example.exception.OwnerAlreadyExistsException;
import com.example.exception.OwnerNotFoundException;
import com.example.repository.OwnerRepo;
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
public class OwnerService {
    private final OwnerRepo repo;

    @Autowired
    public OwnerService(OwnerRepo repo) {
        this.repo = repo;
    }

    @Cacheable("owners")
    public Iterable<Owner> getAll(final int page, final int pageSize) {
        log.info("getting all owners");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Owner getById(final long id) {
        log.info("getting owner by id");
        return repo.findById(id).orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with id: %d not found!", id)));
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner update(final long id,final  Owner owner) {
        if (!repo.existsById(id)) {
            log.info("owner does not exist");
            throw new OwnerNotFoundException(String.format("Owner with id: %d not found!", id));
        }

        if (repo.existsByPersonalNo(owner.getPersonalNo()) && repo.findOwnerByPersonalNo(owner.getPersonalNo()).getOwnerId() != id) {
            throw new OwnerAlreadyExistsException(String.format("Owner with id: %s not found!", owner.getPersonalNo()));
        }
        log.info("updating owner");
        owner.setOwnerId(id);
        repo.save(owner);
        return owner;
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner create(final Owner owner) {
        log.info("creating owner");
        if (repo.existsByPersonalNo(owner.getPersonalNo())) {
            throw new OwnerAlreadyExistsException(String.format("Owner with id: %s not found!", owner.getPersonalNo()));
        }
        if (repo.existsById(owner.getOwnerId())) {
            throw new OwnerAlreadyExistsException(String.format("Owner with id: %d already exists!", owner.getOwnerId()));
        }

        return repo.save(owner);
    }

    @CacheEvict(value = "owners", allEntries = true)
    public Owner remove(final long id) {
        log.info("removing owner");
        if (repo.existsById(id)) {
            Owner owner = repo.findById(id).get();
            repo.deleteById(id);
            return owner;
        }
        throw new OwnerNotFoundException(String.format("Owner with id: %d not found!", id));
    }
}
