package com.samjakob.askama.interfaces;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.samjakob.askama.domain.AskamaActionException;
import org.jetbrains.annotations.NotNull;

public abstract class AskamaActionBase extends AnAction implements AskamaAction {

    @Override
    public final void actionPerformed(@NotNull AnActionEvent e) {
        try {
            askamaActionPerformed(e);
        } catch (AskamaActionException ex) {
            showActionError(e.getProject(), ex.getTitle(), ex.getDescription());
        }
    }

    public abstract void askamaActionPerformed(@NotNull AnActionEvent e) throws AskamaActionException;

}
