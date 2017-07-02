关于security
/*
	 * 1. csrf防护:
	 * a. thymeleaf和sf:form默认支持csrf
	 * b. 普通jsp需要 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>  ---未尝试
	 * 
	 * 2. http basic认证:
	 * a. httpBasic().realmName("Spittr") ----未尝试app，对于浏览器没作用
	 * b. 网上说会进行base64转化，fiddler查看没有发现。 base64仅仅用来转化为http可以接受的字符，非加密. ----仅仅浏览器
	 * 
	 * 3. Remember-me
	 * a. 在页面加入：
	 * <input id="remember_me" name="remember-me" type="checkbox"/>
       <label for="remember_me" class="inline">Remember me</label>
	 * b. 需要userDetailsService； ----ldap不支持
	 * c. 对于浏览器好像没有什么作用。 ----但是会多个cookie，对于浏览器来说，这个 cookie没用
	 * Cookie: remember-me=dTAwMToxNDk0MTYyMzgyNDg3OjliOWIwMTc1MTMxYmQxNTk3YjhhYjAzN2JjYjBhNzlk;
	 * 
	 * 4. 拦截请求匹配
	 * a. antMatchers
	 * b. regexMatchers
	 * c. spel: .antMatchers("/**").access("hasRole('ROLE_RD')")
	 * 
	 * 5. 通道安全 ----未尝试
	 * a. requiresChannel().xxMatchers("/").requiresSecure(); //需要https
	 * 
	 */

使用内存存储需要h2的包