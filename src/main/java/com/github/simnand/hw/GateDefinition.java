package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;

public abstract class GateDefinition<T extends Gate> {

    private final String name;

    protected GateDefinition(@NotNull String name) {
        this.name = name;
    }

    public abstract int[] receiverWidths();
    public abstract int[] emitterWidths();

    public final @NotNull T newGate(@NotNull Simulator simulator) {
        return simulator.addGate(newGate());
    }

    public String name() {
        return name;
    }

    protected abstract @NotNull T newGate();
}
