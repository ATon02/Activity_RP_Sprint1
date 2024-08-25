package com.example.webfluxdemo.services;

import com.example.webfluxdemo.models.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {

    Mono<Item> getById(long id);

    Flux<Item> getAll();

    Mono<Item> save(Item item);

    Mono<Void> deleteById(Long id);

    Mono<Item> update(Item item);

}
