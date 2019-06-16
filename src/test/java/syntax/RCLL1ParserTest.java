package syntax;

import com.liuziling.snl.compiler.frontend.lexer.Lexer;
import com.liuziling.snl.compiler.frontend.lexer.LexerResult;
import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.syntax.LL1.LL1Parser;
import com.liuziling.snl.compiler.frontend.syntax.ParseResult;
import com.liuziling.snl.compiler.frontend.syntax.SyntaxTree;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class RCLL1ParserTest {
    private List<Token> list;
    private LexerResult result;

    @Before
    public void init() throws IOException {
        InputStream in = RCLL1ParserTest.class.getClassLoader().getResourceAsStream("test.snl");
        result = new Lexer().getResult(new InputStreamReader(in));
        list = result.getTokenList();

        for (Token t : list) System.out.println(t);
    }

    @Test
    public void test() throws FileNotFoundException {
        if (result.getErrors().size() > 0) {
            result.getErrors().forEach(System.out::println);
            return;
        }
        ParseResult result = new LL1Parser().parse(list);
//        ParseResult result = new RDParser().parse(list);
        if (result.isSuccess()) {
            SyntaxTree tree = result.getTree();
            System.out.println();
            SyntaxTree.print(tree.getRoot(), new PrintStream(System.out),
                    "以下是语法树", 5);
        } else {
            System.err.println("RDParser Error!");
            result.getErrors().forEach(System.err::println);
        }
    }

}
