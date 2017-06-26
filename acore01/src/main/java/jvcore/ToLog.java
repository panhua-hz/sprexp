/**
 * 
 */
package jvcore;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
//@Target({ TYPE, FIELD, METHOD, PARAMETER })
@Target({METHOD})
/**
 * @author panhua
 *
 */
public @interface ToLog {
	public enum Level{DEBUG,INFO,WARNING,ERROR};
	Level level() default Level.INFO;
}
