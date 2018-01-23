package com.example.lequan.lichvannien.network;

public interface StringRequestCallback {
    void onError(int i, String str);

    void onSuccess(String str);
}
