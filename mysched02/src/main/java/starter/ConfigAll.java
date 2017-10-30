package starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import config.ConfigCtxJPA;
import config.ConfigCtxSched;
import config.ConfigCtxWeb;

@Configuration
@Import(value={ConfigCtxWeb.class,ConfigCtxSched.class, ConfigCtxJPA.class})
public class ConfigAll {

}
