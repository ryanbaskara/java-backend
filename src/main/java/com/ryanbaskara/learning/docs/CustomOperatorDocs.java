package com.ryanbaskara.learning.docs;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOperator;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class CustomOperatorDocs {
    public static void start() {
        System.out.println("=== Introduce Custom Operator ===");
        customOperator();
    }

    private static void customOperator() {
//        Observable.just(1,2,3,4)
//                .filter(item -> item % 2 == 0 )
//                .subscribe(System.out::println);

        Observable.just(1,2,3,4)
                .lift(takeEven())
                .subscribe(System.out::println);
    }

    private static ObservableOperator<Integer, Integer> takeEven() {
        return new ObservableOperator<Integer, Integer>() {
            @Override
            public @NonNull Observer<? super Integer> apply(@NonNull Observer<? super Integer> observer) throws Throwable {
                return new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@NonNull Integer integer) {
                        if (integer % 2 == 0) {
                            observer.onNext(integer);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
            }
        };
    }
}
