package com.shenlx.xinwen.rxjava.demo;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shenlx
 * @description
 * @date 2024/4/19 9:40
 */
@Slf4j
public class Demo {
    public static void handler(){
        //创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                log.info("Tag:{}", "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                log.info("Tag:-{}", value);
            }

            @Override
            public void onError(Throwable e) {
                log.info("Tag:{}", "error");
            }

            @Override
            public void onComplete() {
                log.info("Tag:{}", "complete");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    public static void handler2(){
        //创建一个上游 Observable：
       Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
        //创建一个下游 Observer

            @Override
            public void onSubscribe(Disposable d) {
                log.info("Tag:{}", "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                log.info("Tag:-{}", value);
            }

            @Override
            public void onError(Throwable e) {
                log.info("Tag:{}", "error");
            }

            @Override
            public void onComplete() {
                log.info("Tag:{}", "complete");
            }
        });
        //建立连接
        //observable.subscribe(observer);
    }

    public static void main(String[]args){
        handler();
    }
}
