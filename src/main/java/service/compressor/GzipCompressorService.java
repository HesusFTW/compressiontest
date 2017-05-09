package service.compressor;

import model.AbstractCompressor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static util.Constants.*;

public class GzipCompressorService extends AbstractCompressor {

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
		BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(HIJKLMNOP_FILE));
		CompressorOutputStream compressorOutputStream = gzipCompressorService.getCompressorOutputStream(COMPRESSOR_GZIP,
				gzipCompressorService.getArchive());
		gzipCompressorService.compress(bufferedInputStream, compressorOutputStream);
//      decompress
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(HIJKLMNOP_FILE));
		CompressorInputStream input = new CompressorStreamFactory()
				.createCompressorInputStream(COMPRESSOR_GZIP,
						new BufferedInputStream(Files.newInputStream(gzipCompressorService.getArchive())));
		gzipCompressorService.decompress(input, bufferedOutputStream);
//		destroy
		gzipCompressorService.destroy();
	}

}
