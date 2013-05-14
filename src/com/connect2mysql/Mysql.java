package com.connect2mysql;
import java.sql.SQLException;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.DefaultSQLGenerator;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
public class Mysql {
	private SimpleJDBCConnectionPool connectionPool;
	private DefaultSQLGenerator generator;
	public Mysql(String server, String user, String pwd)
	{
		//com.mysql.jdbc.Driver
		//org.gjt.mm.mysql.Driver
		try {
			
			 connectionPool = new SimpleJDBCConnectionPool(
			 "org.gjt.mm.mysql.Driver", server, user, pwd, 2, 2);
			 System.out.println("connection pool created for Oracle on " + server);
			 } catch (SQLException e) {
			 // Handle error
			 e.printStackTrace();
			 }
			 generator = new DefaultSQLGenerator();
	}
	public SQLContainer queryTable(String tableName)
	 {
	 SQLContainer container = null;
	 
	 try
	 {
	 TableQuery tq = new TableQuery(tableName, connectionPool, generator); 
	 container = new SQLContainer(tq); 
	 System.out.println("container created for table " + tableName);
	 
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 return container;
	 }
	 public SQLContainer dataView(String viewName)
	 {
	 SQLContainer container = null;
	 
	 try
	 {
	 FreeformQuery tq = new FreeformQuery("select * from " + viewName, connectionPool); 
	 container = new SQLContainer(tq); 
	 System.out.println("container created for view " + viewName);
	 
	 } catch (SQLException e) {
	 e.printStackTrace();
	 }
	 return container;
	 }
}
