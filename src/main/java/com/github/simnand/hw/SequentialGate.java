package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;

public abstract non-sealed class SequentialGate extends Gate {

    protected SequentialGate(@NotNull GateDefinition<? extends SequentialGate> definition) {
        super(definition);
    }
}
