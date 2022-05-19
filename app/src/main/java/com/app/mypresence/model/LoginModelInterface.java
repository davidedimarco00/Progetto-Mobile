package com.app.mypresence.model;

public interface LoginModelInterface {
    boolean login(String password, String username);
    void saveLogin();
}
