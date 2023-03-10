package com.slx.springboot.webflux.test;

import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-19 21:52
 **/

public class MonoTest {
    public static void main(String[] args) {
        Mono.just("are").subscribe(System.out::println);
        Mono.empty().subscribe(System.out::println);
        Mono.fromSupplier(() -> "you").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("ok")).subscribe(System.out::println);

        Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
    }
}
