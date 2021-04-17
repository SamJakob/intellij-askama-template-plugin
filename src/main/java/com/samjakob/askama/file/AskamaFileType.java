package com.samjakob.askama.file;

import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.EditorHighlighterProvider;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.samjakob.askama.AskamaIcons;
import com.samjakob.askama.AskamaLanguage;
import com.samjakob.askama.editor.AskamaTemplateHighlighter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AskamaFileType extends LanguageFileType {

    public static final AskamaFileType INSTANCE = new AskamaFileType();

    public static final String DEFAULT_EXTENSION = "askama";
    public static final String[] extensions = { DEFAULT_EXTENSION };

    private AskamaFileType() {
        super(AskamaLanguage.INSTANCE);

        FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
            @Override
            public EditorHighlighter getEditorHighlighter(@Nullable Project project,
                                                          @NotNull FileType fileType,
                                                          @Nullable VirtualFile virtualFile,
                                                          @NotNull EditorColorsScheme colors) {
                return new AskamaTemplateHighlighter(project, virtualFile, colors);
            }
        });
    }

    @NotNull
    @Override
    public String getName() {
        return "Askama Template File";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "HTML-based askama template file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AskamaIcons.FILETYPE_ICON;
    }

}
