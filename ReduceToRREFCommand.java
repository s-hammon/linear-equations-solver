package solver;

public class ReduceToRREFCommand implements Command {
    private final Matrix matrix;

    ReduceToRREFCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.reduceToRREF();
    }
}
