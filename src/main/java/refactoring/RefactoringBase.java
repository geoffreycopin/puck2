package refactoring;

import graph.Graph;

public abstract class RefactoringBase {
    private Graph graph;

    public RefactoringBase(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public abstract void refactor();
}
