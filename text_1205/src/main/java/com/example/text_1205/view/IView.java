package com.example.text_1205.view;

public interface IView<E> {
    void success(E e);
    void error(String str);
}
