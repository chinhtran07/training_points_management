package com.tps.components;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tps.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;

@Component
public class JwtService {
    private static final String SECRET = "99999999999999999999999999999999";
    private static final String USERNAME = "username";
    private static final int EXPIRE_TIME = 3600 * 24;

    public String generateTokenLogin(String username) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());

            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USERNAME, username);
            builder.expirationTime(generateExperationDate());

            JWTClaimsSet claimSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimSet);

            signedJWT.sign(signer);
            token = signedJWT.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return token;
    }

    private byte[] generateShareSecret() {
        byte[] shareSecret = new byte[32];
        shareSecret = SECRET.getBytes();
        return shareSecret;
    }

    private Date generateExperationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private JWTClaimsSet getClaimFromToken(String token) {
        JWTClaimsSet claim = null;
        try {
            SignedJWT signedJwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJwt.verify(verifier)) {
                return signedJwt.getJWTClaimsSet();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return claim;
    }

    public String getUsernameFormToken(String token) throws ParseException {
        String username = null;
        try {
            JWTClaimsSet claimsSet = getClaimFromToken(token);
            username = claimsSet.getStringClaim(USERNAME);
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return username;
    }

    private Boolean isTokenExpired(String token) {
        JWTClaimsSet claims = getClaimFromToken(token);
        Date expiration = claims.getExpirationTime();

        return expiration.before(new Date());
    }

    public Boolean validateTokenLogin(String token) throws ParseException {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        String username = getUsernameFormToken(token);
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        if (isTokenExpired(token)) {
            return false;
        }

        return true;
    }

}
