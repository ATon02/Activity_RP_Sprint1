package com.example.webfluxdemo.controllers;

import com.example.webfluxdemo.exception.ItemNotFoundException;
import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.services.ItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{id}")
    @Operation(summary = "Get Item By Id", description = "Return a item id to get.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task data found."),
            @ApiResponse(responseCode = "400", description = "Bad request: Item with ID not found", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<Item> getItemById(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get All Items", description = "Return all items of system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items data found."),
            @ApiResponse(responseCode = "400", description = "Bad request: Not found items",content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Flux<Item> getAllItems() {
        return itemService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create Item", description = "Save item in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task saved correctly"),
            @ApiResponse(responseCode = "400", description = "Bad request: Item Not Created or Bad request: The 'name' field is empty", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<Item> createItem(@RequestBody @Schema(example = "{\"name\": \"Item Test\"}") Item item) {
        return itemService.save(item);
    }

    @PutMapping("/")
    @Operation(summary = "Update Task By Id", description = "Update task in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task updated correctly"),
            @ApiResponse(responseCode = "400", description = "Bad request: Item not found or Bad request: The 'name' or 'id' field not is valid", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<Item> updateItem(@RequestBody @Schema(example = "{\"id\": 1,\"name\": \"Item Test Update\"}") Item item) {
        return itemService.update(item);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Item By Id", description = "Delete item in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item delete."),
            @ApiResponse(responseCode = "400", description = "Bad request: Item not found", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
    })
    public Mono<ResponseEntity<Object>> deleteItem(@PathVariable Long id) {
        return itemService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(ItemNotFoundException.class, ex -> Mono.just(ResponseEntity.badRequest().body(ex.getMessage())));
    }
}
