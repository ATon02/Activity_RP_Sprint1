package com.example.webfluxdemo.repositories;

import com.example.webfluxdemo.models.Item;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
}