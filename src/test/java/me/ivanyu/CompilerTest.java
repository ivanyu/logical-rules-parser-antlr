package me.ivanyu;

import me.ivanyu.compiler.Compiler;
import me.ivanyu.pojos.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CompilerTest {
    private static Conclusion standardConclusion = new Conclusion("TheConclusion");

    private static RuleSet createRuleSet(LogicalExpression... conditions) {
        RuleSet ruleSet = new RuleSet();
        for (LogicalExpression condition : conditions) {
            Rule rule = new Rule(condition, standardConclusion);
            ruleSet.addRule(rule);
        }
        return ruleSet;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                    "if A = 1 then TheConclusion;",
                    createRuleSet(new ComparisonExpression("=",
                        new NumericVariable("A"),
                        new NumericConstant(BigDecimal.valueOf(1))))
                },

                {
                    "if (true) then TheConclusion;",
                    createRuleSet(LogicalConstant.getTrue())
                },

                {
                    "if a + 2 < 1 and c or b then TheConclusion;",
                    createRuleSet(new OrExpression(
                        new AndExpression(
                            new ComparisonExpression(
                                "<",
                                new RealArithmeticExpression(
                                    "+",
                                    new NumericVariable("a"),
                                    new NumericConstant(BigDecimal.valueOf(2))),
                                new NumericConstant(BigDecimal.valueOf(1))
                            ),
                            new LogicalVariable("c")
                        ),
                        new LogicalVariable("b")
                    ))
                },
        });
    }

    private final String stringToCompile;
    private final RuleSet targetRuleSet;

    public CompilerTest(String stringToCompile, RuleSet targetRuleSet) {
        this.stringToCompile = stringToCompile;
        this.targetRuleSet = targetRuleSet;
    }

    @Test
    public void testRule() {
        Compiler compiler = new Compiler();
        RuleSet gotRuleSet = compiler.compile(stringToCompile);
        assertEquals(gotRuleSet, targetRuleSet);
    }
}
