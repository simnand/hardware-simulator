package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombSingle1binInSingle1bitOut extends GateDefinition<CombinationalGate> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CombBi1binInSingle1bitOut.class);
    private static final int[] RECEIVER_WIDTHS = new int[] {1, 1};
    private static final int[] EMITTER_WIDTHS = new int[] {1};

    private final Operator operator;

    public CombSingle1binInSingle1bitOut(@NotNull String name, @NotNull Operator operator) {
        super(name);
        this.operator = operator;
    }

    @Override
    public int[] receiverWidths() {
        return RECEIVER_WIDTHS;
    }

    @Override
    public int[] emitterWidths() {
        return EMITTER_WIDTHS;
    }

    @Override
    protected @NotNull CombinationalGate newGate() {
        return new CombinationalGate(this) {
            private final Receiver in = receiver(0);
            private final Emitter out = emitter(0);

            @Override
            protected void recompute() {
                final int val0 = in.getAndMarkClean();

                final int value = operator.compute(val0) & 1;
                LOGGER.atDebug().log("{}: ∘ {} -> {}", this, val0, value);

                out.emit(value);
            }
        };
    }

    public interface Operator {
        int compute(int in);
    }
}
