package me.ivanyu;

import me.ivanyu.compiler.ExceptionThrowingErrorHandler;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class GrammarTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                /* Valid rules. */
                { true, "if true then conclusion;" },
                { true, "if false then conclusion;" },
                { true, "if (true) then conclusion;" },
                { true, "if (false) then conclusion;" },

                { true, "if a then conclusion;" },
                { true, "if (a) then conclusion;" },
                { true, "if a or b then conclusion;" },
                { true, "if a and b then conclusion;" },
                { true, "if (a or b) and c then conclusion;" },
                { true, "if a or b and c then conclusion;" },

                { true, "if a_1 or b_2 and c_3_aaa then conclusion;" },

                { true, "if a >= b then conclusion;" },
                { true, "if a > b then conclusion;" },
                { true, "if a = b then conclusion;" },
                { true, "if a < b then conclusion;" },
                { true, "if a <= b then conclusion;" },
                { true, "if a > 0.1 then conclusion;" },
                { true, "if 1.12 <= b then conclusion;" },
                { true, "if 0.1 = 4 then conclusion;" },
                { true, "if 5 = 5.0 then conclusion;" },
                { true, "if a + 5 * b <= c / 12.0 - 1 then conclusion;" },
                { true, "if (a) > (1.23) * (1 + 4) then conclusion;" },
                { true, "if 1 < 4 and true then conclusion;" },
                { true, "if -(a * b) < 12 and true then conclusion;" },

                { true, "" }, // empty rule file
                { true, "//  comment in a rule file" },
                { true, "//  comment with new line\n" },


                /* Invalid rules. */
                { false, "true" },
                { false, "false" },

                { false, "if true then conclusion" }, // No semicolon

                { false, "if then conclusion;" },
                { false, "if true conclusion;" },
                { false, "if then;" },
                { false, "if;" },
                { false, "if" },
                { false, "then;" },
                { false, "then" },

                { false, "a + b" },

                { false, "if a ++ b then conclusion;" },
                { false, "if a + (+b) then conclusion;" },

                { false, "true and false;" },

                { false, "if abc $ 123 then conclusion;" },
                { false, "if abc @ 123 then conclusion;" },

                { false, "if aa( == 123 then conclusion;" },
                { false, "if bb) == 123 then conclusion;" },

                { false, "if true then conclusion1 conclusion2;" },

                // Considered invalid for now
                { false, "if function(a, b, c) then conclusion;" },

                { false, "if logical_function() then conclusion;" },
                { false, "if logical_function() and (true) then conclusion;" },

                { false, "if numeric_function() == 123 then conclusion;" },
                { false, "if numeric_function() >= 0.12 then conclusion;" },
        });
    }

    private final boolean testValid;
    private final String testString;

    public GrammarTest(boolean testValid, String testString) {
        this.testValid = testValid;
        this.testString = testString;
    }

    @Test
    public void testRule() {
        ANTLRInputStream input = new ANTLRInputStream(this.testString);
        RuleSetGrammarLexer lexer = new RuleSetGrammarLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);

        RuleSetGrammarParser parser = new RuleSetGrammarParser(tokens);

        parser.removeErrorListeners();
        parser.setErrorHandler(new ExceptionThrowingErrorHandler());

        if (this.testValid) {
            ParserRuleContext ruleContext = parser.rule_set();
            assertNull(ruleContext.exception);
        } else {
            try {
                ParserRuleContext ruleContext = parser.rule_set();
                fail("Failed on \"" + this.testString + "\"");
            } catch (RuntimeException e) {
                // deliberately do nothing
            }
        }
    }
}
