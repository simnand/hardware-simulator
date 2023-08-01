package com.github.simnand.hw;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Simulator {

    private final Set<Gate> gates;

    private enum State {
        LOW, HIGH
    }

    private State state;

    public Simulator() {
        this.gates = new HashSet<>();
        this.state = State.LOW;
    }

    public <T extends Gate> T addGate(T gate) {
        gates.add(gate);
        return gate;
    }

    public void tick() {
        switch (state) {
            case LOW -> {
                gates.forEach(Gate::tick);
                state = State.HIGH;
            }
            case HIGH -> throw new IllegalStateException("Trying to tick(), but the state is high");
        }
    }

    public void tock() {
        switch (state) {
            case HIGH -> {
                gates.forEach(Gate::tock);
                state = State.LOW;
            }
            case LOW -> throw new IllegalStateException("Trying to tock(), but the state is low");
        }
    }

    public void printConnections() {
        System.out.println("graph LR");
        gates.forEach(this::printGate);
    }

    private void printGate(Gate gate) {
        final String id = gate.toString();
        for (int i = 0; i < gate.emitterCount(); i++) {
            final Emitter emitter = gate.emitter(i);
            final int width = emitter.width();
            final Consumer<Receiver> print;
            if (width == 1)
                print = r -> System.out.printf("    %s --> %s;%n", id, r.gate());
            else
                print = r -> System.out.printf("    %s -->|%d| %s;%n", id, width, r.gate());
            final List<Receiver> receivers = emitter.receivers();
            receivers.forEach(print);
        }
    }
}
