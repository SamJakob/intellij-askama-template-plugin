package com.samjakob.askama.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class AskamaFileViewProviderFactory implements FileViewProviderFactory {

    @Override
    @NotNull
    public FileViewProvider createFileViewProvider(@NotNull VirtualFile virtualFile, Language language, @NotNull PsiManager psiManager, boolean physical) {
        return new AskamaFileViewProvider(psiManager, virtualFile, physical);
    }

}
