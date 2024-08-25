package com.example.webfluxdemo;

import com.example.webfluxdemo.models.Item;
import com.example.webfluxdemo.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Utils {

    @Bean
    public CommandLineRunner initData(ItemRepository itemRepository){
        return args->{
            Item item = new Item("item demo");
            itemRepository.save(item).subscribe();
        };
    }
}
