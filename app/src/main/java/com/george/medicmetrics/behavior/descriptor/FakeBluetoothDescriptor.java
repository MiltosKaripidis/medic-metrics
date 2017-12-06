package com.george.medicmetrics.behavior.descriptor;

import android.support.annotation.NonNull;

import java.util.UUID;

public class FakeBluetoothDescriptor implements Descriptor {

    private UUID mUUID;

    public FakeBluetoothDescriptor(UUID uuid) {
        mUUID = uuid;
    }

    @NonNull
    @Override
    public UUID getUuid() {
        return mUUID;
    }

    @Override
    public void setValue(@NonNull byte[] value) {
        // TODO: Implement
    }
}
