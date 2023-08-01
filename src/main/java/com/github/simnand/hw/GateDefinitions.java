package com.github.simnand.hw;

public class GateDefinitions {
    public static final GateDefinition<CombinationalGate> NOT =
            new CombSingle1binInSingle1bitOut("NOT", a -> ~a);
    public static final GateDefinition<CombinationalGate> AND =
            new CombBi1binInSingle1bitOut("AND", (a, b) -> a & b);
    public static final GateDefinition<CombinationalGate> OR =
            new CombBi1binInSingle1bitOut("OR", (a, b) -> a | b);
    public static final GateDefinition<CombinationalGate> NAND =
            new CombBi1binInSingle1bitOut("NAND", (a, b) -> ~(a & b));
    public static final GateDefinition<InputPin.Pin> IN_1 = new InputPin(1);
    public static final GateDefinition<OutputPin.Pin> OUT_1 = new OutputPin(1);
}
