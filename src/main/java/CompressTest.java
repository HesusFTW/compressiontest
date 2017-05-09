import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CompressTest {
	public static void main(String[] args) throws IOException, ArchiveException, CompressorException {
//        To create a compressor writing to a given output by using the algorithm name:

//        /home/Hesus/Work/workspace_intellij/compressiontest/src/main/resources/abc.txt
		System.out.println(Paths.get("").toAbsolutePath().toString());
/*        BufferedInputStream bufferedInputStream = new BufferedInputStream(
				Files.newInputStream(Paths.get("src/main/resources/abc.txt")));
        */
		if (true) {
			Files.createDirectory(Paths.get("src/main/resources/howAboutThat/"));
		}
		BufferedOutputStream myOutputStream = new BufferedOutputStream(
				Files.newOutputStream(Paths.get("src/main/resources/howAboutThat/newFile.txt"),
						StandardOpenOption.CREATE, StandardOpenOption.APPEND));

		myOutputStream.write(3);
		myOutputStream.close();

		CompressorStreamFactory compressorStreamFactory = new CompressorStreamFactory();
		System.out.println("Compressor input:");
		compressorStreamFactory.getInputStreamCompressorNames().forEach(System.out::println);
		System.out.println();
		System.out.println("Compressor output:");
		compressorStreamFactory.getOutputStreamCompressorNames().forEach(System.out::println);

		System.out.println();
		ArchiveStreamFactory archiveStreamFactory = new ArchiveStreamFactory();
		System.out.println("Archiver input:");
		archiveStreamFactory.getInputStreamArchiveNames().forEach(System.out::println);
		System.out.println();
		System.out.println("Archiver output:");
		archiveStreamFactory.getOutputStreamArchiveNames().forEach(System.out::println);
        /*CompressorOutputStream gzippedOut = new CompressorStreamFactory()
                .createCompressorOutputStream(CompressorStreamFactory.GZIP, myOutputStream);


//        Make the factory guess the input format for a given archiver stream:

        ArchiveInputStream input = new ArchiveStreamFactory()
                .createArchiveInputStream(bufferedInputStream);

//        Make the factory guess the input format for a given compressor stream:

        CompressorInputStream input2 = new CompressorStreamFactory()
                .createCompressorInputStream(bufferedInputStream);*/

	}
}
