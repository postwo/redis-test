package com.altteulmoa;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {
                {
                    // 📦 리스트 자료구조 사용
                    // 1. Stack 구현 (후입선출: LIFO)
//                    jedis.rpush("stack1", "aaaa"); // 리스트 오른쪽에 값 추가
//                    jedis.rpush("stack1", "bbbb");
//                    jedis.rpush("stack1", "ccc");
//
//                    System.out.println(jedis.rpop("stack1")); // 오른쪽에서 값 꺼냄 → "ccc"
//                    System.out.println(jedis.rpop("stack1")); // → "bbbb"
//                    System.out.println(jedis.rpop("stack1")); // → "aaaa"
//
//                    List<String> stack1 = jedis.lrange("stack1", 0, -1); // 리스트 전체 조회
//                    stack1.forEach(System.out::println); // 남은 요소 출력 (현재는 비어 있음)
//
//                    // 2. Queue 구현 (선입선출: FIFO)
//                    jedis.rpush("queue2", "zzzz"); // 오른쪽에 값 추가
//                    jedis.rpush("queue2", "aaaa");
//                    jedis.rpush("queue2", "cccc");
//
//                    System.out.println(jedis.lpop("queue2")); // 왼쪽에서 값 꺼냄 → "zzzz"
//                    System.out.println(jedis.lpop("queue2")); // → "aaaa"
//                    System.out.println(jedis.lpop("queue2")); // → "cccc"
//
//                    // 3. Blocking Queue (BLPOP)
//                    // queue:blocking 리스트에서 값이 들어올 때까지 최대 10초 대기
//                    while (true) {
//                        List<String> blpop = jedis.blpop(10, "queue:blocking"); // [key, value] 형식으로 반환
//                        if (blpop != null) {
//                            blpop.forEach(System.out::println); // 대기 후 값이 들어오면 출력
//                        }
//                    }

                    // set을 실행할때는 list를 주석 처리 list를 실행하고 싶으면 set을 주석 처리
                    // 🧑‍🤝‍🧑 집합(Set) 자료구조 사용
                    jedis.sadd("users:500:follow", "100", "200", "300"); // 팔로우한 사용자 ID 추가
                    jedis.srem("users:500:follow", "100"); // 특정 사용자 ID 제거

                    Set<String> smembers = jedis.smembers("users:500:follow"); // 현재 팔로우 목록 조회
                    smembers.forEach(System.out::println); // 출력: "200", "300"

                    System.out.println(jedis.sismember("users:500:follow", "200")); // "200"이 포함되어 있는지 확인 → true
                    System.out.println(jedis.sismember("users:500:follow", "120")); // "120"이 포함되어 있는지 확인 → false

                    System.out.println(jedis.scard("users:500:follow")); // 집합의 크기 반환 → 2

                    Set<String> sinter = jedis.sinter("users:500:follow", "users:100:follow"); // 두 집합의 교집합 조회
                    sinter.forEach(System.out::println); // 공통 팔로우 사용자 출력
                }
            }
        }
    }
}