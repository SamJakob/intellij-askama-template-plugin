package com.samjakob.askama.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.templateLanguages.TemplateDataElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.samjakob.askama.AskamaLanguage;

public interface AskamaTokenTypes {

    IElementType OUTER_ELEMENT_TYPE = new AskamaOuterElementType("ASKAMA_FRAGMENT");
    IElementType TEMPLATE_HTML_TEXT = new AskamaElementType("ASKAMA_TEMPLATE_HTML_TEXT");
    TemplateDataElementType TEMPLATE_DATA = new TemplateDataElementType(
        "ASKAMA_TEMPLATE_DATA",
        AskamaLanguage.getInstance(),
        TEMPLATE_HTML_TEXT,
        OUTER_ELEMENT_TYPE
    );

    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType COMMENT = new AskamaElementType("COMMENT");
    IElementType STRING_LITERAL = new AskamaElementType("STRING_LITERAL");

    IElementType OPENING = new AskamaElementType("OPEN-MACRO");
    IElementType CLOSING = new AskamaElementType("CLOSE-MACRO");

    IElementType ASKAMA_CONTENT = new AskamaElementType("ASKAMA_CONTENT");
    IElementType MACRO_NAME = new AskamaElementType("MACRO_NAME");
    IElementType MACRO_START = new AskamaElementType("MACRO_START");
    IElementType DIRECTIVE_START = new AskamaElementType("DIRECTIVE_START");

    IFileElementType FILE = new IFileElementType("FILE", AskamaLanguage.getInstance());

}
