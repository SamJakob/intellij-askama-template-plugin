package com.samjakob.askama.lexer;

import com.intellij.psi.tree.IElementType;
import com.samjakob.askama.AskamaLanguage;
import org.jetbrains.annotations.NotNull;

public class AskamaElementType extends IElementType {

    public AskamaElementType(@NotNull String debugName) {
        super(debugName, AskamaLanguage.getInstance());
    }

    public String toString() {
        return "[Askama] " + super.toString();
    }

}
