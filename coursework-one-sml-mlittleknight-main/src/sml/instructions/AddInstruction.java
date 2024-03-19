package sml.instructions;

import sml.Instruction;
import sml.Machine;

public final class AddInstruction extends Instruction {
    private final int result;
    private final int register1;
    private final int register2;
    private String label;

    public AddInstruction(String label, int i1, int i2, int i3) {
        super(label, "add");
        result = i1;
        register1 = i2;
        register2 = i3;
    }

    @Override
    public void execute(Machine m) {
        var value1 = m.registers().register(register1);
        var value2 = m.registers().register(register2);
        m.registers().register(result, value1 + value2);
    }

    @Override
    public String toString() {
        return STR."\{STR."\{STR."\{super.toString()} add registers " + register1} + " + register2} with the result in " + result;
    }
}
