package com.zaycev.vmusic.utils;

public interface Observable {
        void registerObserver(Observer observer);
        void removeObserver(Observer observer);
        void notifyObservers();
    }
