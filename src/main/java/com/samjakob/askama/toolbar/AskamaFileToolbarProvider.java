package com.samjakob.askama.toolbar;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationProvider;
import com.samjakob.askama.actions.AskamaJumpToRustStruct;
import com.samjakob.askama.interfaces.FileBasedAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class AskamaFileToolbarProvider implements EditorNotificationProvider {

    @Override
    public boolean isDumbAware() {
        return false;
    }

    @Override
    public @Nullable Function<? super @NotNull FileEditor, ? extends @Nullable JComponent> collectNotificationData(@NotNull Project project, @NotNull VirtualFile file) {
        List<FileBasedAction> actions = Collections.singletonList(
                (AskamaJumpToRustStruct) ActionManager.getInstance().getAction(AskamaJumpToRustStruct.ID)
        );

        if (actions.stream().noneMatch(action -> action.shouldEnable(file))) {
            return null;
        }

        return editor -> new AskamaFileToolbarPanel(project, file);
    }
}
