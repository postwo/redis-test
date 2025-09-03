package com.altteulmoa;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                // 키값:users:300:email value: kim@fastcampus.co.kr
                jedis.set("users:300:email", "kim@fastcampus.co.kr");
                jedis.set("users:300:name", "kim 00");
                jedis.set("users:300:age", "100");

                // users:300:email 키의 값을 가져와 userEmail 변수에 저장
                var userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                // users:300:email, users:300:name, users:300:age 키의 값을 한 번에 가져옴
                List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                userInfo.forEach(System.out::println);

                // counter 키의 값을 1 증가시키고 결과를 출력
                long counter = jedis.incr("counter");
                System.out.println(counter);

                // counter 키의 값을 10만큼 증가시키고 결과를 출력
                counter = jedis.incrBy("counter", 10L);
                System.out.println(counter);

                // counter 키의 값을 1 감소시키고 결과를 출력
                counter = jedis.decr("counter");
                System.out.println(counter);

                // counter 키의 값을 20만큼 감소시키고 결과를 출력
                counter = jedis.decrBy("counter", 20L);
                System.out.println(counter);

                // 파이프라인 객체 생성: 여러 명령을 한 번에 Redis에 전송
                // 서버와 클라언트 데이터 응답 왕복시간이 짧아지므로 이방식이 좋다
                Pipeline pipelined = jedis.pipelined();

                // users:400 관련 키들을 파이프라인에 추가
                pipelined.set("users:400:email", "greg@fastcampus.co.kr");
                pipelined.set("users:400:name", "greg");
                pipelined.set("users:400:age", "15");

                // 파이프라인 명령들을 실행하고 결과를 리스트로 받음
                List<Object> objects = pipelined.syncAndReturnAll();

                // 각 명령의 결과 출력
                objects.forEach(i -> System.out.println(i.toString()));
            }
        }
    }
}