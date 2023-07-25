package com.example.service;

import com.example.entities.Jewelry;
import com.example.exception.ItemNotFoundException;
import com.example.repository.JewelryRepo;
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
public class JewelryService {

    private final JewelryRepo repo;

    @Autowired
    public JewelryService(JewelryRepo repo) {
        this.repo = repo;
    }

    @Cacheable("jewelries")
    public Iterable<Jewelry> getAll(final int page, final int pageSize) {
        log.info("getting all jewelries");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Jewelry getById(final long id) {
        log.info("getting jewelry by id");
        return repo.findById(id).orElseThrow(() -> new ItemNotFoundException(String.format("Jewelry with id: %d not found!", id)));
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry update(final long id, final Jewelry jewelry) {
        if (!repo.existsById(id)) {
            log.info("jewelry does not exist");
            throw new ItemNotFoundException(String.format("Jewelry with id: %d not found!", id));
        }
        log.info("updating jewelry");
        jewelry.setItemId(id);
        repo.save(jewelry);
        return jewelry;
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry create(final Jewelry jewelry) {
        log.info("creating jewelry");
        return repo.save(jewelry);
    }

    @CacheEvict(value = "jewelries", allEntries = true)
    public Jewelry remove(final long id) {
        log.info("removing jewelry");
        if (repo.existsById(id)) {
            Jewelry jewelry = repo.findById(id).get();
            repo.deleteById(id);
            return jewelry;
        }
        throw new ItemNotFoundException(String.format("Jewelry with id: %d not found!", id));
    }
}
