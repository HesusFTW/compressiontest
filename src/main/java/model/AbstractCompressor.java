package model;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class AbstractCompressor {

	public void compress(BufferedInputStream bufferedInputStream, CompressorOutputStream bufferedOutputStream) throws IOException {
		writeInputToOutputStream(bufferedInputStream, bufferedOutputStream);
	}

	public void decompress(CompressorInputStream bufferedInputStream, BufferedOutputStream bufferedOutputStream) throws IOException {
		writeInputToOutputStream(bufferedInputStream, bufferedOutputStream);
	}

	private void writeInputToOutputStream(InputStream bufferedInputStream, OutputStream bufferedOutputStream) throws IOException {
		byte[] buffer = new byte[8192];
		int len;
		while ((len = bufferedInputStream.read(buffer)) != -1) {
			bufferedOutputStream.write(buffer, 0, len);
		}
		bufferedInputStream.close();
		bufferedOutputStream.close();
	}

	public CompressorInputStream getCompressorInputStream(String compressorName, Path archive) throws IOException, CompressorException {
		return new CompressorStreamFactory()
				.createCompressorInputStream(compressorName, new BufferedInputStream(
						Files.newInputStream(archive)));
	}

	public CompressorOutputStream getCompressorOutputStream(String compressorName, Path archive) throws IOException, CompressorException {
		return new CompressorStreamFactory().createCompressorOutputStream(compressorName, new BufferedOutputStream(
				Files.newOutputStream(archive, StandardOpenOption.CREATE, StandardOpenOption.APPEND)));
	}
}
