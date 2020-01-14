package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.ArrayList;
import java.util.UUID;

public class VersionNode<T> {

    final private VersionNode<T> previousVersion;

    final private T value;

    final private UUID versionNumber;

    private ArrayList<VersionNode> descendants = new ArrayList<>();

    public VersionNode(T value, UUID versionNumber, VersionNode<T> previousVersion) {
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

    public UUID getVersionNumber() {
        return versionNumber;
    }

    public VersionNode<T> getPreviousVersion() {
        return previousVersion;
    }

}
