package com.george.medicmetrics.data;

import android.support.annotation.NonNull;

public interface Callback<T> {

    void onSuccess(@NonNull T t);
}
