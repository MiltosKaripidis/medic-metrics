package com.george.medicmetrics.behavior.gatt.descriptor;

import android.support.annotation.NonNull;

public interface Descriptor {

    void setValue(@NonNull byte[] value);
}
