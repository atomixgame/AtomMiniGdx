package sg.atom.core.io.json;

import java.io.InputStream;
import sg.atom.core.io.FileHandle;


public interface BaseJsonReader {
	JsonValue parse (InputStream input);
	JsonValue parse (FileHandle file);
}
