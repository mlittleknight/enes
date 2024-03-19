package sml.instructions;

import sml.Instruction;
import sml.Machine;

public final class OutInstruction extends Instruction {
    private final int registerIndex;

    public OutInstruction(String label, int registerIndex) {
        super(label, "out");
        this.registerIndex = registerIndex;
    }

    @Override
    public void execute(Machine m) {
        int value = m.registers().register(registerIndex);
        System.out.println(STR."Output: \{value}");
    }

    @Override
    public String toString() {
        return STR."\{super.toString()} \{registerIndex}";
    }
}
