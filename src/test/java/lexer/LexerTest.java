package lexer;

import com.liuziling.snl.compiler.frontend.lexer.Lexer;
import com.liuziling.snl.compiler.frontend.lexer.LexerResult;
import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.lexer.TokenType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LexerTest {
    private Lexer lexer;
    private InputStream source;

    @Before
    public void init() {
        source = LexerTest.class.getClassLoader().getResourceAsStream("test.snl");
        lexer = new Lexer();
    }

    @Test
    public void test() throws IOException {
        LexerResult result = lexer.getResult(new InputStreamReader(source));
        if (result.getErrors().size() == 0) {
            List<Token> list = result.getTokenList();
            Assert.assertTrue(list.size() > 0);
            for (Token t : list) {
                if (t.getValue() != null && t.getValue().equals(TokenType.PROGRAM.getStr())) {
                    Assert.assertEquals(TokenType.PROGRAM, t.getType());
                }
            }
        } else {
            Assert.assertTrue(result.getErrors().size() > 0);
        }
    }

    @After
    public void after() {
        InputStream source = LexerTest.class.getClassLoader().getResourceAsStream("test.snl");
        System.out.println("测试完成，源文件是:");
        System.out.println("//----------开始");
        System.out.println(Lexer.getSourceFileAsString(source));
        System.out.println("//----------结束");
    }
}
