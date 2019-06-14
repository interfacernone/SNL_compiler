package com.liuziling.snl.compiler.frontend.lexer;

import java.util.List;

public class LexerResult {
    private List<Token> tokenList;
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }
}
