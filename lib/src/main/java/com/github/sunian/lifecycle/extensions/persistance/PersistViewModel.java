package com.github.sunian.lifecycle.extensions.persistance;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.io.Serializable;

/**
 * An {@link AndroidViewModel} with a {@link Serializable} Model
 */
public abstract class PersistViewModel<Model extends Serializable> extends AndroidViewModel {

    private final Model model;
    private final boolean fromBundle;

    public PersistViewModel(Application application, @Nullable Bundle bundle) {
        super(application);
        fromBundle = bundle != null && bundle.containsKey(getModelKey());
        if (fromBundle) {
            //noinspection unchecked
            model = (Model) bundle.getSerializable(getModelKey());
        } else {
            model = createModel();
        }
    }

    public boolean hasFreshState() {
        return !fromBundle;
    }

    /**
     * Create a new model. A serialized model was not available.
     */
    protected abstract Model createModel();

    public void persist(Bundle bundle) {
        bundle.putSerializable(getModelKey(), model);
    }

    private String getModelKey() {
        return getClass().getCanonicalName() + " model";
    }

    protected Model getModel() {
        return model;
    }

    private static ViewModelProvider.Factory NULL_BUNDLE_FACTORY;

    private static void initializeFactoryIfNeeded(Application application) {
        if (NULL_BUNDLE_FACTORY == null) {
            NULL_BUNDLE_FACTORY = new BundleFactory(application, null);
        }
    }

    public static ViewModelProvider provider(FragmentActivity activity) {
        initializeFactoryIfNeeded(activity.getApplication());
        return ViewModelProviders.of(activity, NULL_BUNDLE_FACTORY);
    }

    public static ViewModelProvider provider(Fragment fragment) {
        initializeFactoryIfNeeded(fragment.getActivity().getApplication());
        return ViewModelProviders.of(fragment, NULL_BUNDLE_FACTORY);
    }

    public static ViewModelProvider provider(FragmentActivity activity, @Nullable Bundle bundle) {
        return ViewModelProviders.of(activity,
                new BundleFactory(activity.getApplication(), bundle));
    }

    public static ViewModelProvider provider(Fragment fragment, @Nullable Bundle bundle) {
        return ViewModelProviders.of(fragment,
                new BundleFactory(fragment.getActivity().getApplication(), bundle));
    }

}
