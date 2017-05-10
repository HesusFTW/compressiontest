package service.archiver;

import model.AbstractArchive;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class TarArchiveService extends AbstractArchive {

	private Path archive = Paths.get("src/main/resources/tar/compress/archive.tar");
	private Path decompressFolder = Paths.get("src/main/resources/tar/decompress/");

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

	public void init() {
		entries.add(new TarArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new TarArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new TarArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));

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
//      init
		TarArchiveService tarArchiveService = new TarArchiveService();
		tarArchiveService.init();
//		compress
		tarArchiveService.compress(tarArchiveService.getFiles(),
				tarArchiveService.getEntries(),
				tarArchiveService.getArchiveOutputStream(ARCHIVER_TAR, tarArchiveService.getArchive()));
//      decompress
		tarArchiveService.decompress(
				tarArchiveService.getArchiveInputStream(ARCHIVER_TAR, tarArchiveService.getArchive()),
				tarArchiveService.getDecompressFolder());
//		destroy
		tarArchiveService.destroy();
	}

}
