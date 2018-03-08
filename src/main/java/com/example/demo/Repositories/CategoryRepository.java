package com.example.demo.Repositories;

import com.example.demo.Models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    //Find all items that are either lost or found
    Iterable <Category> findByNameContainingIgnoreCase(String name);
    Category findByName(String name);

}

