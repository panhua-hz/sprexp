package c03.stream;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamTest {
	// 1. 创建操作
	@Test
	public void createStream() {
		// 0. Get by Array/Collection/String etc.
		// 1. Empty stream
		Stream<String> s1 = Stream.empty();
		show("Empty Stream", s1);
		// 2. Stream of
		s1 = Stream.of("aa", "bb", "cc");
		show("OfStream", s1);
		// 3. infinite stream generate
		s1 = Stream.generate(() -> "repeat"); //
		show("generate infinite", s1);
		// 4. infinite iterate
		// NOTE: think about why use BigInteger instead of IntStream?
		Stream<BigInteger> ints1 = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));
		show("interate infinite", ints1);

		// 5. infinite generated double stream
		// NOTE: Math.random compare with class Random
		// It is good to use Double Stream instead of Stream<Double>
		DoubleStream ds = DoubleStream.generate(Math::random);
		String priStr = Arrays.toString(ds.limit(5).toArray());
		System.out.println("generate infinit primitive: " + priStr.substring(0, priStr.length() - 1) + ", ...]");

		// 6. Range/rangeClosed
		IntStream is = IntStream.range(1, 100);
		priStr = Arrays.toString(is.limit(10).toArray());
		System.out.println("generate infinit primitive: " + priStr.substring(0, priStr.length() - 1) + ", ...]");
	}

	// 2. 中间操作
	@Test
	public void streamOpTest() {
		String[] song = { "row", "row", "row", "your", "boat", "gently", "down", "the", "stream" };
		Stream<String> s1 = Stream.empty();
		/*
		 * NOTE:seems following operation cloud use parallel stream. Why?
		 * 都是中间操作,非终止操作.所以只是保存要并行操作的指令. 终止操作执行时到底并行的是哪些操作?
		 * peek看来是并行读取元素.然后对元素进行处理. 注意forkjoin的理解,分分合合.能分能合.
		 * 所以通过peek可见,先并行读取元素,然后处理distinct操作,再并行读取元素进行map操作. 操作都是由终止操作来触发
		 */
		// 1. filter/distinct/sort 全表搜索比较得到新流
		// 2. limit/skip/concat 得到一个同类型的新流,size有变化
		// 3. map and flatMap 可以转化为别的类型的流
		s1 = Stream.of(song).parallel().peek(this::peekItem1).distinct().peek(this::peekItem2).flatMap(this::letters);
		show("flatMap char", s1);

		// IntStream mapToObj 然后调用收集器
		String sentence = "\uD835\uDD46 is the set of octonions.";
		System.out.println(sentence);
		IntStream codes = sentence.codePoints();
		System.out.println(codes.mapToObj(c -> String.format("%X ", c)).collect(Collectors.joining()));
	}

	// 3. 终止操作: reduce/collect/foreach etc.
	@Test
	public void reduceEndingStreamOpTest() {
		String[] strlist = { "row", "row", "row", "your", "boy", "boatas", "gently", "down", "the", "stream" };
		// 1. simple reduce to a <Optional> result:
		Optional<String> op1 = Stream.of(strlist).parallel().max((a, b) -> a.length() - b.length());
		System.out.println("Longest word: " + op1.orElse("NULLWD"));
		/*
		 * 通过peek打印可知parallel是并行读取元素并进行处理. 对同一个元素用同一个线程处理，貌似是按流操作的顺序处理数据
		 * 比如对boy进行处理如下,先p1过滤之前的读取操作,后p2查找匹配操作:
		 * p1:ForkJoinPool.commonPool-worker-2: boy
		 * p1:ForkJoinPool.commonPool-worker-3: row
		 * p2:ForkJoinPool.commonPool-worker-2: boy 对比上面的forkjoin线程表现
		 * 
		 */
		op1 = Stream.of(strlist).parallel().peek(this::peekItem1).filter(s -> s != null && s.startsWith("b"))
				.peek(this::peekItem2).findFirst();
		System.out.println("first startwith b: " + op1.orElse("NULLWD"));
	}

	@Test
	public void reduceEndingStreamOpTest2() {
		String[] content = { "I", "love", "java", "and", "like", "running" };
		// 2. Reduce operation
		// -a. reduce1: 一个参数累加器
		Optional<String> op1 = Stream.of(content).parallel().reduce((s1, s2) -> s1 + " " + s2);
		System.out.println("String sentence1: " + op1.orElse("NULLWD"));

		// -b. 添加无害标识: 会合并到累加操作中，可以解决Optional的问题.
		// 为了不返回Optional用了一个标识,本来这个标识可以用来做默认值的, 但会跟元素做结合操作, 按道理应该选择 0 for +,""
		// for String, 1 for 乘积.
		// 但这里可以作为间隔字符,也算有奇效.
		String flagStr = Stream.of(content).parallel().reduce(" ", (s1, s2) -> s1 + s2);
		System.out.println("String sentence2: " + flagStr);

		// -c. 无害标识/累加器/合并器
		// 不是很好,用IntStream免装箱
		int charLeng = Stream.of(content).parallel().reduce(0, (total, word) -> total + word.length(),
				(t1, t2) -> t1 + t2);
		System.out.println("SentenceHaveChars1: " + charLeng);
		// 更好的实现:
		charLeng = Stream.of(content).mapToInt(String::length).sum();
		System.out.println("SentenceHaveChars2: " + charLeng);
		OptionalInt oi1 = Stream.of(content).mapToInt(String::length).max();
		System.out.println("longestWordsLength: " + oi1.orElse(0));
	}

	@Test
	public void foreachEndingStreamOpTest1() {
		String[] content = { "I", "love", "java", "and", "like", "running" };
		// foreach: 如果用parallel的话,会使用不同线程导致无序.
		Stream<String> s1 = Stream.of(content);
		s1.parallel().forEach(this::peekItem1);
		System.out.println("-----------------------------");
		// forEachOrdered: 有序,相当于放弃并行.虽然使用并行可能会通过多线程打印,但为了维持顺序，需要阻塞等待.
		s1 = Stream.of(content);
		s1.parallel().peek(this::peekItem1).forEachOrdered(this::peekItem2); // 这种要顺序最好不用并行.
		System.out.println("-----------------------------");

	}

	@Test
	public void collectArrayEndingStreamOpTest2() {
		String[] content = { "I", "love", "java", "and", "like", "running" };
		// toArray: 对于数组的写入可能是顺序的,否则如何处理闭包?
		Stream<String> s1 = Stream.of(content);
		String[] sar = s1.parallel().peek(this::peekItem1).toArray(String[]::new);
		System.out.println(Arrays.toString(sar));
		System.out.println("-----------------------------");
	}

	@Test
	public void badCollect() {
		String[] words = readAlice().split("\\PL+");
		// 线程不安全:
		// 1. shortWords[s.length()] 并没有更新可见性;
		// 2. shortWords[s.length()]++ 并非原子操作
		int[] shortWords = new int[10];
		Arrays.stream(words).parallel().forEach(s -> {
			if (s.length() < 10)
				shortWords[s.length()]++;
		});
		System.out.println(Arrays.toString(shortWords));

		// Try again--the result will likely be different (and also wrong)
		Arrays.fill(shortWords, 0);
		Arrays.stream(words).parallel().forEach(s -> {
			if (s.length() < 10)
				shortWords[s.length()]++;
		});
		System.out.println(Arrays.toString(shortWords));

		// 可能统计需要使用ConcurrentHashMap<String, LongAdder>
		// counts.computeIfAbsent(key, k->new LongAdder()).increment();

		// Remedy: Group and count
		Map<Integer, Long> shortWordCounts = Arrays.stream(words).parallel().filter(s -> s.length() < 10)
				.collect(groupingBy(String::length, counting()));

		System.out.println(shortWordCounts);
	}

	/*
	 * 如果并行流把结果收集到一个集合/map,但目标并不是线程安全的如ArrayList/HashMap etc. 就要使用collect方法。
	 * collect方法参数: 提供者: 能创建目标类型实例的方法.如构造方法; 累加器: 将元素添加的方法; 如add 合并器:
	 * 把对象合并的方法,如addAll 我们可以想象，并行的线程会创建多个集合对象,每个线程进行累加,最后通过合并得到最终结果
	 * 如果在合并器中定义顺序可以保证list的顺序.
	 */
	@Test
	public void collectEndingStreamOpTest3() {
		String[] alice = readAlice().split("\\PL+");
		String[] content = { "I", "love", "java", "and", "like", "running" };
		// 通过Collectors解决了并发问题,见collect javadoc.
		// toList:
		List<String> list = Stream.of(alice).parallel().collect(Collectors.toList());
		System.out.println("List: " + list.subList(0, 10).toString());

		// toSet:
		Set<String> set = Stream.of(alice).parallel().collect(Collectors.toSet());
		show("toSet", set);
		HashSet<String> hashSet = Stream.of(alice).parallel().collect(HashSet::new, HashSet::add, HashSet::addAll);
		show("toHashSet", hashSet);
		TreeSet<String> treeSet = Stream.of(alice).parallel().collect(Collectors.toCollection(TreeSet::new));
		show("toTreeSet", treeSet);

		// toString
		String result = Stream.of(content).parallel().collect(Collectors.joining(", "));
		System.out.println(result);

		// Get summaryStatistics
		IntSummaryStatistics summary = Stream.of(alice).parallel().collect(Collectors.summarizingInt(String::length));
		double averageWordLength = summary.getAverage();
		int maxWordLength = summary.getMax();
		System.out.println("Average word length: " + averageWordLength);
		System.out.println("Max word length: " + maxWordLength);

		// ToBitSet, 由于线程问题需要采用收集器
		IntStream is = new Random().ints(1, 100);
		BitSet bs = is.parallel().limit(15).collect(BitSet::new, BitSet::set, BitSet::or);
		System.out.println(bs);
	}

	@Test
	public void collect2MapTest() {
		// 1. 默认的map是HashMap
		Map<Integer, String> id2Name = people().collect(Collectors.toMap(Person::getId, Person::getName));
		System.out.println(id2Name.getClass().getName());
		System.out.println("idToName: " + id2Name);

		// 2. 如何把本身对象加入value? Function.identity
		Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
		System.out.println("idToPerson: " + idToPerson);

		// 3. 对于重复的key会抛出IllegalStateException
		// 如何处理? toMap第三个参数
		// 对于下面这个操作使用分组是更方便的操作
		Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
		Map<String, Set<String>> countryLangs = locales.parallel()
				.filter(l -> l.getDisplayCountry().trim().length() > 0).collect(Collectors.toMap(
						Locale::getDisplayCountry, l -> Collections.singleton(l.getDisplayLanguage()), (a, b) -> {
							Set<String> union = new HashSet<>(a);
							union.addAll(b);
							return union;
						}));
		System.out.println("countryLanguageSets: " + countryLangs);

		// 4. 如果想用TreeMap而不是HashMap怎么办？
		// toMap第四个参数
		Map<Integer, Person> toTreeMap = people()
				.collect(Collectors.toMap(Person::getId, Function.identity(), (o, n) -> o, TreeMap::new));
		System.out.println(toTreeMap.getClass().getName());
		System.out.println("toTreeMap: " + toTreeMap);

		// 5. 如果希望是并发map toMap -> toConcurrentMap
		Map<Integer, Person> concuMap = people().parallel()
				.collect(Collectors.toConcurrentMap(Person::getId, Function.identity(), (o, n) -> o));
		System.out.println(concuMap.getClass().getName());
		System.out.println("concuMap: " + concuMap);

	}

	@Test
	public void collectGroup() {
		// 1. groupby
		Stream<Locale> locales = Stream.of(Locale.getAvailableLocales()).parallel()
				.filter(l -> l.getDisplayCountry().trim().length() > 0);
		Map<String, List<Locale>> country2Locals = locales.collect(Collectors.groupingBy(Locale::getCountry));
		System.out.println("country2Locals: " + country2Locals);
		// 2. partition true/false group
		locales = Stream.of(Locale.getAvailableLocales()).parallel()
				.filter(l -> l.getDisplayCountry().trim().length() > 0);
		Map<Boolean, List<Locale>> isEnglish = locales
				.collect(Collectors.partitioningBy(l -> l.getLanguage().equals("en")));
		List<Locale> english = isEnglish.get(true);
		System.out.println("English: " + english);
		// 3. 得到并发map: concurrent by
		// groupingByConcurrent/partitioningByConcurrent

	}

	/*
	 * 下游收集器比较难懂超多函数式接口+泛型, 头晕
	 * 函数接口很简单
	 * 无参无返
	 * Runnalbe				null->void		run()
	 * 无参有返: 生产者
	 * Supplier<T>			null->T			get
	 * 有参无返: 消费者
	 * Consumer<T>			T->void			accept
	 * BiConsumer<T,U>		T,U->void
	 * 有参有返回: 函数
	 * Function<T,R>		T->R			apply
	 * BiFunction<T,U,R>	T,U->R
	 * 	衍生出T->T, T,T->T的叫做(元)操作
	 * 	UnaryOperation<T>	T->T			apply
	 * 	BinaryOperation<T>	T,T->T			apply
	 * 	衍生出断言
	 * 	Predicate<T>		T->boolean		test
	 * 	BiPredicate<T,U>	T,T->boolean	test
	 * 
	 */
	@Test
	public void downstreamTest() throws IOException {
		// 4. downstream 下游收集器
		// -a. groupby map value 为set
		Stream<Locale> locales = Stream.of(Locale.getAvailableLocales()).parallel()
				.filter(l -> l.getDisplayCountry().trim().length() > 0);
		Map<String, Set<Locale>> mapset = locales.collect(Collectors.groupingBy(Locale::getCountry, toSet()));
		System.out.println("mapset: " + mapset);
		// -b. counting/suming(Int|Long|Double)/maxBy/minBy
		// 计算洲(下属城市)的总人口
		Stream<City> cities = readCities();
		Map<String, Integer> stateToCityPopulation = cities
				.collect(groupingBy(City::getState, summingInt(City::getPopulation)));
		System.out.println("stateToCityPopulation: " + stateToCityPopulation);
		// 计算洲下属人口最多的城市
		cities = readCities();
		Map<String, Optional<City>> stateToLargestCity = cities
				.collect(groupingBy(City::getState, maxBy(Comparator.comparing(City::getPopulation))));
		// --功能同上通过reducing的identifier设置,去掉Optional
		cities = readCities();
		Map<String, City> stateToLargestCity2 = cities
				.collect(groupingBy(City::getState, reducing(new City("NA", "NA", 0), Function.identity(),
						BinaryOperator.maxBy(Comparator.comparing(City::getPopulation)))));
		// --上面数据还有一个办法就是toMap, 比较人口然后进行replace

		System.out.println("stateToLargestCity2: " + stateToLargestCity2);
		// 计算洲下属城市名最长的城市//mapping里面的maxBy只能处理String了
		cities = readCities();
		Map<String, Optional<String>> stateToLongestCityName = cities.collect(
				groupingBy(City::getState, mapping(City::getName, maxBy(Comparator.comparing(String::length)))));
		System.out.println("stateToLongestCityName: " + stateToLongestCityName);

		// 下面是每个洲的城市列表
		cities = readCities();
		Map<String, String> stateToCityNames = cities.collect(
				groupingBy(City::getState, reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)));
		System.out.println("stateToCityNames1: " + stateToCityNames);
		cities = readCities();
		stateToCityNames = cities.collect(groupingBy(City::getState, mapping(City::getName, joining(", "))));
		System.out.println("stateToCityNames2: " + stateToCityNames);

	}

	public static class City {
		private String name;
		private String state;
		private int population;

		public City(String name, String state, int population) {
			this.name = name;
			this.state = state;
			this.population = population;
		}

		public String getName() {
			return name;
		}

		public String getState() {
			return state;
		}

		public int getPopulation() {
			return population;
		}

		@Override
		public String toString() {
			return "City [name=" + name + ", state=" + state + ", population=" + population + "]";
		}
	}

	public static Stream<City> readCities() throws IOException {
		Path path = Paths.get("./src/main/java/c03/stream/cities.txt");
		// 注意这里写法没有关闭,因为延迟执行的原因,所以一旦关闭则流终结操作的时候发现流已经被关闭了.
		// 照抄了javacore的源码,不知道这样做是否合适,留个疑问.
		return Files.lines(path, StandardCharsets.UTF_8).map(l -> l.split(", "))
				.map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));

		// try (Stream<String> lines = Files.lines(path,
		// StandardCharsets.UTF_8)) {
		// return lines.map(l -> l.split(", ")).map(a -> new City(a[0], a[1],
		// Integer.parseInt(a[2])));
		// }
	}

	public static class Person {
		private int id;
		private String name;

		public Person(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String toString() {
			return getClass().getName() + "[id=" + id + ",name=" + name + "]";
		}
	}

	public static Stream<Person> people() {
		return Stream.of(new Person(1001, "Peter"), new Person(1002, "Paul"), new Person(1003, "Mary"));
	}

	public static Stream<Person> duppeople() {
		return Stream.of(new Person(1002, "Paul"), new Person(1001, "Peter"), new Person(1002, "Paul"),
				new Person(1003, "Mary"));
	}

	public Stream<String> letters(String w) {
		return IntStream.range(0, w.length()).mapToObj(i -> w.substring(i, i + 1));
		// IntStream is = word.codePoints();
		// is.mapToObj(i->new String(new char[]{(char)i}));
	}

	public <T> void peekItem1(T item) {
		Thread t = Thread.currentThread();
		System.out.println("  p1:" + t.getName() + ": " + item);
	}

	public <T> void peekItem2(T item) {
		Thread t = Thread.currentThread();
		System.out.println("  p2:" + t.getName() + ": " + item);
	}

	public static String readAlice() {
		Path path = Paths.get("./src/main/java/c03/stream/alice.txt");
		// System.out.println(path.toAbsolutePath());
		String content = "";
		try {
			content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			content = e.getMessage();
		}
		return content;
	}

	public static <T> void show(String title, Stream<T> stream) {
		System.out.println("Before collect to list.");
		final int SIZE = 10;
		List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
		System.out.print(title + ": ");
		if (firstElements.size() <= SIZE)
			System.out.println(firstElements);
		else {
			firstElements.remove(SIZE); // list is not a view.
			String out = firstElements.toString();
			System.out.println(out.substring(0, out.length() - 1) + ", ...]");
		}
	}

	public static <T> void show(String label, Set<T> set) {
		System.out.print(label + ": " + set.getClass().getName());
		System.out.println("[" + set.stream().limit(10).map(Object::toString).collect(Collectors.joining(", ")) + "]");
	}
}
