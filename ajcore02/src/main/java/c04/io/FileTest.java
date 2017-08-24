package c04.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.stream.Stream;

import org.junit.Test;

public class FileTest {

	@Test
	public void getPathTest() {
		// D:/myproject/stsgrd/sprexp/ajcore02/src/main/java/c04/io/FileTest.java

		// 1. 通过Paths.get组合得到Path:
		Path baseDirR = Paths.get("");
		Path baseDir = baseDirR.toAbsolutePath();
		System.out.println("BaseDir: " + baseDir);
		// 如果有特殊字符如*会抛InvalidPathException
		Path rp = Paths.get("src", "main", "java", "c04", "io");
		System.out.println("RelatedDir: " + rp);

		// 2. Path to Path
		// resolve: 解析相对路径
		// resolveSibling: 兄弟路径
		// relativize: 相对路径与resolve相反
		// normalize: 去除冗余路径.或者..
		// getParent: 父路径
		// getFileName：文件路径的最后一个元素即文件名
		// getRoot: 根路径,相对路径则返回null
		// getName(0): 路径字符串的第一个元素.
		// subpath(1, p.getNameCount()): 路径字符串子串
		// Iterator: Path可以迭代,应该是每个路径字符串
	}

	/*
	 * 1. 创建文件夹必须一步一步,不能一步到位. 
	 * 2. 创建空文件(Files.createFile(path)),如果文件已经存在,Exception 
	 * 3. 检查文件是否存在(Files.exists(path))和创建文件都是“原子操作”; 如果文件不存在则会在其他人创建文件之前创建该文件.
	 */
	@Test
	public void createDirFileTest() throws IOException {
		// 要保证路径下的文件夹自动创建调用Files.createDirectories
		// Files.createDirectory只能创建一个文件夹.
		Path lcdir = Paths.get("tmp", "da", "db");

		if (Files.notExists(lcdir)) {
			lcdir = Files.createDirectories(lcdir);
			System.out.println("Created " + lcdir);
		}

		// 创建空文件(Files.createFile(path)),如果文件已经存在,Exception
		Path hellofp = lcdir.resolve(Paths.get("hello.txt"));
		// Files.notExists/Files.exists没法保证后续的一致性.所以有可能在多线程/进程下别使用这个方法
		if (Files.notExists(hellofp)) { // 多线程不靠谱
			// 注意createFile包含两步骤,两步骤是原子操作:
			// 1. 文件不存在;
			// 2. 创建文件
			hellofp = Files.createFile(hellofp);
			System.out.println("Luckly! created file " + hellofp);
		}
		// 写文件：如果没有该文件则创建文件,写文件,关闭文件
		Files.write(hellofp, "我们都是javaer;\n".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		// copy可以拷贝文件,或者拷贝到流
		Path hellofp2 = hellofp.resolveSibling("hello2.txt");
		Files.copy(hellofp, System.out);
		Files.copy(hellofp, hellofp2, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
		// move file
		Path hellofp3 = hellofp.resolveSibling("hello3.txt");
		Files.move(hellofp, hellofp3, StandardCopyOption.ATOMIC_MOVE);// 原子操作
		// delete不能删除非空目录
		Files.delete(hellofp3); // Files.deleteIfExists(hellofp3);

	}

	@Test
	public void createTmpDirFileTest() throws IOException {

		Path tempPath = Files.createTempDirectory("corejava");

		Path workDir = Files.createDirectory(tempPath.resolve("work"));
		System.out.println(workDir);

		Path tempFile = Files.createTempFile(workDir, "test", ".txt");
		System.out.println(tempFile);
		Files.write(tempFile, "Hello".getBytes(StandardCharsets.UTF_8));
		Files.copy(tempFile, workDir.resolve("hello.txt"));

		Path target2 = workDir.resolve("hello2.txt");
		Files.move(tempFile, target2, StandardCopyOption.ATOMIC_MOVE);
		boolean deleted = Files.deleteIfExists(target2);
		if (deleted)
			System.out.println("Deleted " + target2);
	}

	@Test
	public void directoryTest() throws IOException {
		// 列出子目录list:
		Path javahome = Paths.get(System.getProperty("java.home"));
		System.out.printf("Directories inside %s:\n", javahome);
		try (Stream<Path> entries = Files.list(javahome)) {
			entries.forEach(System.out::println);
		}
		// 遍历子目录及子子目录使用walk
		System.out.println("----walk doc-----------------");
		Path lcdir = Paths.get("tmp", "da");
		try (Stream<Path> entries = Files.walk(lcdir)) {
			entries.forEach(System.out::println);
		}
		// 查找文件
		System.out.println("----find doc-----------------");
		lcdir = Paths.get("tmp");
		try (Stream<Path> find = Files.find(lcdir, 10, (f, a) -> {
			// System.out.println("-->"+f.getFileName());
			return f.getFileName().toString().startsWith("alice") && !a.isDirectory();
		})) {
			find.forEach(System.out::println);
		}
	}

	@Test
	public void dirCopyTest() throws IOException {
		// 可以通过walk边遍历边创建.
		Path from = Paths.get("tmp", "da");
		Path to = from.resolveSibling("da1");
		Files.walk(from).forEach(p -> {
			try {
				// 先要解析相应的地址q
				Path q = to.resolve(from.relativize(p));
				if (Files.isDirectory(p)) {
					System.out.printf("Creating %s\n", q);
					Files.createDirectory(q);
				} else {
					System.out.printf("Copying %s to %s\n", p, q);
					Files.copy(p, q);
				}
			} catch (IOException ex) {
				throw new UncheckedIOException(ex);
			}
		});
	}

	@Test
	public void dirDelTest() throws IOException {
		// 删除目录,由于只能删除空目录,而walk是先父目录再子目录,操作不方便
		// 使用walkFileTree, 然而它需要一个FileVisitor来定义通知时策略
		// 时机选择读取文件时以及访问目录后,必须先删除文件再删除文件夹
		Path from = Paths.get("tmp", "da1");
		Files.walkFileTree(from, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				System.out.printf("Deleting %s\n", file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				// 如果有Exception,抛出异常
				if (exc != null)
					throw exc;
				Files.delete(dir);
				System.out.printf("Removing %s\n", dir);
				return FileVisitResult.CONTINUE;
			}

		});
	}

	@Test
	public void zipCreate() throws URISyntaxException, IOException {
		Path root = Paths.get("tmp");
		Path src = root.resolve("da");
		Path zip = root.resolve("da.zip");

		if (Files.exists(zip)) {
			Files.delete(zip);
			System.out.println("To delete old zip file.");
		}

		// Constructs the URI jar:file://da.zip
		URI uri = new URI("jar", zip.toUri().toString(), null);
		try (FileSystem zipfs = FileSystems.newFileSystem(uri, Collections.singletonMap("create", "true"))) {
			Path zipd = zipfs.getPath("/");
			Files.walk(src).forEach(p -> {
				// Ignore self folder:
				if (p.equals(src)) { // 可以用equals比较两个path
					return; // 注意这里return而不是break
				}

				System.out.println(src.relativize(p));
				try {
					Files.copy(p, zipd.resolve(src.relativize(p).toString()));
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
		}
	}

	@Test
	public void zipWalkTest() throws IOException {
		Path root = Paths.get("tmp");
		Path zip = root.resolve("da.zip");
		// 对比下创建的zip的不同,这里不需要uri
		try (FileSystem zipfs = FileSystems.newFileSystem(zip,null)) {
			Path zipd = zipfs.getPath("/");
			Files.walk(zipd).forEach(p -> {
				
				if (Files.isDirectory(p)){
					System.out.println("Dir: "+p.toString());
				}else{
					System.out.println("File: "+p.toString());
					String fileName = p.getFileName().toString();
					try {
						// 把所有文件解到tmp目录下
						Files.copy(p, root.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	@Test
	public void bigFileTest() throws IOException{
		//内存映射文件可以用来随机存取大文件: ByteBuffer
		//文件锁用来分片锁定文件
		Path bigFilePath = Paths.get("tmp", "big.bmp");
		//Path bigFilePath = Paths.get("tmp", "chart.bmp");
		try (FileChannel channel = FileChannel.open(bigFilePath,
                StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,
                    0, channel.size());
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            //对于bmp的文件格式需要搜索下
            int size = buffer.getInt(2);
            int headerSize = buffer.getInt(14);
            int width = buffer.getInt(18);
            int height = buffer.getInt(22);
            short planes = buffer.getShort(26);
            short depth = buffer.getShort(28);
            int compressionMode = buffer.getInt(30);
            System.out.println("Size: " + size);
            System.out.println("Header size : " + headerSize);
            System.out.println("Width : " + width);
            System.out.println("Height : " + height);
            System.out.println("Planes : " + planes);
            System.out.println("Depth : " + depth);
            System.out.println("Compression mode : " + compressionMode);
            
        }
	}

}
