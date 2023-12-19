package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final RestTemplate restTemplate;
    private final PostRepository postRepository;

    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    public ResponseEntity<List<Posts>> getAll(){
        ResponseEntity<Posts[]> jsonData = restTemplate.getForEntity(BASE_URL, Posts[].class); // untuk mengambil data dari json placeholder

        List<Posts> externalData = List.of(jsonData.getBody()); //  mengambil list body data dari json placeholder

        List<Posts> internalDb = postRepository.findAll(); // mengambil semua data yang ada dalam db local

        internalDb.addAll(externalData); // didagabunin antara data di db local dan di db json place  holder

        return ResponseEntity.ok(internalDb); // untuk mengembalikan semua data yang sudah di gabung tadi
    }

    public ResponseEntity<String> getById(Long id){
        return responseMethod(restTemplate.getForEntity(BASE_URL+"/"+id,String.class), "Failed to load data");
    }

    public ResponseEntity<String> createposts(Posts posts){
        //Mengatur header permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // membungkus data permintaan dalam HttpEntity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts, headers);
        postRepository.save(posts);

        // response
        return responseMethod(restTemplate.postForEntity(BASE_URL, requestEntity, String.class), "Failed to create data");
    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> restTemplate, String message){
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(responseEntity.getStatusCode()).body(message);
    }
}
