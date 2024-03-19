package sml.instructions;

import sml.Instruction;
import sml.Machine;
public final class BnzInstruction extends Instruction {
    private final int registerIndex;
    private final String branchLabel;

    public BnzInstruction(String label, int registerIndex, String branchLabel) {
        super(label, "bnz");
        this.registerIndex = registerIndex;
        this.branchLabel = branchLabel;
    }

    @Override
    public void execute(Machine m) {
        int value = m.registers().register(registerIndex);
        if (value != 0){
            // get the label index from the label list
            int newIndex = m.labels().indexOf(branchLabel);
            System.out.println(newIndex);
            // set the program counter to the index of the label
            if (newIndex != -1) {
                m.pc(newIndex);
            } else{
                System.out.println("Label not found");
            }
        }
    }


    @Override
    public String toString() {
        return STR."\{super.toString()} \{registerIndex} \{branchLabel}";
    }
}
