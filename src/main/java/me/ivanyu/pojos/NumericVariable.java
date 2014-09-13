package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NumericVariable extends NumericEntity {
    private final String variableName;

    public NumericVariable(String variableName) {
        super("var");

        this.variableName = variableName;
    }

    @JsonProperty("n")
    public String getVariableName() {
        return variableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumericVariable other = (NumericVariable) o;
        if (variableName != null ? !variableName.equals(other.variableName) : other.variableName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return variableName != null ? variableName.hashCode() : 0;
    }
}
