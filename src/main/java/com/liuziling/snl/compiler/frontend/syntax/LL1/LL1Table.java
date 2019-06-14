package com.liuziling.snl.compiler.frontend.syntax.LL1;

import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.syntax.symbol.NonTerminalSymbol;
import com.liuziling.snl.compiler.frontend.syntax.symbol.Symbol;
import com.youthlin.snl.compiler.frontend.syntax.symbol.NON_TERMINAL_SYMBOLS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LL1Table {
    private static final Logger LOG = LoggerFactory.getLogger(LL1Table.class);

    static List<Symbol> find(NonTerminalSymbol nonTerminalSymbol, Token predict) {
        return lookUp(nonTerminalSymbol, predict);
    }

    private static List<Symbol> lookUp(NonTerminalSymbol nonTerminalSymbol, Token predict) {
        String value = nonTerminalSymbol.getValue();
        LOG.trace("查表 非终极符=" + value + ", 展望符=" + predict.getValue());
        NON_TERMINAL_SYMBOLS symbols = NON_TERMINAL_SYMBOLS.valueOf(value);
        return symbols.find(predict);
    }
}
