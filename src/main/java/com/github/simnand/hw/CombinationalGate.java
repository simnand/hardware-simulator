package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;

public abstract non-sealed class CombinationalGate extends Gate {

    protected CombinationalGate(@NotNull GateDefinition<? extends CombinationalGate> definition) {
        super(definition);
    }

    @Override
    protected final void clockUp() {
        eval();
    }

    @Override
    protected final void clockDown() {
        eval();
    }
}
