package service;

import model.AbstractArchive;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class ZipArchiveService extends AbstractArchive {

	private Path archive = Paths.get("src/main/resources/zip/compress/archive.zip");
	private Path decompressFolder = Paths.get("src/main/resources/zip/decompress/");

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

//	private Path theNany = Paths.get("/home/Hesus/Videos/New folder/the.nanny.s02e01.dvdrip.xvid-wat.avi");

	public void init() {
		entries.add(new ZipArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new ZipArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new ZipArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));
//		entries.add(new ZipArchiveEntry(theNany.toFile(), "the.nanny.s02e01.dvdrip.xvid-wat.avi"));

		ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME);
//		zipArchiveEntry.setLastModifiedTime()
		files.add(ABC_FILE);
		files.add(DEFG_FILE);
		files.add(HIJKLMNOP_FILE);
//		files.add(theNany);

		archive = Paths.get("src/main/resources/extra.zip");
		decompressFolder = Paths.get("src/main/resources/_decompress/");
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
		ZipArchiveService zipArchiveService = new ZipArchiveService();
		zipArchiveService.init();
//		compress
/*		zipArchiveService.compress(zipArchiveService.getFiles(),
				zipArchiveService.getEntries(),
				zipArchiveService.getArchiveOutputStream(ARCHIVER_ZIP, zipArchiveService.getArchive()));*/
//      decompress
		zipArchiveService.decompress(
				zipArchiveService.getArchiveInputStream(ARCHIVER_ZIP, zipArchiveService.getArchive()),
				zipArchiveService.getDecompressFolder());
//		destroy
		zipArchiveService.destroy();
	}

	/*@Override
	public void compress(List<Path> files, List<ArchiveEntry> entries, ArchiveOutputStream archiveOutputStream) throws IOException {
		ZipArchiveOutputStream zipArchiveOutputStream = (ZipArchiveOutputStream) archiveOutputStream;
		zipArchiveOutputStream.setLevel(0);
		for (int index = 0; index < entries.size(); index++) {
			archiveOutputStream.putArchiveEntry(entries.get(index));
			archiveOutputStream.write(Files.readAllBytes(files.get(index)));
			archiveOutputStream.closeArchiveEntry();
		}
		archiveOutputStream.close();
	}*/

}
