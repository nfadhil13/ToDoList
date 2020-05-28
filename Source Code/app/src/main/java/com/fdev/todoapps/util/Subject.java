package com.fdev.todoapps.util;

public interface Subject {
    void registerObserver(DateObserver repositoryObserver);
    void removeObserver(DateObserver repositoryObserver);
    void notifyObservers();
}
