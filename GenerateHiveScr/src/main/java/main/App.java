package main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.DBConnection;

public class App {

	public static void main(String[] args) throws SQLException {
		
		StringBuilder hiveScr = new StringBuilder();
		//Do query
		ResultSet sqlResult = null;
		
		String oraOwner = "STAT";
		String oraTableName = "DEALER_CORRECTIONS";
		
		String hiveOwner = "STAT";
		String hiveTableName = oraTableName;
		
		//List<String> oraTypeEqualsString = new ArrayList<>();
		List<String> oraTypeEqualsDecimal = new ArrayList<>();
		
		oraTypeEqualsDecimal.add("NUMBER");
		oraTypeEqualsDecimal.add("LONG");
		oraTypeEqualsDecimal.add("LONG RAW");
		
		String query = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION\r\n" + 
				"FROM all_tab_columns \r\n" + 
				"where owner = '"+ oraOwner +"' \r\n" + 
				"and table_name = '" + oraTableName + "' order by TABLE_NAME, COLUMN_ID";
		
		
		try {
			//Connect to DB
			DBConnection.openConnction();
			
			//Make result set
			sqlResult = QueryStatement.resultSet(DBConnection.getQuerryState(), query);
			
			//StringBuilder
			hiveScr.append(System.lineSeparator() + "CREATE TABLE " + hiveOwner + "." + hiveTableName + " (");
			
			//Print results
			while (sqlResult.next()) {
				hiveScr.append(System.lineSeparator());
				hiveScr.append(sqlResult.getString(2));
				if(oraTypeEqualsDecimal.contains(sqlResult.getString(3))) {
					hiveScr.append(" DECIMAL(" + sqlResult.getString(4) + ", " + sqlResult.getString(5) + "),");
				} else {
					hiveScr.append(" String,");
				}
			}
			hiveScr.deleteCharAt(hiveScr.length()-1);
			hiveScr.append(System.lineSeparator());
			hiveScr.append(");");
			hiveScr.append(System.lineSeparator());
			
			System.out.println(hiveScr);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection();
			sqlResult.close();
		}
	}
}
