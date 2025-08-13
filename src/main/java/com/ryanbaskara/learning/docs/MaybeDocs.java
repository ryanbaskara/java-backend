package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class MaybeDocs {
    public static void start() {
        System.out.println("=== Introduce maybe ===");
        maybe();
    }

    private static void maybe() {
        Maybe<String> maybe = createMaybe();
        maybe.subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull String s) {
                System.out.println(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("no new content");
            }
        });
    }

    private static Maybe<String> createMaybe() {
        return Maybe.create(emitter -> {
            var newContent = readFile();
            if (newContent != null) {
                emitter.onSuccess(newContent);
            } else {
                emitter.onComplete();
            }
        });
    }

    private static String readFile() {
        return "new content is here";
    }
}
