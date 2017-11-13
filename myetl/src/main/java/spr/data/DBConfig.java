package spr.data;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("spr.data")
public class DBConfig {
	@Bean
    public DataSource dataSource() {
		return new DriverManagerDataSource("jdbc:oracle:thin:@HZ27P4993473.corp.statestr.com:1521:xe","my_lc","mylc");
	}
	@Bean
    public JdbcOperations jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
