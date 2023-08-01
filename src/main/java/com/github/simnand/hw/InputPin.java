package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;

public class InputPin extends GateDefinition<InputPin.Pin> {

    private static final int[] EMPTY = new int[0];
    private final int[] emitterWidths;

    public InputPin(int width) {
        super("IN");
        this.emitterWidths = new int[] {width};
    }

    @Override
    public int[] receiverWidths() {
        return EMPTY;
    }

    @Override
    public int[] emitterWidths() {
        return emitterWidths;
    }

    @Override
    protected @NotNull Pin newGate() {
        return new Pin(this);
    }

    public static final class Pin extends CombinationalGate {
        private final Emitter out;

        private Pin(@NotNull GateDefinition<Pin> definition) {
            super(definition);
            this.out = emitter(0);
        }

        public void setValue(int value) {
            out.emit(value);
        }

        @Override
        protected void recompute() {
        }
    }
}
