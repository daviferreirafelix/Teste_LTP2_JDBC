package com.jdbc.controller;

import com.jdbc.connectionFactory.ConnectionFactory;
import com.jdbc.model.Disciplina;
import java.sql.*;
import java.util.ArrayList;

public class DisciplinaDAO {
    Connection con = ConnectionFactory.getConnection();

    public boolean verificarDisciplina(Disciplina disc){
        try{
            String sql = "SELECT COUNT(*) AS disciplinas FROM disciplina WHERE idDisciplina=? OR nomeDisciplina=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, disc.getId());
            stmt.setString(2, disc.getNome());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                int disciplinas = rs.getInt("disciplinas");
                return disciplinas > 0;
            }

            return false;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void setDisciplina(Disciplina disc){
        if (verificarDisciplina(disc)){
            System.out.println("Disciplina já cadastrada.");
        } else{
            try{
                String sql = "INSERT INTO disciplina (idDisciplina, nomeDisciplina) VALUES (?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, disc.getId());
                stmt.setString(2, disc.getNome());
                stmt.execute();
                System.out.println("Cadastro efetuado.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateDiscPorID(Disciplina disc){
        if (!verificarDisciplina(disc)){
            System.out.println("Disciplina não encontrada.");
        } else{
            try{
                String sql = "UPDATE disciplina SET nomeDisciplina=? WHERE idDisciplina=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, disc.getNome());
                stmt.setInt(2, disc.getId());
                stmt.execute();
                System.out.println("Atualização efetuada.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getDiscPorID(Disciplina disc){
        if (!verificarDisciplina(disc)){
            return "Disciplina não encontrada.";
        } else{
            try{
                String sql = "SELECT * FROM disciplina WHERE idDisciplina=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, disc.getId());
                ResultSet rs = stmt.executeQuery();

                rs.next();
                return rs.getString("nomeDisciplina");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String getList(Disciplina disc){
        ArrayList<String> arrayList = new ArrayList<>();

        if (verificarDisciplina(disc)){
            return "Não há disciplinas cadastradas.";
        } else{
            try{
                String sql = "SELECT * FROM disciplina";
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while(rs.next()){
                    arrayList.add(rs.getString("nomeDisciplina"));
                }

                return arrayList.toString();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deletePorNome(Disciplina disc){
        if (!verificarDisciplina(disc)){
            System.out.println("Disciplinas não encontradas.");
        } else{
            try{
                String sql = "DELETE FROM disciplina WHERE nomeDisciplina-?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setString(1, disc.getNome());
                stmt.execute();
                System.out.println("Disciplina deletada.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
