package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.ivanyu.pojos.serializers.ConclusionSerializer;

@JsonSerialize(using= ConclusionSerializer.class)
public class Conclusion implements RuleSetPojo {
    private final String name;

    public Conclusion(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conclusion other = (Conclusion) o;

        if (name != null ? !name.equals(other.name) : other.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
