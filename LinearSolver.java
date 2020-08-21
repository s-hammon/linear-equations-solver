package solver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LinearSolver {
    private static double x;
    private static double y;
    private static double[][] a;

    public double oneVariable(double a, double b) {
        return b / a;
    }

    public double[] twoVariable(double[] eq1, double[] eq2) {
        y = (eq2[2] - eq1[2] * eq2[0] / eq1[0])
                / (eq2[1] - eq1[1] * eq2[0] / eq1[0]);

        x = oneVariable(eq1[0], (eq1[2] - eq1[1] * y));

        return new double[]{x, y};
    }

    private static double[] gaussOperations(double[][] matrix, int size) {
        int i, j, k = 0, c, flag = 0, m = 0;
        double pro = 0;
        double[] solution = new double[size];

        for (i = 0; i < size; i++) {
            if (matrix[i][i] == 0) {
                c = 1;

                while ((i + c) < size && matrix[i + c][i] == 0)
                    c++;

                if ((i + c) == size) {
                    flag = 1;
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

        for (int x = 0; x < solution.length; x++) {
            solution[x] = matrix[x][size] / matrix[x][x];
        }
        return solution;
    }
}
