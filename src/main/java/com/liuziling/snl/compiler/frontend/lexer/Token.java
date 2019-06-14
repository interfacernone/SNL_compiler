package com.liuziling.snl.compiler.frontend.lexer;

public class Token {
    int line;       //行
    int column;     //列
    TokenType type; //类型
    String value;   //含义

    public Token() {
        this(TokenType.EMPTY);
    }

    public Token(String value) {
        this(0, 0, TokenType.EMPTY, value);
    }

    public Token(TokenType type) {
        this(0, 0, type, type.getStr());
    }

    Token(int line, int column, TokenType type, String value) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.value = value;
    }

    /**
     * 如果是保留字则将类型改为相应的类型
     */
    void checkKeyWords() {
        if (type == TokenType.ID) {
            if (value.equals(TokenType.PROGRAM.getStr())) {
                type = TokenType.PROGRAM;
            } else if (value.equals(TokenType.PROCEDURE.getStr())) {
                type = TokenType.PROCEDURE;
            } else if (value.equals(TokenType.TYPE.getStr())) {
                type = TokenType.TYPE;
            } else if (value.equals(TokenType.VAR.getStr())) {
                type = TokenType.VAR;
            } else if (value.equals(TokenType.IF.getStr())) {
                type = TokenType.IF;
            } else if (value.equals(TokenType.THEN.getStr())) {
                type = TokenType.THEN;
            } else if (value.equals(TokenType.ELSE.getStr())) {
                type = TokenType.ELSE;
            } else if (value.equals(TokenType.FI.getStr())) {
                type = TokenType.FI;
            } else if (value.equals(TokenType.WHILE.getStr())) {
                type = TokenType.WHILE;
            } else if (value.equals(TokenType.DO.getStr())) {
                type = TokenType.DO;
            } else if (value.equals(TokenType.ENDWH.getStr())) {
                type = TokenType.ENDWH;
            } else if (value.equals(TokenType.DO.getStr())) {
                type = TokenType.DO;
            } else if (value.equals(TokenType.BEGIN.getStr())) {
                type = TokenType.BEGIN;
            } else if (value.equals(TokenType.END.getStr())) {
                type = TokenType.END;
            } else if (value.equals(TokenType.READ.getStr())) {
                type = TokenType.READ;
            } else if (value.equals(TokenType.WRITE.getStr())) {
                type = TokenType.WRITE;
            } else if (value.equals(TokenType.ARRAY.getStr())) {
                type = TokenType.ARRAY;
            } else if (value.equals(TokenType.OF.getStr())) {
                type = TokenType.OF;
            } else if (value.equals(TokenType.RECORD.getStr())) {
                type = TokenType.RECORD;
            } else if (value.equals(TokenType.RETURN.getStr())) {
                type = TokenType.RETURN;
            } else if (value.equals(TokenType.INTEGER.getStr())) {
                type = TokenType.INTEGER;
            } else if (value.equals(TokenType.CHAR.getStr())) {
                type = TokenType.CHAR;
            }
        }
        /*
        PROGRAM("program"), PROCEDURE("procedure"), TYPE("type"), VAR("var"),
        IF("if"), THEN("then"), ELSE("else"), FI("fi"),
        WHILE("while"), DO("do"), ENDWH("endwh"),
        BEGIN("begin"), END("end"), READ("read"), WRITE("write"),
        ARRAY("array"), OF("of"), RECORD("record"), RETURN("return"),
        INT("integer"), CHAR("char"),
        */
    }

    @Override
    public String toString() {
//        return "[line=" + line + ",column=" + column + ",type=" + type + ",value=" + value + "]";
        return "`" + value + "|" + type + "|" + line + ":" + column + "`";
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }


    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
