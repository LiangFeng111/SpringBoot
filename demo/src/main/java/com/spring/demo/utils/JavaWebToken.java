package com.spring.demo.utils;

import com.sun.javafx.collections.MappingChange;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JavaWebToken {
    private static Logger log = LoggerFactory.getLogger(JavaWebToken.class);

    //该方法使用HS256算法和Secret:bankgl生成signKey
    private static Key getKeyInstance() {
        //We will sign our JavaWebToken with our ApiKey secret
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("abcdefghijkl");//加密，里面的字符串可自行定义
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**使用HS256签名算法和生成的signingKey最终的Token,claims中是有效载荷
     * @param claims  待转化的数据
     * @return  token字符串
     */
    public static String createJavaWebToken(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(nowMillis + 1000*60*60*24*7))//超时时间，设置为7天
                .setIssuedAt(now)
                .setNotBefore(now)
                .signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();
    }

    /**解析Token，同时也能验证Token，当验证失败返回null
     * @param jwt  token字符串
     * @return  解析的数据
     */
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed : " + e.getMessage());
            return null;
        }
    }
}
