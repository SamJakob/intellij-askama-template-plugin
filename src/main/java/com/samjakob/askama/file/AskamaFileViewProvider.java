package com.samjakob.askama.file;

import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.LanguageUtil;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider;
import com.intellij.psi.tree.IElementType;
import com.samjakob.askama.AskamaLanguage;
import com.samjakob.askama.lexer.AskamaTokenTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AskamaFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements TemplateLanguageFileViewProvider {

    private final Language myTemplateDataLanguage;
    private static final IElementType templateDataElement = AskamaTokenTypes.TEMPLATE_DATA;

    public AskamaFileViewProvider(PsiManager manager, VirtualFile file, boolean physical) {
        super(manager, file, physical);

        Language mainLanguage = TemplateDataLanguageMappings.getInstance(manager.getProject()).getMapping(file);
        if (mainLanguage == null) mainLanguage = HtmlFileType.INSTANCE.getLanguage();

        if (mainLanguage instanceof TemplateLanguage) {
            myTemplateDataLanguage = PlainTextLanguage.INSTANCE;
        } else {
            myTemplateDataLanguage = LanguageUtil.getLanguageForPsi(
                    manager.getProject(),
                    file,
                    mainLanguage.getAssociatedFileType()
            );
        }
    }

    public AskamaFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean physical, Language myTemplateDataLanguage) {
        super(psiManager, virtualFile, physical);
        this.myTemplateDataLanguage = myTemplateDataLanguage;
    }

    @NotNull
    @Override
    public Language getBaseLanguage() {
        return AskamaLanguage.INSTANCE;
    }

    @NotNull
    @Override
    public Language getTemplateDataLanguage() {
        return myTemplateDataLanguage;
    }

    @NotNull
    @Override
    public Set<Language> getLanguages() {
        return new HashSet<>(Arrays.asList(AskamaLanguage.INSTANCE, myTemplateDataLanguage));
    }

    @NotNull
    @Override
    protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(@NotNull VirtualFile virtualFile) {
        return new AskamaFileViewProvider(getManager(), virtualFile, false, myTemplateDataLanguage);
    }

    @Override
    protected PsiFile createFile(@NotNull Language language) {
        if (language == myTemplateDataLanguage) {

            PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(language).createFile(this);
            file.setContentElementType(templateDataElement);
            return file;

        } else if (language == AskamaLanguage.INSTANCE) {

            return LanguageParserDefinitions.INSTANCE.forLanguage(language).createFile(this);

        } else {
            return null;
        }
    }

}
