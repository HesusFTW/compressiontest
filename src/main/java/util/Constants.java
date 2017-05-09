package util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
	public static final Path ABC_FILE = Paths.get("src/main/resources/original/abc.txt");
	public static final Path DEFG_FILE = Paths.get("src/main/resources/original/defg.json");
	public static final Path HIJKLMNOP_FILE = Paths.get("src/main/resources/original/hijklmnop.xml");

	public static final String ABC_FILE_NAME = "abc.txt";
	public static final String DEFG_FILE_NAME = "defg.json";
	public static final String HIJKLMNOP_FILE_NAME = "hijklmnop.xml";

	public static final String ARCHIVER_AR = "ar";
	public static final String ARCHIVER_CPIO = "cpio";
	public static final String ARCHIVER_TAR = "tar";
	public static final String ARCHIVER_ZIP = "zip";

	public static final String COMPRESSOR_GZIP = "gz";
}
