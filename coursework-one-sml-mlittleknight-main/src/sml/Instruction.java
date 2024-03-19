package sml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * This class represents an abstract instruction for the <code>SML</code> language
 *
 * @author KLM
 */
@Data
@AllArgsConstructor
@Accessors(fluent = true)
public abstract class Instruction {
    private String label;
    private String opcode;

    public abstract void execute(Machine m);


}