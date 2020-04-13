package it.polito.tdp.anagrammi.DAO;

import java.sql.Connection;

import java.sql.*;
import java.util.*;

public class AnagrammiDAO {
	
	public Set <String> getParoleDataLunghezza(int i) {

		final String sql = "SELECT parola.nome\r\n" + 
				"FROM parola\r\n" + 
				"WHERE LENGTH(parola.nome) = ?;";

		Set <String> parole = new LinkedHashSet<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, i);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				parole.add(rs.getString("nome").toUpperCase());
				
			}

			conn.close();
			
			return parole;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
}