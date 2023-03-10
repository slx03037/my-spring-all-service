package com.slx.springboot.jwt.util;

import lombok.extern.slf4j.Slf4j;
//import com.auth0.jwt.jwt;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-28 23:06
 **/

@Slf4j
public class JWTUtil {
    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @param secret   用户的密码
     * @return 加密的token
     */
    public static String sign(String username, String secret) {
        //Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        // 附带username信息
//        return jwt.create()
//                .withClaim("username", username)
//                .withExpiresAt(date)
//                .sign(algorithm);
    return  "";
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
//        Algorithm algorithm = Algorithm.HMAC256(secret);
//        JWTVerifier verifier = jwt.require(algorithm)
//                .withClaim("username", username)
//                .build();
//        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
//        DecodedJWT jwt = jwt.decode(token);
//        return jwt.getClaim("username").asString();
        return "";
    }
}
