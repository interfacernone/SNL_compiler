package com.liuziling.snl.compiler.frontend.syntax.symbol;

import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.lexer.TokenType;
import com.liuziling.snl.compiler.frontend.syntax.TreeNode;

public class TerminalSymbol extends Symbol {
    //不同于开始符，因为 空 不会产生子树，因此可以重用同一个
    public static final TerminalSymbol epsilon = new TerminalSymbol(new Token("空"));
    private final TreeNode node;
    private Token token;

    public TerminalSymbol(Token token) {
        super();
        this.token = token;
        node = new TreeNode(token.getValue());
    }

    public TerminalSymbol(TokenType type) {
        this(new Token(type));
    }

    public boolean isEpsilon() {
        return this == epsilon;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public TreeNode getNode() {
        return node;
    }
}
