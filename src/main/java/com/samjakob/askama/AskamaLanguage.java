package com.samjakob.askama;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

public class AskamaLanguage extends Language implements TemplateLanguage {

    private static final AskamaLanguage INSTANCE = new AskamaLanguage();

    private AskamaLanguage() {
        super("Askama");
    }

    public static AskamaLanguage getInstance() {
        return INSTANCE;
    }

}
