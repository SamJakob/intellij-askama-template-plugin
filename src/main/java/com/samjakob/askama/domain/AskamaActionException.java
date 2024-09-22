package com.samjakob.askama.domain;

import com.intellij.openapi.util.NlsContexts.DialogTitle;
import com.intellij.openapi.util.NlsContexts.DialogMessage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AskamaActionException extends Exception {

    @NotNull
    private final String title;

    @NotNull
    private final String description;

    @Nullable
    private final Exception cause;

    public AskamaActionException(@DialogTitle @NotNull String title, @DialogMessage @NotNull String description) {
        this(title, description, null);
    }

    public AskamaActionException(@DialogTitle @NotNull String title, @DialogMessage @NotNull String description, @Nullable Exception cause) {
        super(String.format("%s: %s", title, description));
        this.title = title;
        this.description = description;
        this.cause = cause;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public @NotNull String getDescription() {
        return description;
    }

    @Override
    public @Nullable Exception getCause() {
        return cause;
    }
}
