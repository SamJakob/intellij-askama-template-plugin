package com.samjakob.askama.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.samjakob.askama.file.AskamaFileType;
import com.samjakob.askama.AskamaLanguage;
import org.jetbrains.annotations.NotNull;

public class AskamaFile extends PsiFileBase {

    public AskamaFile(FileViewProvider provider) {
        super(provider, AskamaLanguage.getInstance());
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return AskamaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "AskamaFile:" + getName();
    }

}
