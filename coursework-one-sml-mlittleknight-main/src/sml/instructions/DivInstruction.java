package sml.instructions;

import sml.Instruction;
import sml.Machine;

public final class DivInstruction extends Instruction {
    private final int result;
    private final int register1;
    private final int register2;

    public DivInstruction(String label, int result, int register1, int register2) {
        super(label, "div");
        this.result = result;
        this.register1 = register1;
        this.register2 = register2;
    }

    @Override
    public void execute(Machine m) {
        int dividendValue = m.registers().register(register1);
        int divisorValue = m.registers().register(register2);

        if (divisorValue == 0) {
            throw new ArithmeticException("Division by zero");
        }

        m.registers().register(result, dividendValue / divisorValue);
    }

    @Override
    public String toString() {
        return STR."\{STR."\{STR."\{super.toString()} divide registers " + register1} / " + register2} with the result in " + result;

    }
}
