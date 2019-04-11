package io.example.cloner.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.hash;

/**
 * Class to represent the vertices of the graph
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity {

    @JsonProperty("entity_id")
    private final long id;
    private final String name;
    private final String description;

    public Entity() {
        id = 0; name = null; description = null;
    }

    public Entity(long newId, Entity entity) {
        this.id = newId;
        this.name = entity.getName();
        this.description = entity.getDescription();
    }

    public Entity(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return hash(id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
