package c05.concurrency;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class ParallelSearchFile {
	// 得到子目录
	public static Set<Path> descendants(Path p) throws IOException {
		try (Stream<Path> sub = Files.walk(p)) {
			// 收集器终止操作.
			Set<Path> paths = sub.filter(f -> !Files.isDirectory(f) && f.getFileName().toString().endsWith(".txt"))
					.collect(Collectors.toSet());
			return paths;
		}
	}

	// 在文件中查找单词出现的次数
	public static long occurrences(String word, Path path) {
		String content;
		try {
			// Files.readAllBytes(path)适合读取不太大的文件,否则用filechannel/ByteBuffer
			content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			// splitAsStream->Predicate.isEqual
			return Pattern.compile("\\PL+").splitAsStream(content).filter(Predicate.isEqual(word)).count();
		} catch (IOException e) {
			return 0;
		}
	}

	@Test
	public void callableTest() throws IOException, InterruptedException, ExecutionException {
		String word = "English";
		Path root = Paths.get("tmp");
		Set<Path> subFiles = descendants(root);
		ExecutorService pool = Executors.newCachedThreadPool();
		// 用map返回函数列表有点麻烦啊.--函数接口再返回一个函数接口不好写
		// List<Callable<Long>> tasks = subFiles.parallelStream().map(p->
		// {return }});
		List<Callable<Long>> tasks = new ArrayList<>();
		subFiles.forEach(p -> {
			tasks.add(() -> {
				Thread.sleep(3000l); // 拖延时间//Callable不用catch exception.
				return occurrences(word, p);
			});
		});
		List<Future<Long>> results = pool.invokeAll(tasks);// 使用invokeAll批量调用task
		long total = 0;
		// 为何用for而不用.foreach是因为闭包
		for (Future<Long> r : results) {
			total += r.get(); // 这个调用会阻塞junit线程结束
		}
		System.out.println("Occurrences of String: " + total);
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS); // 让junit等待线程池关闭.
	}
}
