package com.github.sunian.lifecycle.extensions.persistance;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;

public class BundleFactory extends ViewModelProviders.DefaultFactory {

    private final Application application;
    private final Bundle bundle;

    /**
     * Creates a {@code DefaultFactory}
     *
     * @param application an application to pass in {@link android.arch.lifecycle.AndroidViewModel}
     */
    public BundleFactory(@NonNull Application application, @Nullable Bundle bundle) {
        super(application);
        this.application = application;
        this.bundle = bundle;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (PersistViewModel.class.isAssignableFrom(modelClass)) {
            //noinspection TryWithIdenticalCatches
            try {
                return modelClass.getConstructor(Application.class, Bundle.class)
                        .newInstance(application, bundle);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        return super.create(modelClass);
    }

}
