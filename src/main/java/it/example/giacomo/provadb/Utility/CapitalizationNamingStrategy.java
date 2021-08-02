package it.example.giacomo.provadb.Utility;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class CapitalizationNamingStrategy extends SpringPhysicalNamingStrategy {

	private final String HIBERNATE_PREFIX = "hibernate"; 
	
	@Override
    protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
        
		//check if table name is a hibernate table name
		if (name.length() >= 9) {
			String hibernatePref = name.substring(0,9);
			if (hibernatePref.equals(HIBERNATE_PREFIX)) {
				return new Identifier(name, quoted);
			}
		}
		
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		return new Identifier(name, quoted);
    }
	
}
