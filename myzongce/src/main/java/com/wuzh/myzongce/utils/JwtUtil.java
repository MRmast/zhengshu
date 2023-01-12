package com.wuzh.myzongce.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //设置过期时间
    private static final long EXPIRE_TIME = 240 * 60 * 1000;
    //token秘钥
    private static final String TOKEN_SECRET = "f26e587c28064d0e855e72c0a6a0e618";

    public static String sign(String username, String permission) {
        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username,password信息，生成签名
            return JWT.create()
                    .withHeader(header)
                    .withClaim("loginName", username)
                    .withClaim("permission", permission)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static boolean verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
            }
        }
        public static String parseJWT(String token){
            /**
             * @desc   解密token，返回一个map
             * @params [token]需要校验的串
             **/
            DecodedJWT decodeToken = JWT.decode(token);
            return decodeToken.getClaim("loginName").asString();
    }
        public static boolean isJwtExpired(String token){
            /**
             * @desc 判断token是否过期
             * @author lj
             */
        try {
            DecodedJWT decodeToken = JWT.decode(token);
            return decodeToken.getExpiresAt().before(new Date());
        } catch(Exception e){
            return true;
            }
        }
    }

