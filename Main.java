package solver;

public class Main {
    public static void main(String[] args) throws Exception {
        LinearSolver solver = new LinearSolver();
        solver.solve(args[1], args[3]);
    }
}
