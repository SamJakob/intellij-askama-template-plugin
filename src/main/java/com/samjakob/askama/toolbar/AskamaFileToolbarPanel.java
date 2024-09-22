package com.samjakob.askama.toolbar;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.colors.EditorColors;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.EditorNotificationPanel;
import com.intellij.ui.HyperlinkLabel;
import com.samjakob.askama.AskamaIcons;
import com.samjakob.askama.actions.AskamaJumpToRustStruct;
import com.samjakob.askama.utilities.StringUtilities;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AskamaFileToolbarPanel extends EditorNotificationPanel {

    @NotNull
    final Project project;

    @NotNull
    final VirtualFile virtualFile;

    public AskamaFileToolbarPanel(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        super(EditorColors.GUTTER_BACKGROUND);

        this.project = project;
        this.virtualFile = virtualFile;

        icon(AskamaIcons.FILETYPE_ICON);
        text(virtualFile.getFileType().getName());

        createActionLabel(AskamaJumpToRustStruct.ID);
    }

    private void createActionLabel(@NotNull String id) {
        final Presentation actionPresentation = ActionManager.getInstance().getAction(id).getTemplatePresentation();
        HyperlinkLabel label = createActionLabel(StringUtilities.sentenceCase(Objects.requireNonNull(actionPresentation.getTextWithMnemonic())), id);
        label.setIcon(actionPresentation.getIcon());
    }

}
