package solver;

public class Matrix {
    private Row[] matrix;
    private boolean noSolutions = false;
    private boolean infiniteSolutions = false;
    private String[] solution;

    private final int countVariables;
    private final int countEquations;

    Matrix(Row... equations) {
        this.matrix = equations;
        this.countEquations = equations.length;
        this.countVariables = equations[0].size() - 1;
        printMatrix();
    }

    boolean isInfiniteSolutions() {
        return infiniteSolutions;
    }

    boolean isNoSolutions() {
        return noSolutions;
    }

    String[] getSolution() {
        if (noSolutions || infiniteSolutions) {
            System.out.println("No unique solution available.");
        }

        return solution;
    }

    private Row getRowAtIndex(int index) {
        return this.matrix[index];
    }

    private void swapRows(int rowIndex1, int rowIndex2) {
        Row temp = matrix[rowIndex1];
        matrix[rowIndex1] = matrix[rowIndex2];
        matrix[rowIndex2] = temp;
        System.out.println("R" + rowIndex1 + " <==> R" + rowIndex2);
        printMatrix();
    }

    private void swapColumns(int colIndex1, int colIndex2) {
        for (int i = 0; i < countEquations; i++) {
            Row row = matrix[i];
            Complex temp = row.getValue(colIndex1);
            row.setValue(colIndex1, row.getValue(colIndex2));
            row.setValue(colIndex2, temp);
            System.out.println("C" + colIndex1 + " <==> C" + colIndex2);
            printMatrix();
        }
    }

    private void printMatrix() {
        System.out.println();
        for (Row row : this.matrix) {
            System.out.println(row.toString());
        }

        System.out.println();
    }

    private void checkLeadingZero(int row, int col) {
        //checks conditions for non-zero coefficients

        if (!matrix[row].getValue(col).equals(Complex.ZERO))
            return;

        //non-zero in column below
        for (int i = row + 1; i < countEquations; i++) {
            if (!matrix[i].getValue(col).equals(Complex.ZERO)) {
                swapRows(row, i);
                return;
            }
        }

        //non-zero to the right
        for (int i = col + 1; i < countVariables; i++) {
            if (!matrix[row].getValue(col).equals(Complex.ZERO)) {
                swapColumns(col, i);
                return;
            }
        }

        //non-zero below and right
        for (int i = row + 1; i < countEquations; i++) {
            for (int j = col + 1; j < countVariables; j++) {
                if (!matrix[i].getValue(j).equals(Complex.ZERO)) {
                    swapRows(row, i);
                    swapColumns(col, j);
                    return;
                }
            }
        }
    }

    private boolean checkConsistency() {
        for (int i = countEquations - 1; i >= 0; i--) {
            Row equation = matrix[i];
            if (equation.isAllZero() && !equation.getValue(countVariables).equals(Complex.ZERO)) {
                return true;
            }
        }

        return false;
    }

    void checkInfiniteSolutions() {
        int significantEqs = countEquations;
        for (int i = countEquations - 1; i >= 0; i--) {
            Row equation = matrix[i];
            if (equation.isAllZero() && equation.getValue(countVariables).equals(Complex.ZERO)) {
                significantEqs--;
            }
            if (significantEqs < countVariables) {
                infiniteSolutions = true;
                return;
            }
        }
    }

    private void reduceToOne(int index) {
        //reduces diagonal coefficients to 1
        Row oldRow = this.getRowAtIndex(index);

        //does nothing if 1 (no action needed) or 0 (cannot be changed)
        if (oldRow.getValue(index).equals(Complex.ZERO) || oldRow.getValue(index).equals(Complex.ONE))
            return;

        //multiplies all elements in row by 1 / coefficient
        Complex multiple = Complex.ONE.divide(oldRow.getValue(index));
        matrix[index] = oldRow.multiply(multiple);

        System.out.println(multiple + " * R" + (index + 1) + " => R" + (index + 1));
        this.printMatrix();
    }

    private void reduceToZero(int row, int col) {
        //reduces coefficient to 0
        Row reduced = matrix[row];
        Row pivot = matrix[col];

        //does not included cases where coefficient is already 0
        if (pivot.getValue(col).equals(Complex.ZERO) || reduced.getValue(col).equals(Complex.ZERO))
            return;

        Complex pivotMultiple = pivot.getValue(col);
        Complex reducedMultiple = reduced.getValue(col);
        Row newReduced = reduced.multiply(pivotMultiple);
        Row newPivot = pivot.multiply(reducedMultiple);
        matrix[row] = newReduced.subtract(newPivot);

        System.out.println(pivotMultiple + "R" + (row + 1) + " - " + reducedMultiple + "R"
            + (col + 1) + " => R" + (row + 1));
        this.printMatrix();
    }

    void reduceToRREF() {
        //reduces matrix to its row-echelon form
        for (int i = 0; i < countEquations; i++) {
            this.checkLeadingZero(i, i);
            if (this.checkConsistency()) {
                this.noSolutions = true;
                return;
            }

            this.reduceToOne(i);
            for (int j = i + 1; j < countEquations; j++) {
                this.reduceToZero(j, i);
            }
        }

        for (int i = countEquations - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                this.reduceToZero(j, i);
            }
        }
    }

    void findUniqueSolutions() {
        solution = new String[countVariables];
        for (int i = 0; i < countVariables; i++) {
            solution[i] = this.matrix[i].getValue(countVariables).toString();
        }
    }
}
