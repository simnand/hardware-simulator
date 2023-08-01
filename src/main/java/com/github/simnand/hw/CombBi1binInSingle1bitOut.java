package com.github.simnand.hw;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombBi1binInSingle1bitOut extends GateDefinition<CombinationalGate> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CombBi1binInSingle1bitOut.class);
    private static final int[] RECEIVER_WIDTHS = new int[] {1, 1};
    private static final int[] EMITTER_WIDTHS = new int[] {1};

    private final Operator operator;

    public CombBi1binInSingle1bitOut(@NotNull String name, @NotNull Operator operator) {
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
            private final Receiver a = receiver(0);
            private final Receiver b = receiver(1);
            private final Emitter out = emitter(0);

            @Override
            protected void recompute() {
                final int val0 = a.getAndMarkClean();
                final int val1 = b.getAndMarkClean();

                final int value = operator.compute(val0, val1) & 1;
                LOGGER.atDebug().log("{}: {} ∘ {} -> {}", this, val0, val1, value);

                out.emit(value);
            }
        };
    }

    public interface Operator {
        int compute(int a, int b);
    }
}
