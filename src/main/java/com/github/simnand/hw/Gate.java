package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public sealed abstract class Gate permits CombinationalGate, SequentialGate {

    private static final Logger LOGGER = LoggerFactory.getLogger(Gate.class);

    private final String type;
    private final Receiver[] receivers;
    private final Emitter[] emitters;
    private final AtomicBoolean dirty = new AtomicBoolean(false);

    protected Gate(@NotNull GateDefinition<? extends Gate> definition) {
        this.type = definition.name();
        this.receivers = new Receiver[definition.receiverWidths().length];
        for (int i = 0; i < definition.receiverWidths().length; i++)
            this.receivers[i] = new Receiver(this, i, definition.receiverWidths()[i]);

        this.emitters = new Emitter[definition.emitterWidths().length];
        for (int i = 0; i < definition.emitterWidths().length; i++)
            this.emitters[i] = new Emitter(this, i, definition.emitterWidths()[i]);
    }

    public int receiverCount() {
        return receivers.length;
    }

    public Receiver receiver(int index) {
        return receivers[index];
    }

    public int emitterCount() {
        return emitters.length;
    }

    public Emitter emitter(int index) {
        return emitters[index];
    }

    /**
     * Recomputes the values of all output pins according to the gate's functionality.
     */
    protected abstract void recompute();

    /**
     * Updates the internal state of a clocked gate according to the gate's functionality.
     * (outputs are not updated).
     */
    protected abstract void clockUp();

    /**
     * Updates the outputs of the gate according to its internal state.
     */
    protected abstract void clockDown();

    public void eval() {
        if (dirty.compareAndSet(true, false)) {
            LOGGER.atDebug().log("{}.recompute()", this);
            recompute();
        }
    }

    public void tick() {
        eval();
        clockUp();
    }

    public void tock() {
        clockDown();
        eval();
    }

    public void setDirty() {
        dirty.set(true);
        eval();
    }

    @Override
    public String toString() {
        return type + "_" + hashCode();
    }
}
