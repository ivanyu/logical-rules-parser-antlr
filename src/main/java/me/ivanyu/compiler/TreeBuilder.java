package me.ivanyu.compiler;

import me.ivanyu.RuleSetGrammarBaseListener;
import me.ivanyu.RuleSetGrammarParser;
import me.ivanyu.pojos.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Stack;

public class TreeBuilder extends RuleSetGrammarBaseListener {
    private RuleSet ruleSet = null;
    private Rule rule = null;
    private LogicalExpression condition = null;

    private Stack<LogicalExpression> logicalExpressions = new Stack<>();
    private Stack<ComparisonOperand> comparisonOperands = new Stack<>();
    private Stack<ArithmeticExpression> arithmeticExpressions = new Stack<>();

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    @Override
    public void enterRule_set(@NotNull RuleSetGrammarParser.Rule_setContext ctx) {
        assert ruleSet == null;
        assert rule == null;
        assert condition == null;

        assert logicalExpressions.empty();
        assert comparisonOperands.empty();
        assert arithmeticExpressions.empty();

        this.ruleSet = new RuleSet();
    }

    @Override
    public void enterSingle_rule(@NotNull RuleSetGrammarParser.Single_ruleContext ctx) {
        this.rule = new Rule();
    }

    @Override
    public void exitConclusion(@NotNull RuleSetGrammarParser.ConclusionContext ctx) {
        this.rule.setConclusion(ctx.getText());
    }

    @Override
    public void exitSingle_rule(@NotNull RuleSetGrammarParser.Single_ruleContext ctx) {
        this.rule.setCondition(this.logicalExpressions.pop());
        this.ruleSet.addRule(this.rule);
        this.rule = null;
    }

    @Override
    public void exitNumericVariable(@NotNull RuleSetGrammarParser.NumericVariableContext ctx) {
        this.arithmeticExpressions.add(new NumericVariable(ctx.getText()));
    }

    @Override
    public void exitNumericConst(@NotNull RuleSetGrammarParser.NumericConstContext ctx) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String pattern = "#0.0#";

        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal value;
        try {
            value = (BigDecimal)decimalFormat.parse(ctx.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.arithmeticExpressions.push(new NumericConstant(value));
    }

    @Override
    public void enterNumericConst(@NotNull RuleSetGrammarParser.NumericConstContext ctx) {
        super.enterNumericConst(ctx);
    }

    @Override
    public void exitArithmeticExpressionMult(@NotNull RuleSetGrammarParser.ArithmeticExpressionMultContext ctx) {
        exitRealArithmeticExpression("*");
    }

    @Override
    public void exitArithmeticExpressionDiv(@NotNull RuleSetGrammarParser.ArithmeticExpressionDivContext ctx) {
        exitRealArithmeticExpression("/");
    }

    @Override
    public void exitArithmeticExpressionPlus(@NotNull RuleSetGrammarParser.ArithmeticExpressionPlusContext ctx) {
        exitRealArithmeticExpression("+");
    }

    @Override
    public void exitArithmeticExpressionMinus(@NotNull RuleSetGrammarParser.ArithmeticExpressionMinusContext ctx) {
        exitRealArithmeticExpression("-");
    }

    protected void exitRealArithmeticExpression(String op) {
        // popping order matters
        ArithmeticExpression right = this.arithmeticExpressions.pop();
        ArithmeticExpression left = this.arithmeticExpressions.pop();
        RealArithmeticExpression expr = new RealArithmeticExpression(op, left, right);
        this.arithmeticExpressions.push(expr);
    }

    @Override
    public void exitArithmeticExpressionNegation(
            @NotNull RuleSetGrammarParser.ArithmeticExpressionNegationContext ctx) {
        Negation negation = new Negation(this.arithmeticExpressions.pop());
        this.arithmeticExpressions.push(negation);
    }

    @Override
    public void exitComparison_operand(@NotNull RuleSetGrammarParser.Comparison_operandContext ctx) {
        ArithmeticExpression expr = this.arithmeticExpressions.pop();
        this.comparisonOperands.push(expr);
    }

    @Override
    public void exitComparisonExpressionWithOperator(
            @NotNull RuleSetGrammarParser.ComparisonExpressionWithOperatorContext ctx) {
        // popping order matters
        ComparisonOperand right = this.comparisonOperands.pop();
        ComparisonOperand left = this.comparisonOperands.pop();
        String op = ctx.getChild(1).getText();
        ComparisonExpression expr = new ComparisonExpression(op, left, right);
        this.logicalExpressions.push(expr);
    }

    @Override
    public void exitLogicalConst(@NotNull RuleSetGrammarParser.LogicalConstContext ctx) {
        switch (ctx.getText().toUpperCase()) {
            case "TRUE":
                this.logicalExpressions.push(LogicalConstant.getTrue());
                break;
            case "FALSE":
                this.logicalExpressions.push(LogicalConstant.getFalse());
                break;
            default:
                throw new RuntimeException("Unknown logical constant: " + ctx.getText());
        }
    }

    @Override
    public void exitLogicalVariable(@NotNull RuleSetGrammarParser.LogicalVariableContext ctx) {
        LogicalVariable variable = new LogicalVariable(ctx.getText());
        this.logicalExpressions.push(variable);
    }

    @Override
    public void exitLogicalExpressionOr(@NotNull RuleSetGrammarParser.LogicalExpressionOrContext ctx) {
        // popping order matters
        LogicalExpression right = logicalExpressions.pop();
        LogicalExpression left = logicalExpressions.pop();
        OrExpression expr = new OrExpression(left, right);
        this.logicalExpressions.push(expr);
    }

    @Override
    public void exitLogicalExpressionAnd(@NotNull RuleSetGrammarParser.LogicalExpressionAndContext ctx) {
        // popping order matters
        LogicalExpression right = logicalExpressions.pop();
        LogicalExpression left = logicalExpressions.pop();
        AndExpression expr = new AndExpression(left, right);
        this.logicalExpressions.push(expr);
    }
}
