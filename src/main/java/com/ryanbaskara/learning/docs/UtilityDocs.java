package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class UtilityDocs {
    public static void start() {
        System.out.println("=== Introduce Utility ===");
        delay();
        timeout();
        observeOn();
        subscribeOn();
        doOnNext();
        doOnDispose();
        retry();
        onErrorReturnItem();
        onErrorResumeWith();
    }

    private static void delay() {
        System.out.println("--delay--");
        Observable.just("Hello World!")
                .delay(2, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        // prints after 2 second ->
        // Hello world!
    }

    private static void timeout() {
        System.out.println("--timeout--");
        Observable.just("Hello World!")
                .timeout(1, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        // prints ->
        // Hello World!
    }

    private static void observeOn() {
        System.out.println("--observeOn--");
        // will change the thread when observe item
        System.out.println(Thread.currentThread().getName());
        Observable.just("Hello World!")
                .observeOn(Schedulers.newThread())
                .subscribe(item -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(item);
                });
        // prints ->
        // main
        // RxNewThreadScheduler-1
        // Hello World!
    }

    private static void subscribeOn() {
        System.out.println("--subscribeOn--");
        // will change the thread when produce item
        System.out.println(Thread.currentThread().getName());
        Observable.just("Hello World!")
                .subscribeOn(Schedulers.newThread())
                .subscribe(item -> {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println(item);
                });
        // prints ->
        // main
        // RxNewThreadScheduler-1
        // Hello World!
    }

    private static void doOnNext() {
        System.out.println("--doOnNext--");
        Observable.just(1, 2)
                .doOnNext(item -> {
                    System.out.println("Log some info");
                }).filter(item -> item == 2)
                .subscribe(System.out::println);
        // prints ->
        // Log some info
        // Log some info
        // 2
    }

    private static void doOnDispose() {
        System.out.println("--doOnDispose--");
        Disposable disposable = Observable.timer(1, TimeUnit.SECONDS)
                .doOnDispose(() -> System.out.println("Disposed called"))
                .subscribe(System.out::println);

        disposable.dispose();

        // prints ->
        // Disposed called
    }

    // ===== Error handling operator =====

    private static void retry() {
        System.out.println("--retry--");
        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .retry(1)
                .subscribe(item -> {
                    System.out.println(item);
                }, throwable -> System.out.println(throwable.getMessage()));
        // prints ->
        // 1
        // 2
        // 1
        // 2
        // / by zero
    }

    private static void onErrorReturnItem() {
        System.out.println("--onErrorReturnItem--");
        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .onErrorReturnItem(-1)
                .subscribe(System.out::println,
                        throwable -> System.out.println(throwable.getMessage()));
        // prints ->
        // 1
        // 2
        // -1
    }

    private static void onErrorResumeWith() {
        System.out.println("--onErrorResumeWith--");
        Observable.just(2, 1, 0)
                .map(item -> 2 / item)
                .onErrorResumeWith(Observable.just(5, 6, 7))
                .subscribe(System.out::println);
        // prints ->
        // 1
        // 2
        // 5
        // 6
        // 7
    }
}
