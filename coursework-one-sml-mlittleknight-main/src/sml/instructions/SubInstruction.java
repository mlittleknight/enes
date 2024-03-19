package sml.instructions;

import sml.Instruction;
import sml.Machine;

public final class SubInstruction extends Instruction {
    private final int result;
    private final int register1;
    private final int register2;

    public SubInstruction(String label, int result, int register1, int register2) {
        super(label, "sub");
        this.result = result;
        this.register1 = register1;
        this.register2 = register2;
    }

    @Override
    public void execute(Machine m) {
        int value1 = m.registers().register(register1);
        int value2 = m.registers().register(register2);
        m.registers().register(result, value1 - value2);
    }

    @Override
    public String toString() {
        return STR."\{STR."\{STR."\{super.toString()} subtract registers " + register1} - " + register2} with the result in " + result;

    }
}
