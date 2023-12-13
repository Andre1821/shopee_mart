package com.enigma.shopeymart.service;

import com.enigma.shopeymart.entity.AppUser;

public interface UserService {
    AppUser loadUserByUserId(String id);
}
