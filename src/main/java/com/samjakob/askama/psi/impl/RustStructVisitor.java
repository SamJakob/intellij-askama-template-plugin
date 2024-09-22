package com.samjakob.askama.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiRecursiveElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.rust.lang.core.psi.RsElementTypes;
import org.rust.lang.core.psi.RsOuterAttr;
import org.rust.lang.core.psi.RsStructItem;

public abstract class RustStructVisitor extends PsiRecursiveElementVisitor {

    @Override
    public void visitElement(@NotNull PsiElement element) {
        super.visitElement(element);

        if (element.getNode().getElementType().getIndex() == RsElementTypes.STRUCT_ITEM.getIndex()) {
            visitStruct((RsStructItem) element);
        }
    }

    public void visitStruct(RsStructItem structItem) { /* Stub */ }

}
