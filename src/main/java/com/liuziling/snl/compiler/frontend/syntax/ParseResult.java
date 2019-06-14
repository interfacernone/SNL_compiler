package com.liuziling.snl.compiler.frontend.syntax;

import java.util.List;

public class ParseResult {
    private SyntaxTree tree;
    private List<String> errors;

    public boolean isSuccess() {
        return errors == null || errors.size() == 0;
    }

    public SyntaxTree getTree() {
        return tree;
    }

    public void setTree(SyntaxTree tree) {
        this.tree = tree;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
