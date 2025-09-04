package com.altteulmoa;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try(var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try(Jedis jedis = jedisPool.getResource()) {
                // hset
                // "users:2:info" 해시 키에 "name" 필드와 값을 저장
                jedis.hset("users:2:info", "name", "greg2");

                // 사용자 정보를 담은 HashMap 생성
                var userInfo = new HashMap<String, String>();
                userInfo.put("email", "greg3@fastcampus.co.kr"); // 이메일 정보 추가
                userInfo.put("phone", "010-XXXX-YYYY");          // 전화번호 정보 추가

                // HashMap에 담긴 모든 필드를 "users:2:info" 해시에 저장
                jedis.hset("users:2:info", userInfo);

                // "users:2:info" 해시에서 "phone" 필드를 삭제
                jedis.hdel("users:2:info", "phone");

                // "users:2:info" 해시에서 "email" 필드의 값을 가져와 출력
                System.out.println(jedis.hget("users:2:info", "email"));

                // "users:2:info" 해시의 모든 필드와 값을 가져와 출력
                Map<String, String> user2Info = jedis.hgetAll("users:2:info");
                user2Info.forEach((k, v) -> System.out.printf("%s %s%n", k,v));

                // "users:2:info" 해시의 "visits" 필드 값을 30만큼 증가 (없으면 0에서 시작)
                jedis.hincrBy("users:2:info", "visits", 30);
            }
        }
    }
}