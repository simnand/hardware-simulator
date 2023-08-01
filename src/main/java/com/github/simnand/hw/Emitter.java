package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Emitter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Emitter.class);

    private final Gate gate;
    private final int index;
    private final List<Receiver> receivers = new ArrayList<>();
    private final int width;
    private final int mask;
    private int value;

    public Emitter(Gate gate, int index, int width) {
        this.gate = gate;
        this.index = index;
        this.width = width;
        this.mask = (1 << width) - 1;
        this.value = 0;
    }

    public void connect(@NotNull Receiver receiver) {
        if (width != receiver.width())
            throw new IllegalArgumentException("Cannot connect emitter/%d to receiver/%d"
                                                       .formatted(width, receiver.width()));

        receivers.add(receiver);
    }

    public void disconnect(@NotNull Receiver receiver) {
        receivers.remove(receiver);
    }

    public @UnmodifiableView List<Receiver> receivers() {
        return Collections.unmodifiableList(receivers);
    }

    public int width() {
        return width;
    }

    public void emit(int value) {
        final int masked = value & mask;
        final boolean changed = masked != this.value;
        LOGGER.atDebug().log("{}.emit(value={}, changed={})", this, masked, changed);
        if (changed) {
            this.value = masked;
            receivers.forEach(receiver -> receiver.receive(masked));
        }
    }

    @Override
    public String toString() {
        return gate + ":OUT_" + index;
    }
}
