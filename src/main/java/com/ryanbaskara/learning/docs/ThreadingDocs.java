package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThreadingDocs {
    public static void start() {
        System.out.println("=== Introduce Threading ===");
        normalSubscribe();
        subscribeOn();
        observeOn();
    }

    private static void normalSubscribe() {
        System.out.println("-- Normal subscribe --");
        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(item -> System.out.println("Pushing item " + item + " on " + Thread.currentThread().getName() + " thread"))
                .subscribe(item -> System.out.println("Receive item " + item + " on " + Thread.currentThread().getName() + " thread \n"));

        // prevent Main thread of this console application to finish
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // prints ->
        // Pushing item 1 on main thread
        // Receive item 1 on main thread
        //
        // Pushing item 2 on main thread
        // Receive item 2 on main thread
        //
        // Pushing item 3 on main thread
        // Receive item 3 on main thread
        //
        // Pushing item 4 on main thread
        // Receive item 4 on main thread
        //
        // Pushing item 5 on main thread
        // Receive item 5 on main thread
    }

    private static void subscribeOn() {
        System.out.println("-- Subscribe on --");
        Observable.just(1, 2, 3, 4, 5)
                .doOnNext(item -> System.out.println("Pushing item " + item + " on " + Thread.currentThread().getName() + " thread"))
                .subscribeOn(Schedulers.computation())
                .subscribe(item -> System.out.println("Receive item " + item + " on " + Thread.currentThread().getName() + " thread \n"));

        // prevent Main thread of this console application to finish
        // if we remove this, it will do nothing because main thread is finished
        // when main thread finished, the other thread will also finished
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // prints ->
        // Pushing item 1 on RxComputationThreadPool-1 thread
        // Receive item 1 on RxComputationThreadPool-1 thread
        //
        // Pushing item 2 on RxComputationThreadPool-1 thread
        // Receive item 2 on RxComputationThreadPool-1 thread
        //
        // Pushing item 3 on RxComputationThreadPool-1 thread
        // Receive item 3 on RxComputationThreadPool-1 thread
        //
        // Pushing item 4 on RxComputationThreadPool-1 thread
        // Receive item 4 on RxComputationThreadPool-1 thread
        //
        // Pushing item 5 on RxComputationThreadPool-1 thread
        // Receive item 5 on RxComputationThreadPool-1 thread
    }

    private static void observeOn() {
        System.out.println("-- observeOn --");
        Observable.just("Hello World!")
                .subscribeOn(Schedulers.computation())
                .doOnNext(item -> System.out.println("Emitting item " + item + " on " + Thread.currentThread().getName() + " thread"))
                .observeOn(Schedulers.single())
                .subscribe(item -> System.out.println("Observing item " + item + " on " + Thread.currentThread().getName() + " thread \n"));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // prints ->
        // Emitting item Hello World! on RxComputationThreadPool-1 thread
        // Observing item Hello World! on RxSingleScheduler-1 thread
    }
}
