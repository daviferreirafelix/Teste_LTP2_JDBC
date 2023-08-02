package com.jdbc.controller;

import java.sql.*;
import java.util.ArrayList;

import com.jdbc.connectionFactory.ConnectionFactory;
import com.jdbc.model.Curso;

public class CursoDAO {
	Connection con = ConnectionFactory.getConnection();

	public boolean verificarCurso(Curso curso) {
		try {
			String sql = "SELECT COUNT(*) AS count FROM curso WHERE nome=? OR id=?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, curso.getNome());
			stmt.setInt(2, curso.getId());
			ResultSet rs = stmt.executeQuery();

			if(rs.next()) {
				int count = rs.getInt("count");
				return count > 0;
			}

			return false;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void adicionarCurso(Curso curso) {
		String sqlInsert = "INSERT INTO curso (nome, id) VALUES (?,?)";
		String nome = curso.getNome();
		int id = curso.getId();		

		if(verificarCurso(curso)) {
			System.out.println("Curso já cadastrado.");

		} else {
			try {
				PreparedStatement stmt = con.prepareStatement(sqlInsert);
				stmt.setString(1, nome);
				stmt.setInt(2, id);
				stmt.execute();
				stmt.close();	
				System.out.println("Curso adicionado.");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public void alterarCursoPorID(Curso curso) {
		String sqlUpdate = "UPDATE curso SET nome=? WHERE id=?";
		
		if(verificarCurso(curso)) {
			try {
				PreparedStatement stmt = con.prepareStatement(sqlUpdate);

				stmt.setString(1, curso.getNome());
				stmt.setInt(2, curso.getId());	
				stmt.execute();
				stmt.close();	
				System.out.println("Curso alterado.");
			} catch(SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Este curso não existe.");
		}		
	}

	public String buscarPorID(Curso curso) {
		ArrayList<String> list = new ArrayList<>();
		int colunaNomeCurso = 1;

		if (verificarCurso(curso)){
			return "Este ID de curso não está cadastrado.";
		} else{
			try {
				String sqlRead = "SELECT nome FROM curso WHERE id=?";
				PreparedStatement stmt = con.prepareStatement(sqlRead);
				stmt.setInt(1, curso.getId());
				ResultSet rs = stmt.executeQuery();

				rs.next();
				curso.setNome(rs.getString(colunaNomeCurso));
				list.add(curso.getNome());

				return list.toString();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getLista() {
		ArrayList<String> listNomes = new ArrayList<>();
		Curso curso;
		try {
			String sqlRead = "SELECT * FROM curso";
			PreparedStatement stmt = con.prepareStatement(sqlRead);
			ResultSet rs = stmt.executeQuery();

			if(!rs.next()){
				return "Não há cursos neste banco de dados.";
			} else{
				do{
					curso = new Curso();
					curso.setNome(rs.getString("nome"));
					listNomes.add(curso.getNome());
				} while(rs.next());

			stmt.close();
			return listNomes.toString();
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void removerPorNome(Curso curso) {
		if (!verificarCurso(curso)) {
			System.out.println("Este nome não existe na base de dados.");				
		} else {
			try {
				String sqlDelete = "DELETE FROM curso WHERE nome=?";
				PreparedStatement stmt = con.prepareStatement(sqlDelete);
				stmt.setString(1, curso.getNome());

				stmt.execute();
				stmt.close();
				System.out.println("Curso removido.");
			} catch(SQLException e) {
				e.printStackTrace();

			}
		}
	}
}