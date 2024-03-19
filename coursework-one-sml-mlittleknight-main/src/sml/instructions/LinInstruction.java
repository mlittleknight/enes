package sml.instructions;

import sml.Instruction;
import sml.Machine;

public final class LinInstruction extends Instruction {
    private final int register;
    private final int value;
    private String label;

    public LinInstruction(final String label, int register, int value) {
        super(label, "lin");
        this.register = register;
        this.value = value;
    }

    @Override
    public void execute(Machine m) {

        m.registers().register(register, value);
        // TODO
    }

    @Override
    public String toString() {
        return STR."\{super.toString()} lin \{register}" + " value is " + value;
    }
}
