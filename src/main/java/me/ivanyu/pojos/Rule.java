package me.ivanyu.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule implements RuleSetPojo {
    private LogicalExpression condition;

    private Conclusion conclusion;

    public Rule() {
    }

    public Rule(LogicalExpression condition, Conclusion conclusion) {
        this.condition = condition;
        this.conclusion = conclusion;
    }

    @JsonProperty("cond")
    public LogicalExpression getCondition() {
        return condition;
    }

    public void setCondition(LogicalExpression condition) {
        this.condition = condition;
    }

    @JsonProperty("concl")
    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = new Conclusion(conclusion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        if (conclusion != null ? !conclusion.equals(rule.conclusion) : rule.conclusion != null) return false;
        if (condition != null ? !condition.equals(rule.condition) : rule.condition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = condition != null ? condition.hashCode() : 0;
        result = 31 * result + (conclusion != null ? conclusion.hashCode() : 0);
        return result;
    }
}
