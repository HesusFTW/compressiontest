package service.compressor;

import model.AbstractCompressor;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.*;

import static util.Constants.HIJKLMNOP_FILE;
import static util.Constants.HIJKLMNOP_FILE_NAME;

public class BzipTwoCompressorService extends AbstractCompressor {

	private static Path fileToCompress = HIJKLMNOP_FILE;
	private static Path compressedFile = Paths.get("src/main/resources/bz2/compress/", HIJKLMNOP_FILE_NAME + ".bz2");
	private static Path fileToDeCompress = Paths.get("src/main/resources/bz2/decompress/", HIJKLMNOP_FILE_NAME);

	private static BufferedInputStream bufferedInputStream;
	private static BufferedOutputStream bufferedOutputStream;

	public void init() {
	}

	public void destroy() {
	}

	public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
//		compress
		bufferedInputStream = new BufferedInputStream(Files.newInputStream(fileToCompress));
		bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(compressedFile,
				StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		BZip2CompressorOutputStream bZip2CompressorOutputStream = new BZip2CompressorOutputStream(bufferedOutputStream, 3);

		BzipTwoCompressorService bzipTwoCompressorService = new BzipTwoCompressorService();
		bzipTwoCompressorService.compress(bufferedInputStream, bZip2CompressorOutputStream);

//		decompress
		bufferedInputStream = new BufferedInputStream(Files.newInputStream(compressedFile));
		bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(fileToDeCompress,
				StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		BZip2CompressorInputStream bZip2CompressorInputStream = new BZip2CompressorInputStream(bufferedInputStream);
		bzipTwoCompressorService.decompress(bZip2CompressorInputStream, bufferedOutputStream);
	}

}
