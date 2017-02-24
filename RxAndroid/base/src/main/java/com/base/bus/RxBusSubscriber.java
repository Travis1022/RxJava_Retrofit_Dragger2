package com.base.bus;


import io.reactivex.functions.Consumer;

/**
 * RxBus订阅者
 * Created by Travis1022 on 2017/02/24.
 */
public abstract class RxBusSubscriber<T> implements Consumer<RxEvent> {
    @Override
    public void accept(RxEvent event) throws Exception {
        onEvent(event.tag, (T) event.event);
    }

    protected abstract void onEvent(String tag, T t);
}
