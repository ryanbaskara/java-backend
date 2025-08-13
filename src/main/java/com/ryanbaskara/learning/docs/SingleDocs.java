package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class SingleDocs {
    public static void start() {
        System.out.println("=== Introduce single ===");
        single();
    }

    private static void single() {
        Single<String> single = createSingle();
        single.subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
            }
        });
    }

    private static Single<String> createSingle() {
        return Single.create(emitter -> {
            var user = fetchUser();
            if (user != null) {
                emitter.onSuccess(user);
            } else {
                emitter.onError(new Exception("User not found"));
            }
        });
    }

    private static String fetchUser() {
        return "John";
    }
}
