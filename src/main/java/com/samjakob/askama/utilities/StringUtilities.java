package com.samjakob.askama.utilities;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StringUtilities {

    /**
     * Words where the capitalization should be preserved.
     */
    private static final List<String> properNouns = List.of(
        "Rust"
    );

    @NotNull
    @Nls(capitalization = Nls.Capitalization.Sentence)
    public static String sentenceCase(@NotNull @Nls(capitalization = Nls.Capitalization.Title) String titleText) {
        if (titleText.isBlank()) {
            //noinspection DialogTitleCapitalization
            return titleText;
        }

        StringBuilder text = new StringBuilder();

        int leadingSpaces = 0;
        if (titleText.startsWith(" ")) {
            for (int i = 0; i < titleText.length(); i++) {
                if (titleText.charAt(i) == ' ') {
                    leadingSpaces++;
                } else {
                    break;
                }
            }
        }
        titleText = titleText.substring(leadingSpaces);

        int trailingSpaces = 0;
        if (titleText.endsWith(" ") && !titleText.isBlank()) {
            for (int i = titleText.length() - 1; i >= 0; i--) {
                if (titleText.charAt(i) == ' ') {
                    trailingSpaces++;
                } else {
                    break;
                }
            }
        }
        titleText = titleText.substring(0, titleText.length() - trailingSpaces);

        boolean hasFirstWord = false;
        for (String word : titleText.split(" ")) {
            if (hasFirstWord) {
                text.append(' ');

                if (!properNouns.contains(word)) {
                    text.append(word.substring(0, 1).toLowerCase()).append(word.substring(1));
                } else {
                    text.append(word);
                }
            } else {
                text.append(word);
            }
            hasFirstWord = true;
        }

        text.insert(0, " ".repeat(leadingSpaces));
        text.append(" ".repeat(trailingSpaces));

        return text.toString();
    }

}
