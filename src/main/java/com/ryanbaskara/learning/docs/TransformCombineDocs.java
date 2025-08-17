package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;

public class TransformCombineDocs {
    public static void start() {
        System.out.println("=== Introduce Transforming and Combining ===");
        map();
        sorted();
        scan();
        buffer();
        groupBy();
        flatMap();
        toList();

        mergeWith();
        zipWith();
    }

    private static void map() {
        System.out.println("--map--");
        Observable.just(1, 2, 3)
                .map(item -> String.valueOf(item)) // transform one type to another type
                .subscribe(System.out::println);
        // prints ->
        // "1"
        // "2"
        // "3"
    }

    private static void sorted() {
        System.out.println("--sorted--");
        Observable.just(3, 2, 1)
                .sorted()
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // 3
    }

    private static void scan() {
        System.out.println("--scan--");
        Observable.just(3, 2, 1)
                .scan((accumulator, item) -> accumulator + item) // will sum with previous item
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 3
        // 6
    }

    private static void buffer() {
        System.out.println("--buffer--");
        Observable.range(0, 10)
                .buffer(3)// emit item in chunks
                .subscribe(System.out::println);
        // prints ->
        // [0, 1, 2]
        // [3, 4, 5]
        // [6, 7, 8]
        // [9]
    }

    private static void groupBy() {
        System.out.println("--groupBy--");
        Observable.just("a", "a", "bb", "bb", "bb", "ccc", "ccc")
                .groupBy(item -> item.length())
                .flatMapSingle(group -> group.toList())
                .subscribe(System.out::println);
        // prints ->
        // [a, a]
        // [bb, bb, bb]
        // [ccc, ccc]
    }

    private static void flatMap() {
        // return func observable of map
        // used for async operator in the stream
        System.out.println("--flatMap--");
        Observable.just(1, 2, 3)
                .flatMap(item -> Observable.just(item * 2))
                .subscribe(System.out::println);
        // prints ->
        // 2
        // 4
        // 6
    }

    private static void toList() {
        System.out.println("--toList--");
        Observable.just(1, 2, 3)
                .toList()
                .subscribe(System.out::println);
        // prints ->
        // [1, 2, 3]
    }

    // Combining observable

    private static void mergeWith() {
        System.out.println("--mergeWith--");
        Observable.just(1, 2)
                .mergeWith(Observable.just(3, 4))
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // 3
        // 4
    }

    private static void zipWith() {
        var obs1 = Observable.just("A", "B");
        var obs2 = Observable.just("C", "D");
        obs1.zipWith(obs2, (item1, item2) -> {
            return String.format("%s%s", item1, item2);
        }).subscribe(finalResult -> System.out.println("Item: " + finalResult));
    }
    // prints ->
    // Item AC
    // Item BD
}
