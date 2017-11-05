package starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.ConfigCtxJPA;
import config.ConfigCtxSched;
import config.ConfigCtxWeb;

@Configuration
@Import(value={ConfigCtxSched.class, ConfigCtxJPA.class, ConfigCtxWeb.class})
public class ConfigAll {
	
}
