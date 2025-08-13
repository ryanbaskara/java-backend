package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

public class HotColdObservableDocs {
    public static void start() {
        System.out.println("=== Introduce hot vs cold observable ===");
//        coldObservable();
        hotObservable();
    }

    private static void coldObservable() {
        // analogy: imagine there's room and in the room has radio
        // and there's a person in the room, after the 10 songs emitted
        // from our radio "cold observable" another person enter the room
        // this person can't hear the songs which were played before this person
        Observable<String> observable = Observable.just("a", "b", "c");

        observable.subscribe((item) -> System.out.println("Observer 1 - " + item));
        observable.subscribe((item) -> System.out.println("Observer 2 - " + item));
        observable.subscribe((item) -> System.out.println("Observer 3 - " + item));
    }

    private static void hotObservable() {
        // analogy: imagine in cinema have some rooms and in the room there's
        // a movie to be played. of course you don't want to show movie for one person
        // persons is subscriber and movie is hot observable
        // wait until full room, when you have full room you can call connect method
        ConnectableObservable<Long> observable = Observable.interval(1, TimeUnit.SECONDS)
                .publish(); // publish transform from cold to hot observable
        observable.connect(); // hot observable will start emitting item

        observable.subscribe((item) -> {
            System.out.println("Observer 1, sec: " + item);
        }); // subscribe without connect will not start observable

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // the observer 2 doesn't has previous emission,
        // will start from 5, 6, 7

        observable.subscribe((item) -> {
            System.out.println("Observer 2, sec: " + item);
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
