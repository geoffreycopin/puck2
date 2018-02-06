package graph.readers;

import graph.Edge;
import graph.Node;
import graph.UniqueIdGenerator;

import java.util.List;
import java.util.Map;

public abstract class AbstractReader {
    protected UniqueIdGenerator idGenerator;

    public AbstractReader(UniqueIdGenerator generator) {
        this.idGenerator = generator;
    }

    public abstract void readInto(Map<String, Node> nodes, List<Edge> edges);
}
