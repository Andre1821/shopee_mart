package com.enigma.shopeymart.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.shopeymart.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    //generate token
    //get data by username
    //validation

    @Value("${app.shopeymart.jwt.jwt-secret}")
    private String jwtSecret;

    @Value("${app.shopeymart.jwt.app-name}")
    private String appName;

    @Value("${app.shopeymart.jwt.jwtExpirationInSecond}")
    private long jwtExpirationInSecond;

    public String generateToken(AppUser appUser){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            String token = JWT.create()
                    .withIssuer(appName) // info untuk nama application yang kita buat
                    .withSubject(appUser.getId()) // menentukan object yang akan dibuat biasanya dari ID
                    .withExpiresAt(Instant.now().plusSeconds(jwtExpirationInSecond)) // menetapkan waktu kadaluarsa token nanti, dalam sini kadalarsanya adalah 60 detik setelah dibuat
                    .withIssuedAt(Instant.now()) // menetapkan waktu token kapan dibuat
                    .withClaim("role", appUser.getRole().name()) // menambahkan claim atau info nama pengguna
                    .sign(algorithm); // ini tu jelasinya gimana yaa? intinya ini itu untuk seperti ttd kontrak bahwa algoritma yang kita pakai itu udah pasti HMAC256
            return token;
        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
    }

    public boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build(); // verifikasi  apakah algonya udah sesuai atau belom
            DecodedJWT decodedJWT = verifier.verify(token); // dari encode kemudian di decode disini
            return decodedJWT.getIssuer().equals(appName);
        }catch (JWTVerificationException e){
            throw  new RuntimeException();
        }
    }

    public Map<String, String> getUserInfoByToken(String token){ // untuk mengambil info
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject()); // save user ID
            userInfo.put("role", decodedJWT.getClaim("role").asString()); // save role
            return userInfo;
        } catch (JWTVerificationException e){
            throw new RuntimeException();
        }
    }
}
