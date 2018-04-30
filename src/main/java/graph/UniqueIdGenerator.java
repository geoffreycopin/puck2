package graph;

import java.util.HashMap;

public class UniqueIdGenerator {
    private Integer nextId = 0;
    private HashMap<String, Integer> namesCache = new HashMap<>();

    public int idFor(String nodeName) {
        if (namesCache.containsKey(nodeName)) {
            return namesCache.get(nodeName);
        }
        int id = generateId();
        namesCache.put(nodeName, id);
        return id;
    }

    private int generateId() {
        return nextId++;
    }
}
