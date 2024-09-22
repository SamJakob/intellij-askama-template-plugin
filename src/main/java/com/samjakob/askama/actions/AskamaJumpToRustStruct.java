package com.samjakob.askama.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.PopupStep;
import com.intellij.openapi.ui.popup.util.BaseListPopupStep;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.samjakob.askama.domain.AskamaActionException;
import com.samjakob.askama.file.AskamaFileType;
import com.samjakob.askama.interfaces.AskamaActionBase;
import com.samjakob.askama.interfaces.FileBasedAction;
import com.samjakob.askama.psi.impl.RustStructVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rust.lang.RsFileType;
import org.rust.lang.core.psi.RsLitExpr;
import org.rust.lang.core.psi.RsPath;
import org.rust.lang.core.psi.RsStructItem;

import java.nio.file.Path;
import java.util.*;

import static com.samjakob.askama.utilities.PathUtilities.*;

public class AskamaJumpToRustStruct extends AskamaActionBase implements FileBasedAction {

    public static final String TEMPLATE_PROC_MACRO_PATH = "template";
    public static final String TEMPLATE_PROC_MACRO_ARG = "path";

    /**
     * The unique identifier for this action.
     * This needs to match the value in plugin.xml.
     */
    public static final String ID = "com.samjakob.askama.actions.AskamaJumpToRustStruct";

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean shouldEnable(@NotNull VirtualFile file) {
        return file.getFileType().equals(AskamaFileType.INSTANCE);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile activeFile = e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(
                e.getProject() != null &&
                        activeFile != null &&
                        shouldEnable(activeFile)
        );
    }

    @Override
    public void askamaActionPerformed(@NotNull AnActionEvent e) throws AskamaActionException {
        if (e.getProject() == null) {
            throw new AskamaActionException("No Active Project", "No active project could be found.");
        }

        VirtualFile activeFile = e.getDataContext().getData(PlatformDataKeys.VIRTUAL_FILE);
        if (activeFile == null) {
            throw new AskamaActionException("No Active File", "No active file could be found.");
        }

        Path packagePath = getCargoProjectPackagePath(e.getProject(), activeFile)
                .orElseThrow(() -> new AskamaActionException("No Active Cargo Project", "No active cargo project could be found."));

        final boolean inTemplatesDir = isFileInTemplatesDirectory(e.getProject(), activeFile);
        if (!inTemplatesDir) {
            throw new AskamaActionException("Not in Templates Directory", "The active file is not in the templates directory.");
        }

        // Resolve the expected path of the template file in the templates directory.
        String expectedPath = resolveFileToTemplatesDirectory(e.getProject(), activeFile);

        List<RsStructItemWithPath> relevantStructs = new ArrayList<>();
        ProjectRootManager.getInstance(e.getProject()).getFileIndex().iterateContent(
            fileOrDir -> {
                if (fileOrDir.getFileType().equals(RsFileType.INSTANCE)) {
                    final var psiFile = Objects.requireNonNull(PsiManager.getInstance(e.getProject()).findFile(fileOrDir));
                    psiFile.accept(new RustStructVisitor() {
                        @Override
                        public void visitStruct(RsStructItem structItem) {
                            final var templateAttr = structItem.getOuterAttrList().stream()
                                    .filter(attr -> TEMPLATE_PROC_MACRO_PATH.equals(
                                            Optional.ofNullable(attr.getMetaItem().getPath())
                                                    .map(RsPath::getReferenceName)
                                                    .orElse("")
                                    )).findFirst();
                            if (templateAttr.isEmpty()) return;

                            final var templateArgs = templateAttr.get().getMetaItem().getMetaItemArgs();
                            if (templateArgs == null) return;

                            final var pathArg = templateArgs.getMetaItemList().stream()
                                    .filter(arg -> TEMPLATE_PROC_MACRO_ARG.equals(
                                            Optional.ofNullable(arg.getPath())
                                                    .map(RsPath::getIdentifier)
                                                    .map(PsiElement::getText)
                                                    .orElse("")
                                    )).findFirst();
                            if (pathArg.isEmpty()) return;

                            final var pathArgValue = Optional.ofNullable(pathArg.get().getLitExpr())
                                    .map(RsLitExpr::getStringLiteral)
                                    .map(PsiElement::getText)
                                    .map(text -> text.substring(1, text.length() - 1));
                            if (pathArgValue.isEmpty()) return;

                            try {
                                if (pathArgValue.get().equals(expectedPath)) {
                                    relevantStructs.add(new RsStructItemWithPath(structItem, fileOrDir.toNioPath()));
                                }
                            } catch (UnsupportedOperationException ignored) {
                                // If we can't get the path of the file, just return.
                            }
                        }
                    });
                    return false;
                }

                return true;
            }
        );

        if (relevantStructs.isEmpty()) {
            Messages.showInfoMessage(e.getProject(), "No template struct was found referencing this file.", "No Matching Struct");
            return;
        }

        if (relevantStructs.size() == 1) {
            relevantStructs.getFirst().structItem.navigate(true);
        } else {
            // Otherwise, if there's more than one struct that matches the template path,
            // show a dialog to the user to select which one they want to navigate to.

            // Create a list of selectable struct entries (this extracts the name and path of the struct
            // so we don't send the entire PsiElement to the UI thread).
            final var selectableRelevantStructs = relevantStructs.stream()
                    .map(item -> new NavigationEntry(
                            item.structItem.getName(),
                            packagePath.relativize(item.path).toString(),
                            relevantStructs.indexOf(item)
                    ))
                    .toList();

            // Show the list popup with our custom elements.
            JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<>(getName(), selectableRelevantStructs) {
                @Override
                public @NotNull String getTextFor(NavigationEntry value) {
                    return Objects.requireNonNull(value.getText());
                }

                @Override
                public String getIndexedString(NavigationEntry value) {
                    return getTextFor(value);
                }

                @Override
                public @Nullable PopupStep<?> onChosen(NavigationEntry selectedValue, boolean finalChoice) {
                    // Then just index the selected value and navigate to the struct.
                    relevantStructs.get(selectedValue.entry()).structItem.navigate(true);
                    return PopupStep.FINAL_CHOICE;
                }
            }).showCenteredInCurrentWindow(e.getProject());
        }
    }

    private record RsStructItemWithPath(RsStructItem structItem, Path path) {}

    private record NavigationEntry(String name, String path, int entry) {
        public String getText() {
            return String.format("%s (%s)", name, path);
        }
    }

}
