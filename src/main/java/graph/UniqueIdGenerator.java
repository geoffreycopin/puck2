package graph;

public class UniqueIdGenerator {
    private Integer nextId = 0;

    public int generate() {
        return nextId++;
    }
}
