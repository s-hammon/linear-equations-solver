package solver;

import java.util.Arrays;

public class Row {
    private Complex[] row;
    private int countVariables;

    Row(Complex... coefficients) {
        this.row = coefficients;
        this.countVariables = row.length - 1;
    }

    int size() {
        return row.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(row);
    }

    Complex getValue(int index) {
        return this.row[index];
    }

    void setValue(int index, Complex value) {
        this.row[index] = value;
    }

    Row multiply(Complex multiple) {
        Complex[] newRow = row.clone();
        for (int i = 0; i < newRow.length; i++) {
            newRow[i] = newRow[i].multiply(multiple);
        }

        return new Row(newRow);
    }

    Row subtract (Row subtrahend) {
        for (int i = 0; i < row.length; i++) {
            row[i] = row[i].subtract(subtrahend.getValue(i));
        }

        return this;
    }

    boolean isAllZero() {
        for (int i = 0; i < countVariables; i++) {
            if (!row[i].equals(Complex.ZERO))
                return false;
        }
        return true;
    }
}
