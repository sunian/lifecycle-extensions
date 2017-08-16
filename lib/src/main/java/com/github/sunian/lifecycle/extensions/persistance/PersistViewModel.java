package com.github.sunian.lifecycle.extensions.persistance;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

import java.io.Serializable;

public abstract class PersistViewModel<Model extends Serializable> extends AndroidViewModel {

    private Model model;

    public PersistViewModel(Application application) {
        super(application);
    }

    @SuppressWarnings("unchecked")
    public <T extends PersistViewModel> T withBundle(@Nullable Bundle bundle) {
        if (bundle != null) {
            model = (Model) bundle.getSerializable(getModelKey());
        } else {
            model = createModel();
        }
        onModelCreated();
        return (T) this;
    }

    protected abstract Model createModel();

    @CallSuper
    protected void onModelCreated() {
        // currently no-op
    }

    public void persist(Bundle bundle) {
        bundle.putSerializable(getModelKey(), model);
    }

    private String getModelKey() {
        return getClass().getCanonicalName() + " model";
    }

    public Model getModel() {
        return model;
    }

}
