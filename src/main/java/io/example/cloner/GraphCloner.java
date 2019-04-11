package io.example.cloner;

import io.example.cloner.bean.Entity;
import io.example.cloner.bean.Graph;
import io.example.cloner.bean.Link;

import java.util.*;

import static java.util.Collections.max;

/**
 * Class to clone the sub graph from the provided source
 */
public class GraphCloner {

    private final Graph graph;
    private final Map<Long, Entity> vertices = new HashMap<>();
    private final Map<Long, List<Long>> edges = new HashMap<>();
    private final Map<Long, List<Long>> reverseEdges = new HashMap<>();
    private final Map<Long, Long> visited = new HashMap<>();
    private final Map<Long, Long> parents = new HashMap<>();
    private long maxId;
    private boolean unused = true;

    public GraphCloner(Graph graph) {
        // Create copy of graph
        this.graph = new Graph(graph);

        // Map ids to vertices for quick access
        graph.getEntities().forEach(entity -> {
            vertices.put(entity.getId(), entity);

            edges.put(entity.getId(), new ArrayList<>());
            reverseEdges.putIfAbsent(entity.getId(), new ArrayList<>());
        });

        // Create adjacency arrays of edges and reverse edges
        graph.getLinks().forEach(link -> {
            edges.get(link.getFrom()).add(link.getTo());
            reverseEdges.get(link.getTo()).add(link.getFrom());
        });

        // Store maximum id, to generate new ids
        maxId = max(vertices.keySet());
    }

    public Graph cloneSource(long sourceId) {
        // GraphCloner class instance cannot be reused due to the internal state
        if (!unused) {
            throw new IllegalStateException("GraphCloner instance cannot be reused!");
        }
        unused = false;

        // If source doesn't exists return graph unmodified
        if (!vertices.containsKey(sourceId)) {
            return graph;
        }

        // Traverse sub graph using the BFS algorithm from the provided source and clone it
        Queue<Long> queue = new LinkedList<>();
        queue.add(sourceId);
        cloneEntity(sourceId, true);

        while (!queue.isEmpty()) {
            long currentId = queue.remove();

            // If we have an edge to the vertex which is already visited,
            // and it doesn't share with current vertex the same parent,
            // then create corresponding edge in the clone
            edges.get(currentId)
                    .stream()
                    .filter(neighbourId -> visited.containsKey(neighbourId))
                    .filter(neighbourId -> parents.get(currentId) != parents.get(neighbourId))
                    .forEach(neighbourId -> {
                        graph.getLinks().add(new Link(visited.get(currentId), visited.get(neighbourId)));
                    });

            // Clone non visited vertices and corresponding edges
            edges.get(currentId)
                    .stream()
                    .filter(neighbourId -> !visited.containsKey(neighbourId))
                    .forEach(neighbourId -> {
                        cloneEntity(neighbourId);

                        parents.putIfAbsent(neighbourId, currentId);

                        queue.add(neighbourId);
                    });
        }

        return graph;
    }

    private void cloneEntity(long id) {
        this.cloneEntity(id, false);
    }

    private void cloneEntity(long id, boolean isSource) {
        // Generate new id for the entity clone
        long cloneId = ++maxId;

        // Clone entity, add it to the graph, and map entity to its' clone
        Entity entity = vertices.get(id), entityClone = new Entity(cloneId, entity);
        graph.getEntities().add(entityClone);
        visited.put(id, entityClone.getId());

        // When source vertex is visited, its' clone must have edges with actual parents of the source vertex.
        // Other visited vertices must have edges only with clones of parents.
        if (isSource) {
            reverseEdges.get(id).forEach(parentId -> {
                graph.getLinks().add(new Link(parentId, cloneId));
            });
        } else {
            reverseEdges.get(id).forEach(parentId -> {
                long parentCloneId = visited.getOrDefault(parentId, 0L);

                if (parentCloneId > 0L) {
                    graph.getLinks().add(new Link(parentCloneId, cloneId));
                }
            });
        }
    }
}
