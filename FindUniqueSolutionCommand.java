package solver;

public class FindUniqueSolutionCommand implements Command {
    private final Matrix matrix;

    FindUniqueSolutionCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.findUniqueSolutions();
    }
}
