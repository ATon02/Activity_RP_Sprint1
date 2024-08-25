package com.example.webfluxdemo.services.impl;

import com.example.webfluxdemo.exception.ItemNotCreatedException;
import com.example.webfluxdemo.exception.ItemNotFoundException;
import com.example.webfluxdemo.exception.NotValidFieldException;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import com.example.webfluxdemo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Mono<Item> getById(long id) {
        return itemRepository.findById(id).switchIfEmpty(Mono.error(new ItemNotFoundException("Item with ID " + id + " not found")));
    }

    @Override
    public Flux<Item> getAll() {
        return itemRepository.findAll().switchIfEmpty(Flux.error(new ItemNotFoundException("Not found items")));
    }

    @Override
    public Mono<Item> save(Item item) {
        if (item.getName() == null || item.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("The 'name' field is empty"));
        }
        return itemRepository.save(item)
                .switchIfEmpty(Mono.error(new ItemNotCreatedException("Item not created")));
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return itemRepository.findById(id).switchIfEmpty(Mono.error(new ItemNotFoundException("Item with id " + id + " not found")))
                .flatMap(item-> {itemRepository.delete(item).subscribe();
            return Mono.empty();
        });
    }

    @Override
    public Mono<Item> update(Item item) {
        if (item.getName() == null || item.getName().isBlank()) {
            return Mono.error(new NotValidFieldException("The 'name' field is empty"));
        }
        if (item.getId() == null || item.getId()<=0) {
            return Mono.error(new NotValidFieldException("The 'id' field not is valid"));
        }
        return getById(item.getId()).flatMap(existItem-> {
            existItem.setName(item.getName());
            return itemRepository.save(existItem)
                    .switchIfEmpty(Mono.error(new ItemNotCreatedException("Item not updated")));
        });
    }
}
