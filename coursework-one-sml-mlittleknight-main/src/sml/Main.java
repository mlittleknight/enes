package sml;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    /**
     * Initialises the system and executes the program.
     *
     * @param args name of the file containing the program text.
     */
    public void main(final String... args) {
        if (args.length != 1) {
            System.err.println("Incorrect number of arguments — Machine <file> — required");
        } else {

            Machine m = new Machine();
            Translator t = new Translator(args[0]);
            t.readAndTranslate(m.labels(), m.prog());

            System.out.format("Here is the program; it has %d instructions.%n", m.prog().size());
            System.out.println(m);

            System.out.println("Beginning program execution.");
            m.execute();
            System.out.println("Ending program execution.");

            System.out.format("Values of registers at program termination: %s.%n", m.registers());
        }
    }
}
