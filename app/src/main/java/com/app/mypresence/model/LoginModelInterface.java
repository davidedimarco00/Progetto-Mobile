package com.app.mypresence.model;

import android.os.Bundle;

public interface LoginModelInterface {
    boolean login(String password, String username);
    void saveLogin();

    Bundle getUserInfo();
}
