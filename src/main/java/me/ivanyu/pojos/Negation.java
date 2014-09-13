package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Negation implements ArithmeticExpression {
    private final ArithmeticExpression expression;

    public Negation(ArithmeticExpression expression) {
        this.expression = expression;
    }

    @JsonProperty("t")
    public String getType() { return "neg"; }

    @JsonProperty("expr")
    public ArithmeticExpression getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Negation other = (Negation) o;
        if (expression != null
                ? !expression.equals(other.expression)
                : other.expression != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return expression != null ? expression.hashCode() : 0;
    }
}
