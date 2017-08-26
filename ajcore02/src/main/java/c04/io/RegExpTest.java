package c04.io;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class RegExpTest {
	/*特殊字符: . *+? | {[() \ ^$ 
	 * 正则表达式用来表示某种规律的文本.
	 * 由简单到难:
	 * 1. 匹配一个字符
	 * --a. 使用.表示任意字符.
	 * --b. 特殊字符(\c) \a \e \f \n \r \t \\
	 * --c. 控制字符(\cX) \cH(退格符) ----不懂
	 * --d. []包含一个字符范围集合
	 * ----[]可以有^ && | a-z等操作
	 * --e. 特定字符集合(猜想： 大写字母表示非):
	 * -----\d, \D  数字; 非数字
	 * -----\w, \W  同[a-zA-Z0-9_], 非word
	 * -----\s, \S  表示空白,非空白
	 * -----\h,\v,\H,\V 表示水平空白符,垂直空白符,非xxx,非xxx
	 * --f. 预定义字符集合: 这个是java里面的,有点复杂,请查阅p310表9-5
	 * -----使用\p{...}和\P{...}表示
	 * -----包括Lower/Upper/Alpha/Digit/Alnum/Punct/Graph/Print/Cntrl/XDigit
	 * ------Space/Blank/ASCII
	 * 
	 * 1a. 匹配字符串:
	 * \Q...\E 开始和结束括起来的所有字符
	 * 如: \Q(...)\E匹配字符串(...)
	 * 
	 * 2. 表示重复:
	 * --a. *+? 表示0个或多个,一个或多个,0个或一个
	 * --b. {n} {n,} {m,n} 匹配m到n次重复
	 * 
	 * 3. 匹配(搜索):
	 * a. 量化表达式内匹配--我理解为某个对称符号(如<>,'')之中来匹配.
	 * ----Q? 尝试最短匹配:
	 * 比如: .*(<.+?>).* 获取<>里面最短串.
	 * 我猜()是特殊字符,所起作用是提升优先级,作为一个整体.
	 * ----Q+ 尝试最长匹配: 
	 * 比如: '[^']*+' 获取''里的最长串,若没有闭合单引号则失败.
	 * [^']表示除去单引号
	 * [^']*表示0或者多次重复
	 * [^']*+表示尝试最长匹配
	 * 
	 * b. 边界匹配:
	 * --b1. ^ $ 匹配开头,结尾
	 * --b2. \A \Z \z 开头 末尾 绝对末尾
	 * --b3. \b \B 单词边界, 非单词边界
	 * --b4. \R unicode换行符
	 * --b5. \G 上次匹配的结尾
	 * 
	 * 4. 分组:
	 * (X) 获取匹配X的结果 这是一个可以被引用的结果
	 * \n 匹配第n组 (第n个匹配值) 
	 * 比如 (['"]).*\1 匹配 'Fred'或者"Fred"但不匹配"Fred'
	 * 
	 * (?<name>X) 把X匹配的结果赋值给name
	 * 
	 * \k<name> name的组(name的值)
	 * 
	 * (?:X) 使用()但不获取匹配X的结果(就是上面分组赋值不算它)
	 */
	
	@Test
	/*
	 * 测试一个字符串是否匹配给定的表达式
	 */
	public void isMatchTest(){
		String regex = "[+-]?\\d+";
		CharSequence input = "-123";
		if (Pattern.matches(regex, input)) 
			System.out.println(input + " is an integer");
		
		//注意如果要重用表达式,进行compile一下更有效
		Pattern pattern = Pattern.compile(regex);
        input = "Fred";
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches())  
            System.out.println(input + " is not an integer");
	}
	
	@Test
	/*
	 * 在字符串中找出匹配的子串
	 */
	public void findMatchTest(){
		String regex = "[+-]?\\d+";
		Pattern pattern = Pattern.compile(regex);
		
		//使用stream.filter函数式
		Stream<String> strings = Stream.of("99 bottles of beer on the wall, 99 bottles of beer.".split(" "));
        Stream<String> result = strings.filter(pattern.asPredicate()); //过滤器中使用pattern.asPredicate()
        System.out.println(result.collect(Collectors.toList()));
		System.out.println("--------------------------------------");
        //使用循环matcher.find() 以及分组
        CharSequence input = "June 14, 1903";
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String match = matcher.group();            
            System.out.println(match);        
        }
        
	}
	@Test
	public void findMatchesTest2(){
		System.out.printf("%-30s%-30s%-20s\n", "Regex", "Input", "Matches");
        showMatches("a", "Java");	//a a
        showMatches(".", "Java");	//J a v a
        showMatches("\\x{1D546}", "The octonions \uD835\uDD46"); //
        showMatches("\\uD835\\uDD46", "The octonions \uD835\uDD46");
        showMatches("\\n", "Hello\nWorld");  //匹配换行
        showMatches("\\cJ", "Hello\nWorld");	//控制字符cJ是什么?	
        showMatches("\\\\", "c:\\windows\\system"); 
        showMatches("\\Q.\\E", "Hello. World."); //. .
        showMatches("[A-Za-z]", "San Jos\u00E9");
        showMatches("[^aeiou]", "Hello");
        showMatches("[\\p{L}&&[^A-Za-z]]", "San Jos\u00E9");
        showMatches("\\d", "99 bottles of beer");//注意是9 9而不是99
        showMatches("\\w", "99 bottles of ros茅");
        showMatches("\\s*,\\s*", "Hello, World");
        showMatches("(e|o).l", "Hello, World");
        showMatches("(['\"]).*\\1", "Hello, 'World'"); 
        showMatches("(?<quote>['\"]).*\\k<quote>", "Hello, 'World'");
        showMatches("(?:[a-z]:)?([\\\\/])\\w+(\\1\\w+)*", "c:\\windows\\system and /bin");//有点难懂 //c:\windows\system /bin 
        showMatches("(?i:jpe?g)", "JPEG, jpeg, JPG, and jpg");//JPEG jpeg JPG jpg
        showMatches("[0-9]{3,}", "99 bottles of 333");
        showMatches("<(.+?>).*</\\1", "<i>Hello</i>, <b>World</b>!");
        showMatches("'[^']*+'", "This 'joke' isn't funny.");
        showMatches("\\G\\w+,\\s*", "Athens, Rome, New York, Paris");
        showMatches("\\p{Punct}", "Hello, World!");
        showMatches("\\p{sc=Greek}", "2\u03C0r");
        showMatches("\\p{InLetterlike Symbols}", "Java\u2122");
        showMatches("\\pP", "Hello, World!"); // Ok to omit {} around one-letter properties
        showMatches("\\p{IsUppercase}", "Hello, World!");
        showMatches("\\p{javaJavaIdentifierStart}", "99 bottles");
	}
	
    public static void showMatches(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        System.out.printf("%-30s%-30s", regex, input);
        while (matcher.find()) {
            String match = matcher.group();
            System.out.print(match + " ");
        }
        System.out.println();        
    }
	
    @Test
    public void findMatchesWithPatternFlagTest3(){
    	showMatches("[AO\u00C9]", Pattern.CASE_INSENSITIVE, "San Jos茅");
        showMatches("[AO\u00C9]", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS, "San Jos茅");
        showMatches("^[a-z ]+$", Pattern.MULTILINE, "99 bottles\nof beer\non the\rwall\n");
        showMatches("^[a-z ]+$", Pattern.MULTILINE | Pattern.UNIX_LINES, "99 bottles\nof beer\non the\rwall\n");
        showMatches(".", 0, "99 bottles\nof beer");
        showMatches(".", Pattern.DOTALL, "99 bottles\nof beer");
        showMatches(".# What a pattern!", Pattern.COMMENTS, "Hello");
        showMatches(".", Pattern.LITERAL, "Hello. World.");
        showMatches("\u00E9", 0, "San Jose\u0301");
        showMatches("\u00E9", Pattern.CANON_EQ, "San Jose\u0301");
    }
    
    public static void showMatches(String regex, int flags, String input) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher matcher = pattern.matcher(input);
        System.out.printf("%-30s%-30s", regex, input);
        while (matcher.find()) {
            String match = matcher.group();
            System.out.print(match + " ");
        }
        System.out.println();        
    }
	
	@Test
	/*
	 * 用来分解字符串得到相应的值
	 */
	public void groupParseAndGetValueTest(){
		String input = "Blackwell Toaster    USD29.95";
		//注意下面pattern使用不同的表达式,但结果是一样的
		//注意这里就是()的使用方法: 组是按照左括号排序的,从1开始(第0组就是整个输入)
		Pattern pattern = Pattern.compile("(\\p{Alnum}+(\\s+\\p{Alnum}+)*)\\s+([A-Z]{3})([0-9.]*)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String item = matcher.group(1);
            String currency = matcher.group(3);
            String price = matcher.group(4);
            System.out.printf("item=%s,currency=%s,price=%s\n", item, currency, price);
        }
        
        pattern = Pattern.compile("(\\p{Alnum}+(?:\\s+\\p{Alnum}+)*)\\s+([A-Z]{3})([0-9.]*)");
        matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String item = matcher.group(1);
            String currency = matcher.group(2);
            String price = matcher.group(3);
            System.out.printf("item=%s,currency=%s,price=%s\n", item, currency, price);
        }
        
        pattern = Pattern.compile("(?<item>\\p{Alnum}+(\\s+\\p{Alnum}+)*)\\s+(?<currency>[A-Z]{3})(?<price>[0-9.]*)");
        matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String item = matcher.group("item");
            String currency = matcher.group("currency");
            String price = matcher.group("price");
            System.out.printf("item=%s,currency=%s,price=%s\n", item, currency, price);
        }     
	}
	
	@Test
	public void splitTest(){
		Pattern commas = Pattern.compile("\\s*,\\s*");
        CharSequence input = "Peter , Paul,  Mary";
        String[] tokens = commas.split(input);
        System.out.println(Arrays.toString(tokens));
        
        Stream<String> tokenStream = commas.splitAsStream(input);
        System.out.println(tokenStream.collect(Collectors.toList()));
	}
	
	@Test
	public void replaceTest(){
		Pattern commas = Pattern.compile("\\s*,\\s*");
		CharSequence input = "Peter , Paul,  Mary";
		Matcher matcher = commas.matcher(input);
        String result = matcher.replaceAll(","); //把空格去掉
        System.out.println(result);
	}
	
	@Test
	public void replaceTest2(){
		Pattern time = Pattern.compile("(\\d{1,2}):(\\d{2})");
		Matcher matcher = time.matcher("3:45");
		String result = matcher.replaceAll("$1 hours and $2 minutes");
        System.out.println(result);
	}
	
	@Test
	public void replaceTest3(){
		String result = "3:45".replaceAll(
                "(?<hours>\\d{1,2}):(?<minutes>\\d{2})", 
                "${hours} hours and ${minutes} minutes");
        System.out.println(result); 
	}
}
