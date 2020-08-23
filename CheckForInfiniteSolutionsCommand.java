package solver;

public class CheckForInfiniteSolutionsCommand implements Command {
    private final Matrix matrix;

    CheckForInfiniteSolutionsCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.checkInfiniteSolutions();
    }
}
