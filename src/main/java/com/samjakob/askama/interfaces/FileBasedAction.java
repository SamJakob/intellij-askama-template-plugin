package com.samjakob.askama.interfaces;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public interface FileBasedAction extends AskamaAction {

    /**
     * Should the action be enabled for the given file?
     *
     * @param file The file context to consider.
     * @return True if the file is relevant to the current action, false otherwise.
     */
    boolean shouldEnable(@NotNull VirtualFile file);

}
