package service.archiver;

import model.AbstractArchive;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class ArArchiveService extends AbstractArchive {

	private Path archive = Paths.get("src/main/resources/ar/compress/archive.ar");
	private Path decompressFolder = Paths.get("src/main/resources/ar/decompress/");

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

	public void init() {
		entries.add(new ArArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new ArArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new ArArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));

		files.add(ABC_FILE);
		files.add(DEFG_FILE);
		files.add(HIJKLMNOP_FILE);
	}

	public void destroy() {
		entries = new LinkedList<>();
		files = new LinkedList<>();
	}

	public List<Path> getFiles() {
		return files;
	}

	public List<ArchiveEntry> getEntries() {
		return entries;
	}

	public Path getArchive() {
		return archive;
	}

	public Path getDecompressFolder() {
		return decompressFolder;
	}

	public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
//		init
		ArArchiveService arArchiveService = new ArArchiveService();
		arArchiveService.init();
//      compress
		arArchiveService.compress(arArchiveService.getFiles(),
				arArchiveService.getEntries(),
				arArchiveService.getArchiveOutputStream(ARCHIVER_AR, arArchiveService.getArchive()));
//      decompress
		arArchiveService.decompress(
				arArchiveService.getArchiveInputStream(ARCHIVER_AR, arArchiveService.getArchive()),
				arArchiveService.getDecompressFolder());
//		destroy
		arArchiveService.destroy();
	}

}
