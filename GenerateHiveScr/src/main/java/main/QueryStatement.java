package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryStatement {
	public static ResultSet resultSet(Statement state, String query) throws SQLException {
		ResultSet queryResult = state.executeQuery(query);
		return queryResult;
	}
}
