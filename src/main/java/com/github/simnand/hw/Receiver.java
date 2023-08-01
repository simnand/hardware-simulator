package com.github.simnand.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private final Gate gate;
    private final int index;
    private final int width;
    private final int mask;
    private int value;
    private boolean dirty;

    public Receiver(Gate gate, int index, int width) {
        this.gate = gate;
        this.index = index;
        this.width = width;
        this.mask = (1 << width) - 1;
        this.value = 0;
        this.dirty = true;
    }

    public int width() {
        return width;
    }

    public void receive(int value) {
        final int masked = value & mask;
        final boolean changed = this.value != masked;
        LOGGER.atDebug().log("{}.receive(value={}, changed={})", this, masked, changed);
        if (changed) {
            this.value = masked;
            dirty = true;
            gate.setDirty();
        }
    }

    public boolean isDirty() {
        return dirty;
    }

    public int getAndMarkClean() {
        dirty = false;
        return value;
    }

    public Gate gate() {
        return gate;
    }

    @Override
    public String toString() {
        return gate + ":IN_" + index;
    }
}
