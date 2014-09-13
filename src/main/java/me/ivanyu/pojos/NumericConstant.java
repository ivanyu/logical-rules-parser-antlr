package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class NumericConstant extends NumericEntity {
    private final BigDecimal value;

    public NumericConstant(BigDecimal value) {
        super("const");
        this.value = value;
    }

    @JsonProperty("v")
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumericConstant other = (NumericConstant) o;
        if (value != null ? !value.equals(other.value) : other.value != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
