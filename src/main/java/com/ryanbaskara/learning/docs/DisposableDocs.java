package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.ResourceObserver;

import java.util.concurrent.TimeUnit;

public class DisposableDocs {
    public static void start() {
        System.out.println("=== Introduce Disposable ===");
        example1();
        example2();
        example3();
    }
    private static void example1() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = seconds.subscribe(l -> {
           System.out.println("Item: " + l);
        });

        if (disposable.isDisposed()) {
            disposable.dispose(); // if we don't need the stream again
        }
    }

    private static void example2() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        seconds.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        // free up resources and avoid memory leak and memory overhead
        compositeDisposable.dispose();
    }

    private static void example3() {
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        ResourceObserver<Long> resourceObserver = new ResourceObserver<Long>() {
            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.println("Item: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        seconds.subscribe(resourceObserver);
        // if we don't need new emission we can disposed it
        resourceObserver.dispose();
    }
}
