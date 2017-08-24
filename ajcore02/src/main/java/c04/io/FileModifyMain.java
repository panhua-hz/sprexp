package c04.io;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class FileModifyMain {

	
	public static void main(String[] args) throws IOException {
		String NEW_LINE = System.lineSeparator();
		Path root = Paths.get("D:\\temp\\java8code");
		try(Stream<Path> subPaths = Files.walk(root)){
			Stream<Path> javaFiles = subPaths.filter(p->Files.isRegularFile(p)&&p.toString().endsWith(".java"));
			javaFiles.forEach(p->{
				String tname = Thread.currentThread().getName();
				Path pd = p.getParent();
				Path pdr = root.relativize(pd);
				String pkg = pdr.toString().replace("\\", ".");
				String fileName = p.getFileName().toString();
				System.out.println(tname+" start to handle "+pkg+"."+fileName);
				String header = "package "+pkg+";"+NEW_LINE;
				
				try {
					Path tmpFile = Files.createTempFile(null, null);
					Files.write(tmpFile, header.getBytes(StandardCharsets.UTF_8));
					byte[] content = Files.readAllBytes(p);
					Files.write(tmpFile, content, StandardOpenOption.APPEND);
					Files.move(tmpFile, p, StandardCopyOption.REPLACE_EXISTING);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(tname+" end of handle "+pkg+"."+fileName);
			});
		}

	}

}
