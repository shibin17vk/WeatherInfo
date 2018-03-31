package com.testapp.problem2.feature_weather.presenter;

/**
 * @author shibin
 * @version 1.0
 * @date 30/03/18
 */

public abstract class BasePresenter<T> {

    protected T callBack;

    public abstract void registerCallback(T callback);

    public abstract void unRegisterCallback();

}
