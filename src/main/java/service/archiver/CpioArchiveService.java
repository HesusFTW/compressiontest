package service.archiver;

import model.AbstractArchive;
import org.apache.commons.compress.archivers.*;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class CpioArchiveService extends AbstractArchive {

	private Path archive = Paths.get("src/main/resources/cpio/compress/archive.cpio");
	private Path decompressFolder = Paths.get("src/main/resources/cpio/decompress/");

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

	public void init() {
		entries.add(new CpioArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new CpioArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new CpioArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));

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
		CpioArchiveService cpioArchiveService = new CpioArchiveService();
		cpioArchiveService.init();
//		compress
		cpioArchiveService.compress(cpioArchiveService.getFiles(),
				cpioArchiveService.getEntries(),
				cpioArchiveService.getArchiveOutputStream(ARCHIVER_CPIO, cpioArchiveService.getArchive()));
//      decompress
		cpioArchiveService.decompress(
				cpioArchiveService.getArchiveInputStream(ARCHIVER_CPIO, cpioArchiveService.getArchive()),
				cpioArchiveService.getDecompressFolder());
//		destroy
		cpioArchiveService.destroy();
	}

}
