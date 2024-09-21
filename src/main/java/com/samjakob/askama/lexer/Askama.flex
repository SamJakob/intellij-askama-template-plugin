package com.samjakob.askama.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.samjakob.askama.lexer.AskamaTokenTypes.*;

%%

%class AskamaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{   return;
%eof}

STRING_LITERAL = \"(\\.|[^\"\\])*\"
WHITE_SPACE_CHAR = [\ \n\r\t\f]
COMMENT = "{#" ~"#}"
OPENING = "{"
CLOSING = "}"
MACRO_START = "{"
MACRO_END = "}"
DIRECTIVE_START = "%"
DIRECTIVE_END = "%"

%state ASKAMA_TRIGGER
%state ASKAMA_MACRO
%state ASKAMA_DIRECTIVE

%%

<YYINITIAL> {
    {COMMENT}                     { yybegin(YYINITIAL); return COMMENT; }

    {OPENING}{DIRECTIVE_START}    { yybegin(ASKAMA_DIRECTIVE); return OPENING; }
    {OPENING}{MACRO_START}        { yybegin(ASKAMA_MACRO); return OPENING; }

//  The Rust compiler converts all CRLF to LF, so we only need to handle LF.
    \n                            { return TEMPLATE_HTML_TEXT; }
    .                             { return TEMPLATE_HTML_TEXT; }
}

<ASKAMA_DIRECTIVE> {
    {WHITE_SPACE_CHAR}+           { return WHITE_SPACE; }
    {STRING_LITERAL}              { return STRING_LITERAL; }
    {DIRECTIVE_END}{CLOSING}      { yybegin(YYINITIAL); return CLOSING; }

    \n                            { return ASKAMA_CONTENT; }
    .                             { return ASKAMA_CONTENT; }
}

<ASKAMA_MACRO> {
    {WHITE_SPACE_CHAR}+           { return WHITE_SPACE; }
    {STRING_LITERAL}              { return STRING_LITERAL; }
    {MACRO_END}{CLOSING}          { yybegin(YYINITIAL); return CLOSING; }

    \n                            { return ASKAMA_CONTENT; }
    .                             { return ASKAMA_CONTENT; }
}

.                                 { return BAD_CHARACTER; }
