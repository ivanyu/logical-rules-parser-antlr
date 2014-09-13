package me.ivanyu.compiler;

import org.antlr.v4.runtime.*;

/**
 * Error handler which throws exception on any parsing error.
 */
public class ExceptionThrowingErrorHandler extends DefaultErrorStrategy {
    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        throw new RuntimeException(e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        throw new RuntimeException(new InputMismatchException(recognizer));
    }

    @Override
    public void sync(Parser recognizer) throws RecognitionException {
    }
}
