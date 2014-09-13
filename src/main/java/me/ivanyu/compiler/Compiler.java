package me.ivanyu.compiler;

import me.ivanyu.RuleSetGrammarLexer;
import me.ivanyu.RuleSetGrammarParser;
import me.ivanyu.pojos.RuleSet;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

public class Compiler {
    public RuleSet compile(String inputString) {
        ANTLRInputStream input = new ANTLRInputStream(inputString);
        RuleSetGrammarLexer lexer = new RuleSetGrammarLexer(input);
        TokenStream tokens = new CommonTokenStream(lexer);
        RuleSetGrammarParser parser = new RuleSetGrammarParser(tokens);

        TreeBuilder treeBuilder = new TreeBuilder();
        parser.addParseListener(treeBuilder);
        parser.setErrorHandler(new ExceptionThrowingErrorHandler());

        parser.rule_set();

        return treeBuilder.getRuleSet();
    }
}
