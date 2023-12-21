package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServiceImplTest {
    //mock storeRepository
    private final StoreRepository storeRepository = mock(StoreRepository.class);

    // create StoreServicenya di instance
    private final StoreService storeService = new StoreServiceImpl(storeRepository);

    @Test
    void itShouldReturnStoreResponseWhenCreateNewStore() {
        StoreRequest dummyStoreRequest = new StoreRequest();
//        dummyStoreRequest.setId("asaefacae"); // id tidak bisa di test karena id dalam Store itu di generate otomatis dengan type UUID
        dummyStoreRequest.setName("Jaya Jaya Jaya");

        // data db request, response
        StoreResponse dummyStoreResponse = storeService.create(dummyStoreRequest);

        verify(storeRepository).save(any(Store.class));
//        assertEquals(dummyStoreRequest.getId(), dummyStoreResponse.getId());
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());
    }

    @Test
    void itShouldGetAllDataStoreResponWhenCallGetAll() {
        List<Store> dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1", "12", "Prediksi", "Ragunan", "0812"));
        dummyStore.add(new Store("2", "123", "Prediksi Jaya", "Ragunan", "08123"));
        dummyStore.add(new Store("3", "1234", "Prediksi Jaya Jaya", "Ragunan", "081234"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<StoreResponse> retriveStore = storeService.getAll();

        assertEquals(dummyStore.size(), retriveStore.size());

        for (int i = 0; i < dummyStore.size(); i++) {
            assertEquals(dummyStore.get(i).getName(), retriveStore.get(i).getStoreName());
        }
    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore() {
        String storeId = "1";
        Store dummyStore = new Store("1", "12", "Prediksi", "Ragunan", "0812");

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(dummyStore));

        StoreResponse storeResponse = storeService.getById(storeId);

        verify(storeRepository).findById(storeId); // untuk memverifikasi
        assertNotNull(storeResponse);
        assertEquals(storeId, storeResponse.getId());
        assertEquals("Prediksi", storeResponse.getStoreName());
    }

    @Test
    void itShouldUpdateDataStoreWhenCallUpdate() {
        Store existingStore = new Store("1", "13", "Prediksi", "Ragunan", "0813");

        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("1");
        dummyStoreRequest.setName("Andi");

        when(storeRepository.findById(dummyStoreRequest.getId())).thenReturn(Optional.of(existingStore));
        assertEquals(dummyStoreRequest.getId(), existingStore.getId());

        StoreResponse currentStore = storeService.getById(dummyStoreRequest.getId());
        verify(storeRepository).findById(dummyStoreRequest.getId());

        StoreResponse dummyStoreResponse = storeService.update(dummyStoreRequest);
        verify(storeRepository).save(any(Store.class));
        assertNotNull(currentStore);
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());
    }

    @Test
    void itShouldDeleteDataStoreOneWhenDeleteByIdStore() {
        String storeId = "1";
        Store dummyStore = new Store("1", "12", "Prediksi", "Ragunan", "0812");
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(dummyStore));

        storeService.delete(storeId);
        verify(storeRepository, times(1)).deleteById(storeId);
    }
}