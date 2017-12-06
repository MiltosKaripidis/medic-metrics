package com.george.medicmetrics.behavior.descriptor;

import android.support.annotation.NonNull;

import java.util.UUID;

public interface Descriptor {

    @NonNull
    UUID getUuid();

    void setValue(@NonNull byte[] value);
}
