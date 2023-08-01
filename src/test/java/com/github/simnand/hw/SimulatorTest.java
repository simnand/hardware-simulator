package com.github.simnand.hw;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    @Test
    public void simpleAndTest() {
        final Simulator simulator = new Simulator();

        final InputPin.Pin a = GateDefinitions.IN_1.newGate(simulator);
        final InputPin.Pin b = GateDefinitions.IN_1.newGate(simulator);
        final OutputPin.Pin out = GateDefinitions.OUT_1.newGate(simulator);

        final AtomicInteger value = new AtomicInteger();
        out.setConsumer(value::set);

        final CombinationalGate and = GateDefinitions.AND.newGate(simulator);
        a.emitter(0).connect(and.receiver(0));
        b.emitter(0).connect(and.receiver(1));
        and.emitter(0).connect(out.receiver(0));

        a.setValue(1);
        b.setValue(1);

        simulator.tick();
        simulator.tock();

        assertEquals(1, value.get());
    }

    @Test
    public void xorTest() {
        // XOR = (a OR b) AND (a NAND b)

        final Simulator simulator = new Simulator();

        final InputPin.Pin a = GateDefinitions.IN_1.newGate(simulator);
        final InputPin.Pin b = GateDefinitions.IN_1.newGate(simulator);
        final OutputPin.Pin out = GateDefinitions.OUT_1.newGate(simulator);

        final AtomicInteger value = new AtomicInteger();
        out.setConsumer(value::set);

        final CombinationalGate and = GateDefinitions.AND.newGate(simulator);
        and.emitter(0).connect(out.receiver(0));

        final CombinationalGate or = GateDefinitions.OR.newGate(simulator);
        a.emitter(0).connect(or.receiver(0));
        b.emitter(0).connect(or.receiver(1));
        or.emitter(0).connect(and.receiver(0));

        final CombinationalGate nand = GateDefinitions.NAND.newGate(simulator);
        a.emitter(0).connect(nand.receiver(0));
        b.emitter(0).connect(nand.receiver(1));
        nand.emitter(0).connect(and.receiver(1));

        a.setValue(0);
        b.setValue(0);
        simulator.tick();
        simulator.tock();
        assertEquals(0, value.get());

        a.setValue(0);
        b.setValue(1);
        simulator.tick();
        simulator.tock();
        assertEquals(1, value.get());

        a.setValue(1);
        b.setValue(0);
        simulator.tick();
        simulator.tock();
        assertEquals(1, value.get());

        a.setValue(1);
        b.setValue(1);
        simulator.tick();
        simulator.tock();
        assertEquals(0, value.get());
    }
}
