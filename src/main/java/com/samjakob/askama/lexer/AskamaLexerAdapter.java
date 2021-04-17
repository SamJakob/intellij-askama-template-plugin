package com.samjakob.askama.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

public class AskamaLexerAdapter extends MergingLexerAdapter {

    private static final TokenSet TOKENS_TO_MERGE = TokenSet.create(
            AskamaTokenTypes.COMMENT,
            AskamaTokenTypes.WHITE_SPACE,
            AskamaTokenTypes.TEMPLATE_HTML_TEXT
    );

    public AskamaLexerAdapter() {
        super(new FlexAdapter(new AskamaLexer(null)), TOKENS_TO_MERGE);
    }

}
