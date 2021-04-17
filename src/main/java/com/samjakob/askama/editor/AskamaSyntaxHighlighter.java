package com.samjakob.askama.editor;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.samjakob.askama.lexer.AskamaLexerAdapter;
import org.jetbrains.annotations.NotNull;

import static com.samjakob.askama.lexer.AskamaTokenTypes.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AskamaSyntaxHighlighter extends SyntaxHighlighterBase {

    // Text Attribute Keys
    public static final String BAD_CHARACTER_ID = "Bad Character";
    public static final TextAttributesKey TA_KEY_BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
        BAD_CHARACTER_ID, HighlighterColors.BAD_CHARACTER
    );

    public static final String COMMENTS_ID = "Askama Comment";
    public static final TextAttributesKey TA_KEY_COMMENTS = TextAttributesKey.createTextAttributesKey(
        COMMENTS_ID, DefaultLanguageHighlighterColors.BLOCK_COMMENT
    );

    public static final String BRACES_ID = "Braces";
    public static final TextAttributesKey TA_KEY_BRACES = TextAttributesKey.createTextAttributesKey(
        BRACES_ID, DefaultLanguageHighlighterColors.BRACES
    );

    public static final String STRING_LITERAL_ID = "String Literal";
    public static final TextAttributesKey TA_KEY_STRING_LITERAL = TextAttributesKey.createTextAttributesKey(
        STRING_LITERAL_ID, DefaultLanguageHighlighterColors.STRING
    );

    // Token sets (group common IElementType's).
    public static final TokenSet TS_BAD_CHARACTER    = TokenSet.create(BAD_CHARACTER);
    public static final TokenSet TS_COMMENTS         = TokenSet.create(COMMENT);
    public static final TokenSet TS_BRACES           = TokenSet.create(OPENING, CLOSING);
    public static final TokenSet TS_WHITESPACE       = TokenSet.create(WHITE_SPACE);
    public static final TokenSet TS_STRING_LITERAL   = TokenSet.create(STRING_LITERAL);

    // Static container for attributes.
    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();
    static {
        populateFromTokenSet(TS_BAD_CHARACTER,      TA_KEY_BAD_CHARACTER);
        populateFromTokenSet(TS_COMMENTS,           TA_KEY_COMMENTS);
        populateFromTokenSet(TS_BRACES,             TA_KEY_BRACES);
        populateFromTokenSet(TS_STRING_LITERAL,     TA_KEY_STRING_LITERAL);
    }

    @Override
    public @NotNull Lexer getHighlightingLexer() {
        return new AskamaLexerAdapter();
    }

    @Override
    public TextAttributesKey @NotNull[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }

    /**
     * Helper method to populate the internal attributes store. For a given set of elements.
     * @param tokenSet The set of elements that should be represented by <code>attributesKey</code>.
     * @param attributesKey The {@link TextAttributesKey} that should be used by IntelliJ to represent each token type
     *                      in <code>tokenSet</code>.
     */
    private static void populateFromTokenSet(TokenSet tokenSet, TextAttributesKey attributesKey) {
        Arrays.stream(tokenSet.getTypes()).forEach(elementType -> ATTRIBUTES.put(elementType, attributesKey));
    }

}
