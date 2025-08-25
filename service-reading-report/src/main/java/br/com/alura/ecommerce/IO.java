package br.com.alura.ecommerce;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IO {

	public static void copyTo(Path source, File target) throws IOException {
		target.getParentFile().mkdirs();
		Files.copy(source, target.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
	}

	public static void append(File target, String content) throws IOException {
		Files.write(target.toPath(), content.getBytes(), java.nio.file.StandardOpenOption.APPEND);
	}

}
