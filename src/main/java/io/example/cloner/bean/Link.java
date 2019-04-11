package io.example.cloner.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 * Class to represent the edges of the graph
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    private final long from;
    private final long to;

    public Link() {
        from = 0L;
        to = 0L;
    }

    public Link(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Link link = (Link) o;

        return from == link.from && to == link.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }
}
