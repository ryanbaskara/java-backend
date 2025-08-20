package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.*;

import java.util.concurrent.TimeUnit;

public class SubjectDocs {
    public static void start() {
        System.out.println("=== Introduce Subject ===");
        publishSubject();
        behaviorSubject();
        replySubject();
        asyncSubject();
    }

    private static void publishSubject() {
        System.out.println("--publish subject--");

        // create two source
        Observable<Long> source1 = Observable.interval(1, TimeUnit.SECONDS);
        Observable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS);

        // create PublishSubject object
        Subject<Long> subject = PublishSubject.create();

        // subscribe to the PublishSubject object
        subject.subscribe(item -> System.out.println("Receive item " + item));

        // we still need to tell our subject that it will receive items
        // from different sources
        source1.subscribe(subject);
        source2.subscribe(subject);

        // prevent our program from exiting
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // prints ->
        // Receive item 0
        // Receive item 0
        // Receive item 1
        // Receive item 1
        // Receive item 2
        // Receive item 2
        // Receive item 3
        // Receive item 3
        // ...
    }

    private static void behaviorSubject() {
        System.out.println("--behavior subject--");

        // represent radio which play songs in some room
        Subject<Integer> subject = BehaviorSubject.create();

        // person 1 enter the room and starts listening to radio
        subject.subscribe(item -> System.out.println("Person 1 listening to song: " + item));

        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        // person 2 enter the room and starts listening to radio
        subject.subscribe(item -> System.out.println("Person 2 listening to song: " + item));

        // prints ->
        // Person 1 listening to song: 1
        // Person 1 listening to song: 2
        // Person 1 listening to song: 3
        // Person 2 listening to song: 3
    }

    private static void replySubject() {
        System.out.println("--reply subject--");
        // represent teacher
        Subject<Integer> subject = ReplaySubject.create();

        // student A entered classroom
        subject.subscribe(item -> System.out.println("Student A receive " + item));

        // teacher talks about some boring topics
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        // student B enters the classroom, but he is late
        subject.subscribe(item -> System.out.println("Student B receive " + item));

        // prints ->
        // Student A receive 1
        // Student A receive 2
        // Student A receive 3
        // Student B receive 1
        // Student B receive 2
        // Student B receive 3
    }

    private static void asyncSubject() {
        System.out.println("--async subject--");
        // represent teacher
        Subject<Integer> subject = AsyncSubject.create();

        // student A entered classroom
        subject.subscribe(item -> System.out.println("Student A receive " + item));

        // teacher talks about some boring topics
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        // student B enters the classroom
        subject.subscribe(item -> System.out.println("Student B receive " + item));

        // teacher talks very important
        subject.onNext(4);

        // teacher ends the lesson
        subject.onComplete();

        // prints ->
        // Student A receive 4
        // Student B receive 4
    }
}
