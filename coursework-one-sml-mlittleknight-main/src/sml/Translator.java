package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * This class ....
 * <p>
 * The translator of a <code>SML</code> program.
 *
 * @author ...
 */
public final class Translator {


    private static final String PATH = "";

    // word + line is the part of the current line that's not yet processed
    // word has no whitespace
    // If word and line are not empty, line begins with whitespace
    private final String fileName; // source file of SML code
    private String line = "";

    // Create a Logger instance
    private static final Logger LOGGER = Logger.getLogger(Translator.class.getName());

    public Translator(final String file) {
        fileName = PATH + file;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public boolean readAndTranslate(final Labels lab, final List<Instruction> prog) {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            // Scanner attached to the file chosen by the user
            // The labels of the program being translated
            lab.reset();
            // The program to be created
            prog.clear();

            try {
                line = sc.nextLine();
            } catch (NoSuchElementException ioE) {
                return false;
            }

            // Each iteration processes line and reads the next input
            // line into "line"
            while (line != null) {
                // Store the label in label
                var label = scan();

                if (!label.isEmpty()) {
                    var ins = getInstruction(label);
                    if (ins != null) {
                        lab.addLabel(label);
                        prog.add(ins);
                    }
                }

                try {
                    line = sc.nextLine();
                } catch (NoSuchElementException ioE) {
                    return false;
                }
            }
        } catch (IOException ioE) {
            LOGGER.severe(String.format("File: IO error %s", ioE.getMessage()));
            return false;
        }
        return true;
    }

    // The input line should consist of an SML instruction, with its label already removed.
    // Translate line into an instruction with label "label" and return the instruction.
    public Instruction getInstruction(final String label) {

        if (line.isEmpty()) {
            return null;
        }
        var opCode = scan();

        try {
            String className = "sml.instructions." + opCode.substring(0, 1).toUpperCase(Locale.ROOT) + opCode.substring(1) + "Instruction" ;
            Class<?> instructionClass = Class.forName(className);
            Constructor<?> constructor = null;
            int r, s1, s2;

            switch (opCode) {
                case "add", "sub", "mul", "div" -> {
                    r = scanInt();
                    s1 = scanInt();
                    s2 = scanInt();
                    constructor = instructionClass.getDeclaredConstructor(String.class, int.class, int.class, int.class);
                    return (Instruction) constructor.newInstance(label, r, s1, s2);
                }
                case "lin" -> {
                    r = scanInt();
                    s1 = scanInt();
                    constructor = instructionClass.getDeclaredConstructor(String.class, int.class, int.class);
                    return (Instruction) constructor.newInstance(label, r, s1);
                }

                case "out" -> {
                    r = scanInt();
                    constructor = instructionClass.getDeclaredConstructor(String.class, int.class);
                    return (Instruction) constructor.newInstance(label, r);
                }

                case "bnz" -> {
                    r = scanInt();
                    String branchLabel = scan();
                    constructor = instructionClass.getDeclaredConstructor(String.class, int.class, String.class);
                    return (Instruction) constructor.newInstance(label, r, branchLabel);
                }

                default -> System.out.printf("Unknown instruction: %s%n", opCode);
            }
            return null; // FIX THIS

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.severe(STR."Error creating instruction: \{e.getMessage()}");
            return null;
        }

    }

    /*
     * Return the first word of line and remove it from line. If there is no word,
     * return ""
     */
    private String scan() {
        line = line.trim();
        if (line.isEmpty()) {
            return "";
        }

        int i = 0;
        while (i < line.length() && line.charAt(i) != ' ' && line.charAt(i) != '\t') {
            i = i + 1;
        }
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
    }

    // Return the first word of line as an integer. If there is any error, return
    // the maximum int
    private int scanInt() {
        String word = scan();
        if (word.isEmpty()) {
            return Integer.MAX_VALUE;
        }

        try {
            return Integer.parseInt(word);
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
    }

    private Instruction returnInstruction(final String label, String opCode) {
        String pkg = "sml.instructions";
        String base = "Instruction";

        // add -> sml.instructions.AddInstruction

        String fqcn = pkg + opCode.substring(0, 1).toUpperCase(Locale.ROOT) + opCode.substring(1) + base;

        Class<?> clazz;
        try {
            clazz = Class.forName(fqcn);
        } catch (ClassNotFoundException e) {
            System.err.printf("Unknown instruction: %s%n", fqcn);
            return null;
        }

        // find the correct constructor
        var cons = findConstructor(clazz);
        var objArray = argsForConstructor(null, label);

        try {
            return (Instruction) cons.newInstance(objArray);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NullPointerException e) {
            LOGGER.severe(String.format("Error creating instance of %s", fqcn));
        }
        return null;
    }

    @SuppressWarnings("SameReturnValue")
    private Constructor<?> findConstructor(Class<?> cl) {
        Constructor<?> cons = null;
        // TODO
        return null;
    }


    @SuppressWarnings("SameReturnValue")
    private Object[] argsForConstructor(Constructor<?> cons, String label) {
        Object[] argsArray = null;
        // TODO
        return null;
    }
}
