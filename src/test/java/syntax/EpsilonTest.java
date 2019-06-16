package syntax;

import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.syntax.symbol.TerminalSymbol;
import org.junit.Assert;
import org.junit.Test;

public class EpsilonTest {
    @Test
    public void test() {
        TerminalSymbol epsilon = TerminalSymbol.epsilon;
        Assert.assertTrue(epsilon.isEpsilon());
        Assert.assertTrue(!new TerminalSymbol(new Token()).isEpsilon());
    }
}
