package c05.concurrency;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class Thread4AsyncTest {
	/*
	 * CompletableFuture
	 * 跟stream很像
	 * 使用装饰模式迭代:
	 * CompletableFuture.supplyAsync
	 * CompletableFuture.completedFuture(t)
	 * thenApplyAsync
	 * thenAccept
	 */
	
	
	
	/*
	 * try: http://horstmann.com/
	 */
	@Test
	public void completeFutureTest(){
		String hrefPattern = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";  //得到子url
	      // CompletableFuture<String> getURL = CompletableFuture.supplyAsync(() -> Util.getInput("URL"));
	      
	      // Make a function mapping URL to CompletableFuture
	      CompletableFuture<String> f = Util.repeat(() -> Util.getInput("URL"), s -> s.startsWith("http://")) //直到读取到http://开头的输入
	    		  .thenApplyAsync((String url) -> Util.getPage(url)); //解析上面url所对应网页的内容
	      CompletableFuture<List<String>> links = f.thenApply(c -> Util.matches(c, hrefPattern)); //寻找到所有链接: 匹配<a herf ...中的元素
	      links.thenAccept(System.out::println); //打印出所有子链接
	      ForkJoinPool.commonPool().awaitQuiescence(50,  TimeUnit.SECONDS); //CompletableFuture.supplyAsync会调用forkjoin线程池
	      System.out.println("exiting");
	}
}

class Util {
	public static String getPage(String urlString) {
		try {
			Scanner in = new Scanner(new URL(urlString).openStream());
			StringBuilder builder = new StringBuilder();
			while (in.hasNextLine()) {
				builder.append(in.nextLine());
				builder.append("\n");
			}
			return builder.toString();
		} catch (IOException ex) {
			RuntimeException rex = new RuntimeException();
			rex.initCause(ex);
			throw rex;
		}
	}

	public static List<String> matches(String input, String patternString) {
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		List<String> result = new ArrayList<>();

		while (matcher.find())
			result.add(matcher.group(1));
		return result;
	}

	public static Scanner in = new Scanner(System.in);

	public static String getInput(String prompt) {
		System.out.print(prompt + ": ");
		return in.nextLine();
	}

	/*
	 * 通过CompletableFuture.supplyAsync初始化了一个CompletableFuture.
	 * 递归调用action得到返回值T,直到T符合util则返回包含T的CompletableFuture<T>
	 * 如何开始一个可完成Future调用： CompletableFuture.supplyAsync(action)
	 * 如何构造一个可完成Future返回值: CompletableFuture.completedFuture(t)
	 */
	public static <T> CompletableFuture<T> repeat(Supplier<T> action, Predicate<T> until) {
		return CompletableFuture.supplyAsync(action).thenComposeAsync((T t) -> {
			return until.test(t) ? CompletableFuture.completedFuture(t) : repeat(action, until);
		});
	}

}