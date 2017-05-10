package service.compressor;

import model.AbstractCompressor;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static util.Constants.HIJKLMNOP_FILE;
import static util.Constants.HIJKLMNOP_FILE_NAME;

public class LzmaCompressorService extends AbstractCompressor {

	private static Path fileToCompress = HIJKLMNOP_FILE;
	private static Path compressedFile = Paths.get("src/main/resources/lzma/compress/", HIJKLMNOP_FILE_NAME + ".lzma");
	private static Path fileToDeCompress = Paths.get("src/main/resources/lzma/decompress/", HIJKLMNOP_FILE_NAME);

	private static BufferedInputStream bufferedInputStream;
	private static BufferedOutputStream bufferedOutputStream;

	public void init() {
	}

	public void destroy() {
	}

	public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
		LzmaCompressorService lzmaCompressorService = new LzmaCompressorService();

//		compress
		bufferedInputStream = new BufferedInputStream(Files.newInputStream(fileToCompress));
		bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(compressedFile,
				StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		LZMACompressorOutputStream lzmaCompressorOutputStream = new LZMACompressorOutputStream(bufferedOutputStream);
		lzmaCompressorService.compress(bufferedInputStream, lzmaCompressorOutputStream);

//		decompress
		bufferedInputStream = new BufferedInputStream(Files.newInputStream(compressedFile));
		bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(fileToDeCompress,
				StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		LZMACompressorInputStream lzmaCompressorInputStream = new LZMACompressorInputStream(bufferedInputStream);
		lzmaCompressorService.decompress(lzmaCompressorInputStream, bufferedOutputStream);
	}

}
