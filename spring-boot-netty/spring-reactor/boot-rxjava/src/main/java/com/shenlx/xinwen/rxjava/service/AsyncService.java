package com.shenlx.xinwen.rxjava.service;

import com.shenlx.xinwen.rxjava.entity.User;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

/**
 * @author shenlx
 * @description
 * @date 2024/4/19 9:36
 */
public class AsyncService {
    public Observable<User> getUserId(String id){
        //User user=new User("1","小三","20");

        //将用户信息发送给订阅者
        Observable<User> observable = Observable.create(new ObservableOnSubscribe<User>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {
                User user=new User("1","小三","20");
                emitter.onNext(user);
                User user1=new User("2","小四","20");
                emitter.onNext(user1);
                User user2=new User("3","小五","20");
                emitter.onNext(user2);
                emitter.onComplete();
            }


        });

        Observable.create(emitter->{
            User user=new User("1","小三","20");
            emitter.onNext(user);
            User user1=new User("2","小四","20");
            emitter.onNext(user1);
            User user2=new User("3","小五","20");
            emitter.onNext(user2);
            emitter.onComplete();
        });


        return observable;
    }
}
