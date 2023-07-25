package com.example.repository;

import com.example.entities.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends CrudRepository<Item, Long> {
    @Query("FROM Item")
    List<Item> getAllItems();

    @Query("From Item")
    List<Item> findAllItems();
}
