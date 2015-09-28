package sg.atom.core.io;

import java.nio.charset.Charset;

public enum EncodingType {

    UTF8("UTF-8"),
    ISO_8859_1("ISO-8859-1"),
    ISO_8859_15("ISO-8859-15"),
    MS949("ms949"),
    UTF16("UTF-16"),
    UTF16BE("UTF-16BE"),
    UTF16LE("UTF-16LE"),
    US_ASCII("US-ASCII"),
    WINDOWS_1252("windows-1252"),;
    private final Charset charset;

    private EncodingType(String name) {
        this.charset = Charset.forName(name);
    }

    public Charset getCharset() {
        return charset;
    }
}