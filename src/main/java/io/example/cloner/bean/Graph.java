package io.example.cloner.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the graph
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Graph {

    private final List<Entity> entities;
    private final List<Link> links;

    public Graph() {
        this.entities = new ArrayList<>();
        this.links = new ArrayList<>();
    }

    public Graph(Graph graph) {
        this.entities = new ArrayList<>(graph.getEntities());
        this.links = new ArrayList<>(graph.getLinks());
    }

    public Graph(List<Entity> entities, List<Link> links) {
        this.entities = new ArrayList<>(entities);
        this.links = new ArrayList<>(links);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Link> getLinks() {
        return links;
    }
}
