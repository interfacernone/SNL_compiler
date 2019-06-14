package com.liuziling.snl.compiler.frontend.syntax.LL1;

import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.lexer.TokenType;
import com.liuziling.snl.compiler.frontend.syntax.ParseResult;
import com.liuziling.snl.compiler.frontend.syntax.SyntaxParser;
import com.liuziling.snl.compiler.frontend.syntax.SyntaxTree;
import com.liuziling.snl.compiler.frontend.syntax.TreeNode;
import com.liuziling.snl.compiler.frontend.syntax.symbol.NonTerminalSymbol;
import com.liuziling.snl.compiler.frontend.syntax.symbol.Symbol;
import com.liuziling.snl.compiler.frontend.syntax.symbol.TerminalSymbol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LL1Parser extends SyntaxParser {
    private static final Logger LOG = LoggerFactory.getLogger(LL1Parser.class);

    @Override
    public ParseResult parse(List<Token> tokenList) {
        list = tokenList;
        errors = new ArrayList<>();
        for (Token t : list) {
            LOG.trace(t.toString());
        }
        Stack<Symbol> stack = new Stack<>();
        final NonTerminalSymbol start = NonTerminalSymbol.Program();
        //语法树根结点
        final TreeNode root = start.getNode();
        ParseResult result = new ParseResult();

        stack.push(start); //文法开始符压栈

        Symbol symbol;
        while (!stack.empty() && peekToken().getType() != TokenType.EOF) {
            symbol = stack.pop();
            //是终极符，则应该匹配
            if (symbol instanceof TerminalSymbol) {
                TreeNode node = match(((TerminalSymbol) symbol).getToken().getType());
                //如果是ID/INTC/CHARACTER，要设置值
                symbol.getNode().setValue(node.getValue());
            }
            //是非终极符，查LL1表，扩展语法树节点，产生式右部逆序入栈
            else if (symbol instanceof NonTerminalSymbol) {
                NonTerminalSymbol non = (NonTerminalSymbol) symbol;
                List<Symbol> productionRight = LL1Table.find(non, peekToken());
                if (productionRight != null) {
                    int size = productionRight.size();
                    TreeNode[] children = new TreeNode[size];
                    for (int i = 0; i < size; i++) {
                        children[i] = productionRight.get(i).getNode();
                        Symbol s = productionRight.get(size - 1 - i);
                        //epsilon不用入栈
                        if (!(s instanceof TerminalSymbol) || !((TerminalSymbol) s).isEpsilon())
                            stack.push(productionRight.get(size - 1 - i));
                    }
                    non.getNode().setChildren(children);
                    LOG.trace("构建 " + non.getNode().getValue() + " 的子树：" + Arrays.toString(children));
                } else {
                    LOG.trace("查表出错" + non.getValue());
                    errors.add("Unexpected token near `" + lastRead.getValue() + "`. at "
                            + lastRead.getLine() + ":" + lastRead.getColumn());
                }
            } else {
                errors.add("未识别的符号,这个错误不应该出现");
            }
        }

        //最后格局应为()(#)
        if (!stack.empty() || getToken().getType() != TokenType.EOF) {
            LOG.warn("当前格局应为 栈空，输入流只剩结束符");
            errors.add("Source code too long.");
        }
        if (peekToken().getType() != TokenType.ERROR) {
            LOG.warn("当前格局应为 栈空，输入流[只]剩结束符");
            errors.add("Source code too long.");
        }
        result.setTree(new SyntaxTree(root));
        result.setErrors(errors);
        return result;
    }

}
