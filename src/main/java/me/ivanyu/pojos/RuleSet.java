package me.ivanyu.pojos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RuleSet implements RuleSetPojo {
    public final List<Rule> rules;

    public RuleSet() {
        this.rules = new ArrayList<>();
    }

    public RuleSet(Collection<Rule> rules) {
        this.rules = new ArrayList<>(rules);
    }

    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RuleSet ruleSet = (RuleSet) o;

        return this.rules.equals(ruleSet.rules);
    }

    @Override
    public int hashCode() {
        return rules != null ? rules.hashCode() : 0;
    }
}
