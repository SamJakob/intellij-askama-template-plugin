package com.samjakob.askama.file;

import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.samjakob.askama.AskamaIcons;
import com.samjakob.askama.AskamaLanguage;
import com.samjakob.askama.editor.AskamaTemplateHighlighter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AskamaFileType extends LanguageFileType {

    public static final AskamaFileType INSTANCE = new AskamaFileType();

    public static final String DEFAULT_EXTENSION = "askama";

    private AskamaFileType() {
        super(AskamaLanguage.getInstance());

        FileTypeEditorHighlighterProviders.getInstance().addExplicitExtension(this, (project, fileType, virtualFile, colors) -> new AskamaTemplateHighlighter(project, virtualFile, colors));
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
