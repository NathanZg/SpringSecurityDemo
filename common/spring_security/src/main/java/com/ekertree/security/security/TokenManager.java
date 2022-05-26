package com.ekertree.security.security;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * ClassName: TokenManager
 * Description:
 * date: 2022/5/25 11:27
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Component
public class TokenManager {

    //有效时长
    private long tokenEcpiration = 24 * 60 * 60 * 1000;

    //密钥
    private String tokenSignKey = "ekertree";

    //1.根据用户名生成token
    public String createToken(String username){
        String token = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+tokenEcpiration))
                .signWith(SignatureAlgorithm.HS512,tokenSignKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }


    //2.根据token字符串得到用户信息
    public String getUserInfoFromToken(String token){
        return Jwts.parser()
                .setSigningKey(tokenSignKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //3.删除token
    public void removeToken(String token){

    }

}
