package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
//    @Override
//    public Store create(Store store) {
//        return storeRepository.save(store);
//    }
//
//    @Override
//    public Store getById(String id) {
//        return storeRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Store> getAll() {
//        return storeRepository.findAll();
//    }
//
//    @Override
//    public Store update(Store store) {
//        Store currentStore = getById(store.getId());
//        if (currentStore != null) return storeRepository.save(store);
//
////        if (!Objects.isNull(getById(store.getId()))) return storeRepository.save(store);
//
//        return null;
//    }
//
//    @Override
//    public void delete(String id) {
//        Store currentStore = getById(id);
//        if (currentStore != null) storeRepository.deleteById(id);
//        else System.out.println("Data with ID "+id+" not exist");
//    }


    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .build();
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = storeRepository.findById(id).orElse(null);
        return StoreResponse.builder()
                .id(store.getId())
                .noSiup(store.getNoSiup())
                .storeName(store.getName())
                .phone(store.getMobilePhone())
                .address(store.getAddress())
                .build();
    }

    @Override
    public List<StoreResponse> getAll() {
        List<Store> stores = storeRepository.findAll();
        return stores.stream().map(store -> StoreResponse.builder()
                .id(store.getId())
                .noSiup(store.getNoSiup())
                .storeName(store.getName())
                .phone(store.getMobilePhone())
                .address(store.getAddress())
                .build()).collect(Collectors.toList());
    }

    @Override
    public StoreResponse update(StoreRequest storeRequest) {
        StoreResponse currentStore = getById(storeRequest.getId());
        if (currentStore != null){
            Store store = Store.builder()
                    .id(storeRequest.getId())
                    .noSiup(storeRequest.getNoSiup())
                    .name(storeRequest.getName())
                    .mobilePhone(storeRequest.getMobilePhone())
                    .address(storeRequest.getAddress())
                    .build();
            storeRepository.save(store);
            return StoreResponse.builder()
                    .id(store.getId())
                    .noSiup(store.getNoSiup())
                    .storeName(store.getName())
                    .phone(store.getMobilePhone())
                    .address(store.getAddress())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        StoreResponse currentStore = getById(id);
        if (currentStore != null) storeRepository.deleteById(id);
    }
}
