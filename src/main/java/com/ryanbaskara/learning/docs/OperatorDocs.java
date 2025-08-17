package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;

public class OperatorDocs {
    public static void start() {
        System.out.println("=== Introduce Operator ===");
        filter();
        take();
        skip();
        distinct();
        first();
        last();

        takeWhile();
        skipWhile();
        all();
        any();
        defaultIfEmpty();
        switchIfEmpty();
    }

    // ===== Filtering =====

    private static void filter() {
        System.out.println("--filter--");
        Observable.just("Hello", "my", "World!")
                .filter(item -> item.length() != 2)
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // World!
    }

    private static void take() {
        System.out.println("--take--");
        Observable.just("Hello", "my", "World!")
                .take(2)
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // my
    }

    private static void skip() {
        System.out.println("--skip--");
        Observable.just("Hello", "my", "World!")
                .skip(2)
                .subscribe(System.out::println);
        // prints ->
        // World!
    }

    private static void distinct() {
        System.out.println("--distinct--");
        Observable.just("Hello", "Hello", "hello")
                .distinct()
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // hello
    }

    private static void first() {
        System.out.println("--first--");
        Observable.just(2, 3, 4, 5)
                .first(-1)
                .subscribe(System.out::println);
        // prints ->
        // 2
    }

    private static void last() {
        System.out.println("--last--");
        Observable.just(2, 3, 4, 5)
                .last(-1)
                .subscribe(System.out::println);
        // prints ->
        // 5
    }

    // ===== Conditional =====

    private static void takeWhile() {
        System.out.println("--takeWhile--");
        Observable.just(2, 3, 4, 5)
                .takeWhile(item -> item <= 3)
                .subscribe(System.out::println);
        // prints ->
        // 2
        // 3
    }

    private static void skipWhile() {
        System.out.println("--skipWhile--");
        Observable.just(2, 3, 4, 5)
                .skipWhile(item -> item < 4)
                .subscribe(System.out::println);
        // prints ->
        // 4
        // 5
    }

    private static void all() {
        System.out.println("--all--");
        Observable.just("jack", "tuck", "luck")
                .all(item -> item.length() == 4)
                .subscribe(System.out::println);
        // prints ->
        // true
    }

    private static void any() {
        System.out.println("--any--");
        Observable.just("jack", "tuck", "luck")
                .any(item -> item.length() == 3)
                .subscribe(System.out::println);
        // prints ->
        // false
    }

    private static void defaultIfEmpty() {
        System.out.println("--defaultIfEmpty--");
        Observable.just("jack", "tuck", "luck")
                .filter(item -> item.length() == 3)
                .defaultIfEmpty("ABC")
                .subscribe(System.out::println);
        // prints ->
        // ABC
    }

    private static void switchIfEmpty() {
        System.out.println("--switchIfEmpty--");
        Observable.just("jack", "tuck", "luck")
                .filter(item -> item.length() == 3)
                .switchIfEmpty(Observable.just("Hello", "World!"))
                .subscribe(System.out::println);
        // prints ->
        // Hello
        // World!
    }
}
