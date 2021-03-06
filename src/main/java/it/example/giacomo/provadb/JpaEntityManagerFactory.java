package it.example.giacomo.provadb;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import it.example.giacomo.provadb.Domain.UserEntity;
import com.mysql.cj.jdbc.MysqlDataSource;

public class JpaEntityManagerFactory {
	
    private final String DB_URL = "jdbc:mysql://localhost:3306/java_prova";
    private final String DB_USER_NAME = "giacomo";
    private final String DB_PASSWORD = "thepassword";


    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    
    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
          .build();
    }
    
    protected PersistenceUnitInfoImpl getPersistenceUnitInfo(String name) {
        return new PersistenceUnitInfoImpl(name, getEntityClassNames(), getProperties());
    }
    
    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
          .stream()
          .map(Class::getName)
          .collect(Collectors.toList());
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }

    protected Class[] getEntities() {
        return new Class[]{UserEntity.class};
    }

    protected DataSource getMysqlDataSource() {
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(DB_URL);
        mysqlDataSource.setUser(DB_USER_NAME);
        mysqlDataSource.setPassword(DB_PASSWORD);
	return mysqlDataSource;
    }
    
}
