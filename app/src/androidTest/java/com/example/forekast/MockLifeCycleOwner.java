package com.example.forekast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

public class MockLifeCycleOwner implements LifecycleOwner {
    private LifecycleRegistry lifecycle = new LifecycleRegistry(this);

    protected void onCreate() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
    }

    protected void onStart() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }
}
