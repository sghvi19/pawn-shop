package com.example.service;

import com.example.entities.Material;
import com.example.exception.MaterialAlreadyExistsException;
import com.example.exception.MaterialNotFoundException;
import com.example.repository.MaterialRepo;
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
public class MaterialService {
    private final MaterialRepo repo;

    @Autowired
    public MaterialService(MaterialRepo repo) {
        this.repo = repo;
    }

    @Cacheable("materials")
    public Iterable<Material> getAll(final int page, final int pageSize) {
        log.info("getting all materials");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Material getById(final long id) {
        log.info("getting material by id");
        return repo.findById(id).orElseThrow(() -> new MaterialNotFoundException(String.format("Material with id: %d not found!", id)));
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material update(final long id, final Material material) {
        if (!repo.existsById(id)) {
            log.info("material does not exist");
            throw new MaterialNotFoundException(String.format("Material with id: %d not found!", id));
        }
        log.info("updating material");
        material.setMaterialId(id);
        repo.save(material);
        return material;
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material create(final Material material) {
        log.info("creating material");
        if (repo.existsById(material.getMaterialId())) {
            throw new MaterialAlreadyExistsException(String.format("Material with id: %d already exists!", material.getMaterialId()));
        }
        return repo.save(material);
    }

    @CacheEvict(value = "materials", allEntries = true)
    public Material remove(final long id) {
        log.info("removing material");
        if (repo.existsById(id)) {
            Material material = repo.findById(id).get();
            repo.deleteById(id);
            return material;
        }
        throw new MaterialNotFoundException(String.format("Material with id: %d not found!", id));
    }

}
