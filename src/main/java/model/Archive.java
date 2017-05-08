package model;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface Archive {
	void compress(List<Path> files, List<ArchiveEntry> entries, ArchiveOutputStream archiveOutputStream)
			throws IOException;

	List<Path> decompress(ArchiveInputStream archiveInputStream, Path decompressFolder)
			throws IOException, ArchiveException;

	ArchiveInputStream getArchiveInputStream() throws IOException, ArchiveException;

	ArchiveOutputStream getArchiveOutputStream() throws IOException, ArchiveException;
}
