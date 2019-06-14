package com.liuziling.snl.compiler;

import com.liuziling.snl.compiler.frontend.lexer.Lexer;
import com.liuziling.snl.compiler.frontend.lexer.LexerResult;
import com.liuziling.snl.compiler.frontend.lexer.Token;
import com.liuziling.snl.compiler.frontend.syntax.LL1.LL1Parser;
import com.liuziling.snl.compiler.frontend.syntax.ParseResult;
import com.liuziling.snl.compiler.frontend.syntax.SyntaxParser;
import com.liuziling.snl.compiler.frontend.syntax.SyntaxTree;
import com.liuziling.snl.compiler.frontend.syntax.recursivedescent.RDParser;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class SNLcompiler {
    private static final Logger LOG = LoggerFactory.getLogger(SNLcompiler.class);

    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("r", "RD", false, "(default)use recursive descent to parse.");
        options.addOption("l", "LL1", false, "use LL1 to parse.");
        options.addOption("h", "help", false, "show this help text and exit.");
        options.addOption("v", "version", false, "show version text and exit.");
        options.addOption("e", "encoding", true, "specify default encoding to open file.");
        String defaultEncoding = "UTF-8";
        String defaultParser = "Recursive Descent";
        try {
            CommandLine cli = new DefaultParser().parse(options, args);
            if (cli.hasOption("h") || cli.hasOption("v")) {
                if (cli.hasOption("h")) help(options);
                if (cli.hasOption("v")) version();
                return;
            }

            String[] arg = cli.getArgs();
            if (arg.length != 1) {
                help(options);
                return;
            }

            if (cli.hasOption("e")) defaultEncoding = cli.getOptionValue("e");
            InputStream in = new FileInputStream(arg[0]);
            //http://akini.mbnet.fi/java/unicodereader/
            UnicodeReader unicodeReader = new UnicodeReader(in, defaultEncoding);

            //语法分析器
            SyntaxParser parser;
            if (cli.hasOption("l")) {
                parser = new LL1Parser();
                defaultParser = "LL1";
            } else parser = new RDParser();
            LOG.debug("参数: Parser=" + defaultParser + ", Encoding=" + defaultEncoding);
            //词法分析器
            Lexer lexer = new Lexer();
            LexerResult lexerResult = lexer.getResult(unicodeReader);
            List<Token> list = null;
            List<String> errors = lexerResult.getErrors();
            if (errors.size() == 0) {
                PrintStream out = new PrintStream(arg[0] + ".token.list.txt");
                list = lexerResult.getTokenList();
                list.forEach(out::println);
            } else {
                errors.forEach(System.err::println);
                System.exit(1);
            }
            //无词法错误
            ParseResult result = parser.parse(list);
            if (result == null) {
                System.err.println("获取分析结果错误");
                System.exit(1);
            }
            if (result.isSuccess()) {
                SyntaxTree.print(result.getTree().getRoot(), new PrintStream(arg[0] + ".tree.txt"),
                        "Syntax Tree for source code: " + arg[0] + "(by " + defaultParser + ")", 0);
            } else {
                System.err.println(defaultParser + " Parser: parse Error. Error List:");
                result.getErrors().forEach(System.err::println);
            }
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            help(options);
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found.找不到源文件。");
            help(options);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void help(Options options) {
        new HelpFormatter().printHelp("SNLc [-r] [-l] [-h] [-v] <SourceFile>", options);
    }

    private static void version() {
        System.out.println("SNLc   : SNL(Small Nested Language) Compiler.");
        System.out.println("Version: 1.0.");
        System.out.println("Author : Liu ziling ,Du qiao");
    }
}
