package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ObservableDocs {

    public static void start() {
        System.out.println("Starting observable docs");
        just();
        iterable();
        range();
        interval();
        time();
        actionComplete();
    }

    private static void just() {
        System.out.println("=== Introduce just ===");
        Observable<String> observable = Observable.just("item 1", "item 2");
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("on complete called");
            }
        };
        observable.subscribe(observer);
    }

    private static void iterable() {
        System.out.println("=== Introduce iterable ===");
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);

        Observable<Integer> observable = Observable.fromIterable(numbers);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("on complete called");
            }
        };
        observable.subscribe(observer);
    }

    private static void range() {
        System.out.println("=== Introduce range ===");
        Observable<Integer> observable = Observable.range(2, 5);
        observable.subscribe(item -> {
//           System.out.println(item);
        });

    }

    private static void interval() {
        System.out.println("=== Introduce interval ===");
        Observable<Long> intervalObservable = Observable.interval(1, TimeUnit.SECONDS);
        intervalObservable.subscribe(item -> {
//            System.out.println(item);
        });
    }

    private static void time() {
        System.out.println("=== Introduce time ===");
        Observable<Long> timeObservable = Observable.timer(5, TimeUnit.SECONDS);
        timeObservable.subscribe(item -> {
            System.out.println("5 second passed");
        });
    }

    static void actionComplete() {
        System.out.println("=== Introduce action complete ===");
        Action action = () -> System.out.println("hello world");
        Completable completable = Completable.fromAction(action);
        completable.subscribe(() -> {
            System.out.println("Action ended");
        });
    }
}
