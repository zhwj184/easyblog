package org.work;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlMetaData {

	/*
	 * Create a Connection.
	 */
	public Connection GetJDBCDirectConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql", "root", "123456");
		return conn;
	}

	/*
	 * Fetch all Tables available in the schema named 'mysql'. You can as well
	 * use a different schema and fetch tables within that schema.
	 */
	public void GetAllAvailableTables(Connection conn) throws SQLException {
		String eachTableName;
		Statement stmt = conn.createStatement();
		;
		java.sql.ResultSetMetaData rsMetaData;
		ResultSet rsDBInfo;
		ResultSet rsTableInfo;
		// Connection MetaData Information.
		DatabaseMetaData meta = conn.getMetaData();
		
		
		System.out.println(meta.getSQLKeywords());
		
		System.out.println(meta.getDatabaseMajorVersion());
		System.out.println(meta.getDatabaseMinorVersion());
		
		System.out.println(meta.getSQLStateType());
		System.out.println(meta.getSystemFunctions());
		System.out.println(meta.getStringFunctions());	
		
		// ResultSet of all tables
		rsDBInfo = meta.getTables(null, null, null, new String[] { "TABLE" });

		/*
		 * Now that we have the Table names , lets get the Column Metadata
		 * information. We iterate over the Tables and print the details.
		 */
		while (rsDBInfo.next()) {
			eachTableName = rsDBInfo.getString("TABLE_NAME");
			System.out
					.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Table Name - "
							+ eachTableName + " ~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

			if (eachTableName != null) {
				rsTableInfo = stmt.executeQuery("Select * from "
						+ eachTableName);
				rsMetaData = rsTableInfo.getMetaData(); // Table MetaData
														// Information.

				for (int i = 1; i < rsMetaData.getColumnCount() + 1; i++) {
					System.out.println(i + ". " + rsMetaData.getColumnName(i)
							+ " " + rsMetaData.getColumnTypeName(i) + " "
							+ rsMetaData.getColumnDisplaySize(i) + " ");
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			MysqlMetaData dbInfoObj = new MysqlMetaData();
			Connection conObj = dbInfoObj.GetJDBCDirectConnection();
			dbInfoObj.GetAllAvailableTables(conObj);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
