package service.compressor;

import model.AbstractCompressor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

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

public class GzipCompressorService extends AbstractCompressor {

	private Path fileToCompress = HIJKLMNOP_FILE;
	private Path compression = Paths.get("src/main/resources/gz/compress/", HIJKLMNOP_FILE_NAME + ".gz");
	private Path fileToDeCompress = Paths.get("src/main/resources/gz/decompress/", HIJKLMNOP_FILE_NAME);

	private List<ArchiveEntry> entries = new LinkedList<>();
	private List<Path> files = new LinkedList<>();

	private static BufferedInputStream bufferedInputStream;
	private static BufferedOutputStream bufferedOutputStream;

	public void init() {
/*		entries.add(new ZipArchiveEntry(ABC_FILE.toFile(), ABC_FILE_NAME));
		entries.add(new ZipArchiveEntry(DEFG_FILE.toFile(), DEFG_FILE_NAME));
		entries.add(new ZipArchiveEntry(HIJKLMNOP_FILE.toFile(), HIJKLMNOP_FILE_NAME));

		files.add(ABC_FILE);
		files.add(DEFG_FILE);
		files.add(HIJKLMNOP_FILE);*/
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

	public Path getCompression() {
		return compression;
	}

	public Path getFileToDeCompress() {
		return fileToDeCompress;
	}

	public Path getFileToCompress() {
		return fileToCompress;
	}

	public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
//      init
		GzipCompressorService gzipCompressorService = new GzipCompressorService();
		gzipCompressorService.init();
//		compress
		bufferedInputStream = new BufferedInputStream(
				Files.newInputStream(gzipCompressorService.getFileToCompress()));
		bufferedOutputStream = new BufferedOutputStream(
				Files.newOutputStream(gzipCompressorService.getCompression(),
						StandardOpenOption.CREATE, StandardOpenOption.APPEND));

/*		GzipParameters gzipParameters = new GzipParameters();
		gzipParameters.setCompressionLevel(9);
		gzipParameters.setFilename(HIJKLMNOP_FILE_NAME);
		gzipParameters.setModificationTime(Files.getLastModifiedTime(HIJKLMNOP_FILE).toMillis());*/
		GzipCompressorOutputStream gzipCompressorOutputStream = new GzipCompressorOutputStream(bufferedOutputStream);

		gzipCompressorService.compress(bufferedInputStream, gzipCompressorOutputStream);
//      decompress
		bufferedOutputStream = new BufferedOutputStream(
				Files.newOutputStream(gzipCompressorService.getFileToDeCompress(), StandardOpenOption.CREATE, StandardOpenOption.APPEND));
		CompressorInputStream input = gzipCompressorService.getCompressorInputStream(COMPRESSOR_GZIP, gzipCompressorService.getCompression());

		gzipCompressorService.decompress(input, bufferedOutputStream);
//		destroy
		gzipCompressorService.destroy();
	}

}
