package solver;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input = args[1];
        String output = args[3];

        //reads matrix from input file
        File readFrom = Paths.get(input).toFile();
        Row[] equations;
        try (Scanner sc = new Scanner(readFrom)) {
            equations = getEquations(sc);
        } catch (Exception e) {
            System.out.println("Error: file not found.");
            return;
        }

        Matrix matrix = new Matrix(equations);
        Controller controller = new Controller();

        controller.setCommand(new ReduceToRREFCommand(matrix));
        controller.executeCommand();
        boolean hasNoSolutions = matrix.isNoSolutions();

        controller.setCommand(new CheckForInfiniteSolutionsCommand(matrix));
        controller.executeCommand();
        boolean hasInfiniteSolutions = matrix.isInfiniteSolutions();

        String solutionType = "Unique";
        if (hasNoSolutions) {
            solutionType = "No solutions";
        } else if (hasInfiniteSolutions) {
            solutionType = "Infinitely many solutions";
        }

        controller.setCommand(new FindUniqueSolutionCommand(matrix));
        File writeTo = Paths.get(output).toFile();

        try (PrintWriter printWriter = new PrintWriter(writeTo)) {
            if (!"Unique".equals(solutionType)) {
                System.out.println(solutionType);
                printWriter.println(solutionType);
            } else {
                controller.executeCommand();
                String[] solution = matrix.getSolution();
                System.out.println("Solution: " + Arrays.toString(solution));
                for (String var : solution) {
                    printWriter.println(var);
                }
            }
        } catch (Exception e) {
            System.out.println ("Error: could not write to file.");
        }
    }

    private static Row[] getEquations(Scanner sc) {
        int countVariables = sc.nextInt();
        int countEquations = sc.nextInt();
        Row[] equations = new Row[countEquations];

        int equationNo = 0;
        while (sc.hasNext()) {
            Complex[] equation = new Complex[countVariables + 1];
            for (int i = 0; i < countVariables + 1; i++) {
                equation[i] = new Complex(sc.next());
            }

            equations[equationNo] = new Row(equation);
            equationNo++;
        }

        return equations;
    }
}
