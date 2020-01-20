package ru.nsu.fit.modern_programming.persistent_data_structures;

public interface UndoRedo {
    UndoRedo undo();

    UndoRedo redo();

    void registerUpdateListener(UpdateListener updateListener);

    public interface UpdateListener {
        void onUpdate(UndoRedo persistentDataStructure);
    }
}
