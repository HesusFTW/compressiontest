import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import service.archiver.ArArchiveService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static util.Constants.ARCHIVER_AR;

public class ArArchiveTest {

	private ArArchiveService arArchiveService;

	@BeforeClass
	private void setUp() {
		arArchiveService = new ArArchiveService();
		arArchiveService.init();
		try {
			Files.deleteIfExists(arArchiveService.getArchive());
			for (ArchiveEntry file : arArchiveService.getEntries()) {
				Files.deleteIfExists(
						Paths.get(arArchiveService.getDecompressFolder().toAbsolutePath().toString(), file.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	private void arTest() {
		try {
			arArchiveService.compress(arArchiveService.getFiles(),
					arArchiveService.getEntries(),
					arArchiveService.getArchiveOutputStream(ARCHIVER_AR, arArchiveService.getArchive()));
			Assert.assertTrue(Files.exists(arArchiveService.getArchive()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}

	@Test
	private void unArTest() {
		try {
			List<Path> originalFiles = arArchiveService.getFiles();
			List<Path> resultingFiles = arArchiveService.decompress(
					arArchiveService.getArchiveInputStream(ARCHIVER_AR, arArchiveService.getArchive()),
					arArchiveService.getDecompressFolder());
			for (int index = 0; index < originalFiles.size(); index++) {
				Assert.assertEquals(
						Files.readAllBytes(originalFiles.get(index)),
						Files.readAllBytes(resultingFiles.get(index)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArchiveException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	private void tearDown() {
		arArchiveService.destroy();
	}
}
