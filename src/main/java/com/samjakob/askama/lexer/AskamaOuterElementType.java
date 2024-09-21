package com.samjakob.askama.lexer;

import com.intellij.lang.ASTNode;
import com.intellij.psi.templateLanguages.OuterLanguageElementImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.ILeafElementType;
import com.samjakob.askama.AskamaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class AskamaOuterElementType extends IElementType implements ILeafElementType {

    public AskamaOuterElementType(@NotNull @NonNls String debugName) {
        super(debugName, AskamaLanguage.getInstance());
    }

    @NotNull
    public ASTNode createLeafNode(@NotNull CharSequence charSequence) {
        return new OuterLanguageElementImpl(this, charSequence);
    }

}
