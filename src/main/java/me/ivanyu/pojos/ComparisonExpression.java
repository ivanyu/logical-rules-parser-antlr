package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComparisonExpression extends LogicalExpression {
    private final String operator;
    private final ComparisonOperand left;
    private final ComparisonOperand right;

    public ComparisonExpression(String operator, ComparisonOperand left, ComparisonOperand right) {
        super("comp");
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @JsonProperty("op")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("left")
    public ComparisonOperand getLeft() {
        return left;
    }

    @JsonProperty("right")
    public ComparisonOperand getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparisonExpression other = (ComparisonExpression) o;

        if (left != null ? !left.equals(other.left) : other.left != null) return false;
        if (operator != null ? !operator.equals(other.operator) : other.operator != null) return false;
        if (right != null ? !right.equals(other.right) : other.right != null) return false;

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
