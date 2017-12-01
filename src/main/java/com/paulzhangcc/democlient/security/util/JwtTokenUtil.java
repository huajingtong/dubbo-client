package com.paulzhangcc.democlient.security.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author paul
 * @date 2017/11/30
 */
@Component
public class JwtTokenUtil {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.secret:1A871EB8542E1B49CDC597F4AF72FE13}")
    private String secret;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 生成 JWT Token
     *
     * @param session
     * @return
     */
    public String generate(User session) {
        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject(session.getUsername())
                .signWith(SignatureAlgorithm.HS512, secret) // 加密方式
                .compact();
        return token;
    }

    /**
     * 解析 JWT Token
     * <p>
     * 先从redis中获取，如果没有则解析token, 存在redis中
     *
     * @param token
     * @return
     */
    public User parser(String token) {

        if (token == null || token.length() == 0) {
            return null;
        }
        String username = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().getSubject();
        return new User(username, "", new ArrayList<>());

    }
}
