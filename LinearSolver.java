package solver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class LinearSolver {

    public void solve(String input, String output) throws Exception {
        //reads matrix from file
        Scanner sc = new Scanner(new BufferedReader(new FileReader(input)));
        int size = Integer.parseInt(sc.nextLine());

        double[][] a = new double[size][size + 1];
        while (sc.hasNextLine()) {
            for (int i = 0; i < a.length; i++) {
                String[] row = sc.nextLine().trim().split(" ");
                for (int j = 0; j < row.length; j++) {
                    a[i][j] = Double.parseDouble(row[j]);
                }
            }
        }

        sc.close();

        //get solution to system of linear equations
        double[] solution = gaussOperations(a, size);

        //writes solution to file
        FileWriter fileWriter = new FileWriter(output);
        for (double s : solution) {
            fileWriter.write(s + "\n");
        }

        fileWriter.close();
    }

    private static double[] gaussOperations(double[][] matrix, int size) {
        int i, j, k, c;
        double[] solution = new double[size];

        //convert matrix to echelon form
        for (i = 0; i < size; i++) {
            if (matrix[i][i] == 0) {
                c = 1;

                while ((i + c) < size && matrix[i + c][i] == 0)
                    c++;

                if ((i + c) == size) {
                    break;
                }

                for (j = i, k = 0; k <= size; k++) {
                    double temp = matrix[j][k];
                    matrix[j][k] = matrix[j + c][k];
                    matrix[j + c][k] = temp;
                }
            }

            for (j = 0; j < size; j++) {
                if (i != j) {

                    double p = matrix[j][i] / matrix[i][i];

                    for (k = 0; k <= size; k++) {
                        matrix[j][k] = matrix[j][k] - matrix[i][k] * p;
                    }
                }
            }
        }

        //put solutions into array of size N to print to file
        for (int x = 0; x < solution.length; x++) {
            solution[x] = matrix[x][size] / matrix[x][x];
        }

        return solution;
    }
}
