package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RealArithmeticExpression implements ArithmeticExpression {
    private final String operator;
    private final ArithmeticExpression left;
    private final ArithmeticExpression right;

    public RealArithmeticExpression(String operator, ArithmeticExpression left, ArithmeticExpression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @JsonProperty("t")
    public String getType() { return "arith"; }

    @JsonProperty("op")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("left")
    public ArithmeticExpression getLeft() {
        return left;
    }

    @JsonProperty("right")
    public ArithmeticExpression getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealArithmeticExpression that = (RealArithmeticExpression) o;

        if (left != null ? !left.equals(that.left) : that.left != null) return false;
        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        if (right != null ? !right.equals(that.right) : that.right != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = operator != null ? operator.hashCode() : 0;
        result = 31 * result + (left != null ? left.hashCode() : 0);
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}
