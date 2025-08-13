package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;

public class CompletableDocs {
    public static void start() {
        System.out.println("=== Introduce completable ===");
        completable();
    }

    private static void completable() {
        Completable completable = createCompletable();
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("Operation is complete");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    private static Completable createCompletable() {
        return Completable.fromAction(deleteItemFromDBAction());
    }

    private static Action deleteItemFromDBAction() {
        return new Action() {
            @Override
            public void run() throws Throwable {
                System.out.println("Deleting item from DB");
            }
        };
    }
}
