package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.Objects;

public class VersionData<T> {
    private int version;
    private T data;

    public VersionData(int version, T data) {
        this.version = version;
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VersionData<?> that = (VersionData<?>) o;
        return version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }
}
