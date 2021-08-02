package it.example.giacomo.provadb.DAOImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

import it.example.giacomo.provadb.DAOInterface.Dao;
import it.example.giacomo.provadb.Domain.User;
import it.example.giacomo.provadb.Domain.UserEntity;


public class JdbcUserDao implements Dao<User>{

		private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);
	
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  //NON VA com.mysql.cj.jdbc.Driver
	   //static final String DB_URL = "jdbc:mysql://localhost/java_prova";
	   static final String DB_URL = "jdbc:mysql://localhost:3306/java_prova";

	   // Database credentials
	   static final String DB_USER = "giacomo";
	   static final String DB_PASSWORD = "thepassword";
	   
	   private Connection conn = null;
	   private DataSource ds = null;

	   public void creaConnessione() {
		   
		   if (ds == null) {
			   ds = getMysqlDataSource();
		   }	   
		   //InitialContext ctx = null;
		   if(conn != null) {
			   return;
		   }
		   /*
		   try {
			    ctx = new InitialContext();	  
			    ds = (DataSource) ctx.lookup("java:/MySqlDS");
		   } catch(Exception e) {
			   e.printStackTrace();		   
		   }*/
		   
		   try {
			   conn = ds.getConnection();
		   } catch (SQLException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
			   conn = null;
		   }	   
	   }
	   
	   public void closeConnection() {
	      
		   try {
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			
			if (conn != null) {
	          try {
	              conn.close();
	          } catch (SQLException sqlex) {
	              // ignore, as we can't do anything about it here
	          }
	          conn = null;
	      }				
		}   	   	
	   }

	   protected DataSource getMysqlDataSource() {
	    	MysqlDataSource mysqlDataSource = new MysqlDataSource();
	            mysqlDataSource.setURL(DB_URL);
	            mysqlDataSource.setUser(DB_USER);
	            mysqlDataSource.setPassword(DB_PASSWORD);
	    	return mysqlDataSource;
	   }


	@Override
	public Optional<User> get(long id) {
		   creaConnessione();
		   ResultSet rs = null;	  
		   Statement stmt = null;
		   User user = new User("", "");
		   
		   try {
			   stmt = conn.createStatement();
		       String sql;
			   sql = "SELECT id, name, email FROM User WHERE id = " + id;
		       rs = stmt.executeQuery(sql);
		       
		       while(rs.next()) {
		    	   user.setId(rs.getInt("id"));
		    	   user.setName(rs.getString("name"));
		    	   user.setEmail(rs.getString("email"));
		       }
	           rs.close();
	           rs = null;
	           
	           stmt.close();
	           stmt = null;
	           
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
	    	   /*
	            * close any jdbc instances here that weren't
	            * explicitly closed during normal code path, so
	            * that we don't 'leak' resources...
	            */

	    	   if(rs != null) {
	    		   try {
	    			   rs.close();
	    		   } catch (SQLException sqlex) {
	    			// ignore, as we can't do anything about it here
	    		   }
	    		   
	    		   rs = null;
	    	   }
	    	   
	           if (stmt != null) {
	               try {
	                   stmt.close();
	               } catch (SQLException sqlex) {
	                   // ignore, as we can't do anything about it here
	               }

	               stmt = null;
	           }
		   }
		   
		   
		   closeConnection();
		   return Optional.ofNullable(user);
	}

	@Override
	public List<User> getAll() {
		   creaConnessione();
		   ResultSet rs = null;	  
		   Statement stmt = null;
		   List<User> userList = new ArrayList<>();
		   
		   try {
			   stmt = conn.createStatement();
		       String sql;
			   sql = "SELECT id, name, email FROM User";          
		       rs = stmt.executeQuery(sql);
		       
	           while (rs.next()) {
	               Integer id  = rs.getInt("id");	               
	               String name = rs.getString("name");
	               String email = rs.getString("email");
	               
	               User user = new User("", "");
	               user.setId(id);
	               user.setName(name);
	               user.setEmail(email);
	               userList.add(user);
	               //Display values
	               /*log.info("ID: " + id + 
	            		   ", Name: " + name +
	            		   ", Email: " + email);*/	               
	           }
	           rs.close();
	           rs = null;
	           
	           stmt.close();
	           stmt = null;	           
	           
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
	    	   /*
	            * close any jdbc instances here that weren't
	            * explicitly closed during normal code path, so
	            * that we don't 'leak' resources...
	            */

	    	   if(rs != null) {
	    		   try {
	    			   rs.close();
	    		   } catch (SQLException sqlex) {
	    			// ignore, as we can't do anything about it here
	    		   }
	    		   
	    		   rs = null;
	    	   }
	    	   
	           if (stmt != null) {
	               try {
	                   stmt.close();
	               } catch (SQLException sqlex) {
	                   // ignore, as we can't do anything about it here
	               }

	               stmt = null;
	           }
		   }
		   
		   closeConnection();
		   return userList;
	}

	@Override
	public void save(User u) {
		   creaConnessione();
		   ResultSet rs = null;	  
		   Statement stmt = null;		   
		   
		   try {
			   stmt = conn.createStatement();
		       String sql;
			   sql = "INSERT INTO User (name, email) VALUES ('"+u.getName()+"', '"+u.getEmail()+"')";          
		       int row = stmt.executeUpdate(sql);		       	         
	           stmt.close();
	           stmt = null;	           
	           
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
	    	   /*
	            * close any jdbc instances here that weren't
	            * explicitly closed during normal code path, so
	            * that we don't 'leak' resources...
	            */

	    	   if(rs != null) {
	    		   try {
	    			   rs.close();
	    		   } catch (SQLException sqlex) {
	    			// ignore, as we can't do anything about it here
	    		   }
	    		   
	    		   rs = null;
	    	   }
	    	   
	           if (stmt != null) {
	               try {
	                   stmt.close();
	               } catch (SQLException sqlex) {
	                   // ignore, as we can't do anything about it here
	               }

	               stmt = null;
	           }
		   }
		   
		   closeConnection();
	}

	@Override
	public void update(User u, String[] params) {
		   creaConnessione();
		   ResultSet rs = null;	  
		   Statement stmt = null;		   
		   
		   try {
			   stmt = conn.createStatement();
		       String sql;
			   sql = "UPDATE User SET name = '"+params[0]+"', email = '"+params[1]+"' WHERE id = " + u.getId();          
		       int row = stmt.executeUpdate(sql);		       	         
	           stmt.close();
	           stmt = null;	           
	           
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
	    	   /*
	            * close any jdbc instances here that weren't
	            * explicitly closed during normal code path, so
	            * that we don't 'leak' resources...
	            */

	    	   if(rs != null) {
	    		   try {
	    			   rs.close();
	    		   } catch (SQLException sqlex) {
	    			// ignore, as we can't do anything about it here
	    		   }
	    		   
	    		   rs = null;
	    	   }
	    	   
	           if (stmt != null) {
	               try {
	                   stmt.close();
	               } catch (SQLException sqlex) {
	                   // ignore, as we can't do anything about it here
	               }

	               stmt = null;
	           }
		   }
		   
		   closeConnection();	
	}

	@Override
	public void delete(User u) {
		   creaConnessione();
		   ResultSet rs = null;	  
		   Statement stmt = null;		   
		   
		   try {
			   stmt = conn.createStatement();
		       String sql;
			   sql = "DELETE FROM User WHERE id=" + u.getId();
		       int row = stmt.executeUpdate(sql);		       	         
	           stmt.close();
	           stmt = null;	           
	           
		   } catch (Exception e) {
			   e.printStackTrace();
		   } finally {
	    	   /*
	            * close any jdbc instances here that weren't
	            * explicitly closed during normal code path, so
	            * that we don't 'leak' resources...
	            */

	    	   if(rs != null) {
	    		   try {
	    			   rs.close();
	    		   } catch (SQLException sqlex) {
	    			// ignore, as we can't do anything about it here
	    		   }
	    		   
	    		   rs = null;
	    	   }
	    	   
	           if (stmt != null) {
	               try {
	                   stmt.close();
	               } catch (SQLException sqlex) {
	                   // ignore, as we can't do anything about it here
	               }

	               stmt = null;
	           }
		   }
		   
		   closeConnection();	
	}   

} //class jdbcUserDao
