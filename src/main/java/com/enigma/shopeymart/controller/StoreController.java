package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {
    private final StoreService storeService;

//    @PostMapping(value = "/store")
    @PostMapping
    public Store createStore(@RequestBody Store store){
        return storeService.create(store);
    }

//    @GetMapping(value = "/store/{id}")
    @GetMapping(value = "/{id}")
    public Store getByIdStore(@PathVariable String id){
        return storeService.getById(id);
    }

    @GetMapping
    public List<Store> getAllStore(){
        return storeService.getAll();
    }

//    @PutMapping(value = "/store/update")
    @PutMapping(value = "/update")
    public Store updateStore(@RequestBody Store store){
        return storeService.update(store);
    }

//    @DeleteMapping(value = "/store/{id}")
    @DeleteMapping(value = "/{id}")
    public void deleteStore(@PathVariable String id){
        storeService.delete(id);
    }

    @PostMapping(value = "/v1")
    public StoreResponse createStores(@RequestBody  StoreRequest storeRequest){
        return storeService.create(storeRequest);
    }
}
