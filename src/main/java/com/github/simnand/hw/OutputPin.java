package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;

import java.util.function.IntConsumer;

public class OutputPin extends GateDefinition<OutputPin.Pin> {

    private static final int[] EMPTY = new int[0];
    private final int[] receiverWidths;

    public OutputPin(int width) {
        super("OUT");
        this.receiverWidths = new int[]{width};
    }


    @Override
    public int[] receiverWidths() {
        return receiverWidths;
    }

    @Override
    public int[] emitterWidths() {
        return EMPTY;
    }

    @Override
    protected @NotNull OutputPin.Pin newGate() {
        return new Pin(this);
    }

    public static final class Pin extends CombinationalGate {
        private final Receiver in;
        private @NotNull IntConsumer consumer = System.out::println;

        private Pin(@NotNull GateDefinition<? extends CombinationalGate> definition) {
            super(definition);
            this.in = receiver(0);
        }

        public void setConsumer(@NotNull IntConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        protected void recompute() {
            consumer.accept(in.getAndMarkClean());
        }
    }
}
