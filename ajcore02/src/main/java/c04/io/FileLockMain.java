package c04.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileLockMain {
	public static final String NEW_LINE = System.lineSeparator();
	public static void createFile(Path p) throws IOException{
		Path newFile = p;
		if (Files.notExists(p)){
			System.out.println("No such file "+p);
			Path pdir = p.getParent();
			if (Files.notExists(pdir)){
				Files.createDirectories(pdir);
				System.out.println("Created dir "+pdir);
			}
			newFile = Files.createFile(p);
			System.out.println("Created file "+newFile);
		}
	}
	public static void writeFile(Path file, int index) throws IOException{
		String tName = Thread.currentThread().getName();
		int lineLength = 50;
		/*设是这样的:
		 * 1. 通过FileChannel打开文件;
		 * 2. 通过FileLock锁定一块区域;
		 * 3. 通过ByteBuffer内存文件的api写数据到文件.
		 * 
		 * 有这些问题以待解决:
		 * 1. FileLock会阻塞线程?没有实现这个,每次都是Exception;
		 * 2. 通过ByteBuffer写入,无法凭空写入到一个位置,要么是顺序写入,要么文件已经这么大,覆盖该位置.//通过main中的sleep控制到底是顺序还是随机写入.
		 * 
		 * 下面这个代码到底有什么意义?lock既然不能阻塞就只能程序按片控制,既然如此,何必lock?
		 * 
		 * */
		
		
		try (FileChannel fc = FileChannel.open(file, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
			StringBuffer sb = new StringBuffer();
			for (int i = index; i < (index+lineLength-2); i++){
				sb.append(i%10);
			}
			sb.append(NEW_LINE);
			try(FileLock fl = fc.lock((index+1)*lineLength, lineLength, false)){
				System.out.println(tName+"Have get key.");
				ByteBuffer buff = fc.map(FileChannel.MapMode.READ_WRITE,
						(index+1)*lineLength, lineLength);
				buff.put(sb.toString().getBytes(StandardCharsets.UTF_8));
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		Path file = Paths.get("tmp", "lc", "log.txt");
		createFile(file);
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 28; i++) {
			int k = i;
			Runnable r1 = () -> {
				try {
					writeFile(file, k);
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			//Thread.sleep(500);
			es.execute(r1);
		}
		es.shutdown();
	}
}
