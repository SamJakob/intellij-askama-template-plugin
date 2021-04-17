package com.samjakob.askama.parser;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.samjakob.askama.editor.AskamaSyntaxHighlighter;
import com.samjakob.askama.lexer.AskamaLexerAdapter;
import com.samjakob.askama.lexer.AskamaTokenTypes;
import com.samjakob.askama.psi.impl.AskamaFile;
import org.jetbrains.annotations.NotNull;

public class AskamaParserDefinition implements ParserDefinition {

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new AskamaLexerAdapter();
    }

    @NotNull
    @Override
    public PsiParser createParser(Project project) {
        return new AskamaParser();
    }

    @NotNull
    @Override
    public IFileElementType getFileNodeType() {
        return AskamaTokenTypes.FILE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return AskamaSyntaxHighlighter.TS_WHITESPACE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return AskamaSyntaxHighlighter.TS_COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return AskamaSyntaxHighlighter.TS_STRING_LITERAL;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode astNode) {
        return new ASTWrapperPsiElement(astNode);
    }

    @NotNull
    @Override
    public PsiFile createFile(@NotNull FileViewProvider fileViewProvider) {
        return new AskamaFile(fileViewProvider);
    }

}
