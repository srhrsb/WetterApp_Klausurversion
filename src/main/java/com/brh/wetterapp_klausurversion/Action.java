package com.brh.wetterapp_klausurversion;

public interface Action<T,U> {

    void invoke( T value, U value2);
}
