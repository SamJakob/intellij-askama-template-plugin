package com.samjakob.askama.editor;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.util.LayerDescriptor;
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter;
import com.intellij.openapi.fileTypes.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.samjakob.askama.lexer.AskamaTokenTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AskamaTemplateHighlighter extends LayeredLexerEditorHighlighter {

    public AskamaTemplateHighlighter(@Nullable Project project,
                                     @Nullable VirtualFile virtualFile,
                                     @NotNull EditorColorsScheme scheme) {
        super(new AskamaSyntaxHighlighter(), scheme);

        FileType type;
        if (project == null || virtualFile == null) {
            type = FileTypes.PLAIN_TEXT;
        } else {
            Language outerLanguage = TemplateDataLanguageMappings.getInstance(project).getMapping(virtualFile);
            if (outerLanguage == null) outerLanguage = HtmlFileType.INSTANCE.getLanguage();
            type = outerLanguage.getAssociatedFileType();
        }

        assert type != null;
        SyntaxHighlighter outerHighlighter = SyntaxHighlighterFactory.getSyntaxHighlighter(type, project, virtualFile);

        assert outerHighlighter != null;
        registerLayer(AskamaTokenTypes.TEMPLATE_HTML_TEXT, new LayerDescriptor(outerHighlighter, ""));
    }

}
