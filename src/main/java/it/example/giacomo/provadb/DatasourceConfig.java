package it.example.giacomo.provadb;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

//@PropertySources(value = {@PropertySource("classpath:application.properties")})
//@PropertySource("classpath:application.properties")
@Configuration
public class DatasourceConfig {
	
	
	/*@Bean
	public DataSource datasource() {
        return DataSourceBuilder.create()
        		.driverClassName("com.mysql.cj.jdbc.Driver")
        		.url("jdbc:mysql://localhost:3306/java_prova")
        		.username("giacomo")
        		.password("thepassword")
        		.build();
	}*/


}
