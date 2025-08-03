package com.ryanbaskara.learning.presentation.handler;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.vertx.rxjava3.ext.web.RoutingContext;

public class InstrumentHandler {
    public void getHealth(RoutingContext ctx) {
        Single.just("Halo")
                .subscribe(
                        data -> System.out.println("Sukses: " + data),
                        error -> System.out.println("Gagal: " + error)
                );

        Observable.just("A", "B", "C")
                .subscribe(
                        item -> System.out.println("Item: " + item),
                        error -> System.err.println("Error: " + error),
                        () -> System.out.println("Selesai")
                );

        ctx.response().putHeader("Content-Type", "application/json").end("OK");
    }
}
