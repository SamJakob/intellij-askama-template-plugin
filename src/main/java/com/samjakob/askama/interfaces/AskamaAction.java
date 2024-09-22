package com.samjakob.askama.interfaces;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsContexts.DialogTitle;
import com.intellij.openapi.util.NlsContexts.DialogMessage;
import org.jetbrains.annotations.Nullable;

public interface AskamaAction {

    /**
     * Get the unique identifier for this action.
     * This can often just be an alias for a static field in the implementing class.
     *
     * @return the unique identifier.
     */
    String getId();

    /**
     * Get the name of the action as it was defined in plugin.xml.
     *
     * @return the name of the action.
     */
    default String getName() {
        return ActionManager.getInstance().getAction(this.getId()).getTemplatePresentation().getText();
    }

    default void showActionError(@Nullable Project project, @DialogTitle String title, @DialogMessage String message) {
        Messages.showErrorDialog(project, message, String.format("%s - %s", getName(), title));
    }

}
