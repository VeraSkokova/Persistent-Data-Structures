package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.ArrayList;

public class VersionNode<T> {

    final private VersionNode<T> previousVersion;

    private T value;

    final private int versionNumber;

    private ArrayList<VersionNode> descendants = new ArrayList<>();

    public VersionNode(T value, int versionNumber, VersionNode<T> previousVersion) {
        this.value = value;
        this.versionNumber = versionNumber;
        this.previousVersion = previousVersion;
    }

    public void add(VersionNode version) {
        this.descendants.add(version);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public VersionNode<T> getPreviousVersion() {
        return previousVersion;
    }

}
