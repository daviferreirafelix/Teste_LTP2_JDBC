package com.jdbc.controller;

import com.jdbc.connectionFactory.ConnectionFactory;
import com.jdbc.model.Prof_efetivo;
import com.jdbc.model.Prof_subs;
import com.jdbc.model.Professor;

import java.sql.*;
import java.util.ArrayList;

public class ProfessorDAO {
    Connection con = ConnectionFactory.getConnection();

    public boolean verificarProfessor(Professor professor){
        String sql = "SELECT COUNT(*) AS profs FROM professor WHERE nome=? OR id=?";

        try{
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, professor.getNome());
            stmt.setInt(2, professor.getId());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                int quantProfs = rs.getInt("profs");
                return quantProfs > 0;
            }

            return false;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void setProfessor(Professor prof, int tipoProf){
        if(verificarProfessor(prof)){
            System.out.println("Professor já cadastrado.");
        } else {
            try {
                switch (tipoProf){
                    case 1 -> {
                    	int tipo = setProfessorSubs(prof);
                    	String sql = "INSERT INTO professor (id, NOME, tipoProfessor) VALUES (?,?,?)";
                    	PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setInt(1, prof.getId());
                        stmt.setString(2, prof.getNome());
                        stmt.setInt(3, tipo);
                        stmt.execute();
                    }

                    case 2 -> {
                    	int tipo = setProfessorEfetivo(prof);
                    	String sql = "INSERT INTO professor (id, NOME, tipoProfessor) VALUES (?,?,?)";
                    	PreparedStatement stmt = con.prepareStatement(sql);
                        stmt.setInt(1, prof.getId());
                        stmt.setString(2, prof.getNome());
                        stmt.setInt(3, tipo);
                        stmt.execute();
                    }
                }

                System.out.println("Professor cadastrado com sucesso.");
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public int setProfessorEfetivo(Professor prof){
        Prof_efetivo profEfetivo = (Prof_efetivo) prof;
        int idProfEfetivo = 1;
        
        try{
            String sql = "INSERT INTO professor_efetivo (numSiape, fk_idProfessor) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, profEfetivo.getNumSiape());
            stmt.setInt(2, profEfetivo.getId());
            stmt.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return idProfEfetivo;
    }

    public int setProfessorSubs(Professor prof){
        Prof_subs profSubs = (Prof_subs) prof;
        int idProfSubs = 2;
        try{
            String sql = "INSERT INTO professor_subs (codContrato, fk_idProfessor) VALUES (?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, profSubs.getCodContrato());
            stmt.setInt(2, profSubs.getId());
            stmt.execute();
            } catch(SQLException e){
                e.printStackTrace();
        }
        
        return idProfSubs;
    }

    public void updateProfessorPorID(Professor prof){
        if(!verificarProfessor(prof)){
            System.out.println("Este ID de professor não está cadastrado.");
        } else{
            try{
                String sql = "UPDATE professor SET nome=? WHERE id=?";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, prof.getNome());
                stmt.setInt(2, prof.getId());
                stmt.execute();

                System.out.println("Dados alterados com sucesso.");
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public String getProfessorPorID(Professor prof){
        ArrayList<String> arr = new ArrayList<>();
        int colunaNomeProfessor = 2;

        if (!verificarProfessor(prof)){
            return "Este ID de professor não está cadastrado.";
        } else{
            try{
                String sql = "SELECT * FROM professor WHERE id=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, prof.getId());
                ResultSet rs = stmt.executeQuery();

                rs.next();
                arr.add(rs.getString(colunaNomeProfessor));
                return arr.toString();

            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public String getList(){
        ArrayList<String> list = new ArrayList<>();
        int colunaNomeProfessor = 2;

        try{
            String sql = "SELECT * FROM professor";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if(!rs.next()){
                return "Não foi encontrado nenhum resultado.";
            } else{
                do{
                    list.add(rs.getString(colunaNomeProfessor));
                } while(rs.next());

                return list.toString();
            }

        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void deletePorNome(Professor prof){
        if (!verificarProfessor(prof)){
            System.out.println("Não existem professores cadastrados com este nome.");
        } else{
            try{
                String sql = "DELETE FROM professor WHERE nome=?";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, prof.getNome());
                stmt.execute();
                stmt.close();
                System.out.println("Professor removido.");
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}