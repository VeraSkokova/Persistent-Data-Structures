package ru.nsu.fit.modern_programming.persistent_data_structures;

import java.util.Optional;

public class FatNode<T> {
    private int maxVersion;
    private Tree<VersionData<T>> versionsData;

    public FatNode(T value) {
        VersionData<T> versionData = new VersionData<>(0, value);
        versionsData = new Tree<>(versionData);
        maxVersion = 0;
    }

    public boolean insertData(T data) {
        maxVersion++;
        VersionData<T> versionData = new VersionData<>(maxVersion, data);
        versionsData.addChild(versionData);
        return true;
    }

    public T getData(int version) {
        VersionData<T> versionData = new VersionData<>(version, null);
        Optional<VersionData<T>> search = versionsData.search(versionData);
        return search.map(VersionData::getData)
                .orElse(null);
    }
}
