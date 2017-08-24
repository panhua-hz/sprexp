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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileLockMain2 {
	
	public static final String NEW_LINE = System.lineSeparator();
	
	public static void writeToFile(Path p, String message) throws IOException{
		String msg = message + NEW_LINE;
		Files.write(p, msg.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
		System.out.println("Has write "+message+" to file.");
	}
	
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
	
	public static void writeWithLock(FileChannel fc, String message) throws IOException{
		String tName = Thread.currentThread().getName();
		try(FileLock fl = fc.lock()){
			System.out.println(tName + ": have get key");
			byte[] msgByte = message.getBytes(StandardCharsets.UTF_8);
			ByteBuffer bb = ByteBuffer.wrap(msgByte);
			fc.write(bb);
		}
	}
	public static void writeTask(String mark, FileChannel fc) throws IOException, InterruptedException{
		String tName = Thread.currentThread().getName();
		String msgHeader = tName+"~"+mark+": ";
		DateTimeFormatter dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		
		Instant start = Instant.now();
		LocalDateTime startLdt = LocalDateTime.now();
		String startStr = dtm.withLocale(Locale.ENGLISH).format(startLdt);
		String msg = tName+": "+mark+" start at "+startStr+NEW_LINE;
		writeWithLock(fc, msg);
		Thread.sleep(3000l);
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		long dur = timeElapsed.toMillis();
		LocalDateTime endLdt = LocalDateTime.now();
		String endStr = dtm.withLocale(Locale.ENGLISH).format(endLdt);
		msg = tName+": "+mark+" end at "+endStr+" cost "+dur+"ms."+NEW_LINE;
		writeWithLock(fc, msg);
	}
	public static void writeTask2(String mark, FileChannel fc) throws IOException, InterruptedException{
		String tName = Thread.currentThread().getName();
		String msgHeader = tName+"~"+mark+": ";
		DateTimeFormatter dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		try(FileLock fl = fc.lock()){ //FileLock真的不太好用.不会用阿
			System.out.println(msgHeader+": have get key");
			Instant start = Instant.now();
			LocalDateTime startLdt = LocalDateTime.now();
			String startStr = dtm.withLocale(Locale.ENGLISH).format(startLdt);
			//writeToFile(file, tName+": "+mark+" start at "+startStr);
			String msg = tName+": "+mark+" start at "+startStr+NEW_LINE;
			byte[] msgByte = msg.getBytes(StandardCharsets.UTF_8);
			ByteBuffer bb = ByteBuffer.wrap(msgByte);
			fc.write(bb);
			Thread.sleep(3000l);
			Instant end = Instant.now();
			Duration timeElapsed = Duration.between(start, end);
			long dur = timeElapsed.toMillis();
			LocalDateTime endLdt = LocalDateTime.now();
			String endStr = dtm.withLocale(Locale.ENGLISH).format(endLdt);
			//writeToFile(file, tName+": "+mark+" end at "+endStr+" cost "+dur+"ms.");
			msg = tName+": "+mark+" end at "+endStr+" cost "+dur+"ms."+NEW_LINE;
			msgByte = msg.getBytes(StandardCharsets.UTF_8);
			bb = ByteBuffer.wrap(msgByte);
			fc.write(bb);
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		Path file = Paths.get("tmp","lc","log.txt");
		createFile(file);
		FileChannel fc = FileChannel.open(file, StandardOpenOption.WRITE);
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++){
			int k = i;
			Runnable r1 = ()->{try {
				writeTask(""+k, fc);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}};
			//Thread.sleep(500);
			es.execute(r1);
		}
		es.shutdown();
	}

}
