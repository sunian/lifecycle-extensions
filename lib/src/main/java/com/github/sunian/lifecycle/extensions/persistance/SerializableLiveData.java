package com.github.sunian.lifecycle.extensions.persistance;

import android.arch.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class SerializableLiveData<T extends Serializable> extends MutableLiveData<T>
        implements Serializable {

    private static final long serialVersionUID = 1;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(getValue());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        //noinspection unchecked
        setValue((T) in.readObject());
    }

    private void readObjectNoData() throws ObjectStreamException {
    }

}
