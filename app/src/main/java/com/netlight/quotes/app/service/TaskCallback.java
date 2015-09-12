package com.netlight.quotes.app.service;

/**
 * Created by n06963 on 12/09/15.
 */
public interface TaskCallback<T> {
    public void onTaskFinished(T response);
}