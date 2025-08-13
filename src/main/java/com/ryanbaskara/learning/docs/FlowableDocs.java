package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlowableDocs {
    public static void start() {
        System.out.println("=== Introduce flowable ===");
//        synchronousObservable();
//        asynchronousObservable();
        asyncFlowable();
    }

    private static void synchronousObservable() {
        Observable.range(1, 1000000)
                .map(id -> new Item(id))
                .subscribe(item -> {
                    Thread.sleep(1000);
                    System.out.println("Receive my item " + item.id + "\n");
                });
    }

    private static void asynchronousObservable() {
        // the main thread omit all the item
        // give the item to a new thread and it doesn't care about these item anymore
        // they processed on different thread and main thread doesn't care about them anymore
        // if > 1 million obj it will take heavy process and it will got out of memory
        // all item emitted once
        Observable.range(1, 1000000)//produces item
                .map(Item::new)
                .observeOn(Schedulers.io()) // asynchronous observe on different thread
                .subscribe(item -> { //consumes item
                    Thread.sleep(1000);
                    System.out.println("Receive my item " + item.id + "\n");
                });

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void asyncFlowable() {
        // will emitted in batches, will apply backpressure
        // will slow down our producer to avoid out of memory exception
        // will emit 128 item, after consumes done it will continue another portion
        Flowable.range(1, 1000000)
                .map(Item::new)
                .observeOn(Schedulers.io())
                .subscribe(item -> {
                    Thread.sleep(1000);
                    System.out.println("Receive my item " + item.id + "\n");
                });
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Item {
    int id;

    public Item(int id) {
        this.id = id;
        System.out.println("Item is created " + id);
    }
}
