package service.compressor;

import model.AbstractArchive;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class GzipCompressorService extends AbstractArchive {

	private Path archive = Paths.get("src/main/resources/gz/compress/archive.gz");
	private Path decompressFolder = Paths.get("src/main/resources/gz/decompress/");

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

	public void init() {
		entries.add(new ZipArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new ZipArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new ZipArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));

		ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME);
//		zipArchiveEntry.setLastModifiedTime()
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
		GzipCompressorService gzipCompressorService = new GzipCompressorService();
		gzipCompressorService.init();
//		compress
		gzipCompressorService.compress(gzipCompressorService.getFiles(),
				gzipCompressorService.getEntries(),
				gzipCompressorService.getArchiveOutputStream(COMPRESSOR_GZIP, gzipCompressorService.getArchive()));
//      decompress
		CompressorInputStream input = new CompressorStreamFactory()
				.createCompressorInputStream(COMPRESSOR_GZIP,
						new BufferedInputStream(Files.newInputStream(gzipCompressorService.getArchive())));
//				.createCompressorInputStream(originalInput);
		gzipCompressorService.decompress(
				gzipCompressorService.getArchiveInputStream(COMPRESSOR_GZIP, gzipCompressorService.getArchive()),
				gzipCompressorService.getDecompressFolder());
//		destroy
		gzipCompressorService.destroy();
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

	private void decompress(CompressorInputStream input, Path outputFile) throws IOException {
		BufferedOutputStream outputStream = new BufferedOutputStream(
				Files.newOutputStream(outputFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND));
		byte[] buffer = new byte[8192];
		int len;
		while ((len = input.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		input.close();
		/*ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) entry;
		BasicFileAttributeView attributes = Files.getFileAttributeView(
				outputFile, BasicFileAttributeView.class);
		attributes.setTimes(zipArchiveEntry.getLastModifiedTime(),
				zipArchiveEntry.getLastAccessTime(), zipArchiveEntry.getCreationTime());
		decompressedFiles.add(outputFile);*/
		System.out.println("Created file: " + outputFile.toAbsolutePath().toString());
	}

}
