package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogicalConstant extends LogicalExpression {
    private final boolean value;

    private LogicalConstant(boolean value) {
        super("const");

        this.value = value;
    }

    @JsonProperty("v")
    public boolean getValue() {
        return value;
    }

    public static LogicalConstant getTrue(){
        return new LogicalConstant(true);
    }

    public static LogicalConstant getFalse(){
        return new LogicalConstant(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogicalConstant other = (LogicalConstant) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return (value ? 1 : 0);
    }
}
