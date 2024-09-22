package com.samjakob.askama.utilities;

import com.intellij.util.io.DataExternalizer;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class RawStringExternalizer implements DataExternalizer<String> {

    @Override
    public void save(@NotNull DataOutput out, String value) throws IOException {
        out.writeUTF(value);
    }

    @Override
    public String read(@NotNull DataInput in) throws IOException {
        return in.readUTF();
    }

}
