package com.jdbc.controller;

import com.jdbc.connectionFactory.ConnectionFactory;
import com.jdbc.model.Prof_efetivo;
import com.jdbc.model.Prof_subs;
import com.jdbc.model.Professor;

import java.sql.*;
import java.util.ArrayList;

public class ProfessorDAO {
    Connection con = ConnectionFactory.getConnection();

    // colocar os tipos de professores possíveis em uma enum
    // enum é uma classe de tipagem, com isso, ao passar uma enum com os tipos de professores
    // eu posso acessar os tipos na passagem de parâmetro e evitar que sejam escritos erroneamente
    // na assinatura do método durante sua chamada
    // portanto, só passa logo essa porra desses professores para uma enum e para de falar demais
    public boolean verificarProfessor(Professor professor){
            try{
                String sql = "SELECT COUNT(*) AS profs FROM professor WHERE nomeProfessor=? OR idProfessor=?";
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

    public void setProfessor(Professor prof, int tipoProfessor){
        try{
            String sql = "INSERT INTO professor (idProfessor, nomeProfessor, tipoProfessor) VALUES (?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, prof.getId());
            stmt.setString(2, prof.getNome());
            stmt.setInt(3, tipoProfessor);
            stmt.execute();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void setProfessorEfetivo(Professor prof){
        Prof_efetivo profEfetivo = (Prof_efetivo) prof;
        prof.setTipoProfessor(2);

        if(verificarProfessor(profEfetivo)){
            System.out.println("Professor já cadastrado.");
        } else {
        	try{
                String sql = "INSERT INTO professor_efetivo (idProfEfetivo, nomeProfEfetivo, numSiape) VALUES (?,?,?)";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setInt(1, profEfetivo.getId());
                stmt.setString(2, profEfetivo.getNome());
                stmt.setInt(3, profEfetivo.getNumSiape());
                stmt.execute();
                setProfessor(profEfetivo, prof.getTipoProfessor());
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void setProfessorSubs(Professor prof){
        Prof_subs profSubs = (Prof_subs) prof;
        prof.setTipoProfessor(1);

        if(verificarProfessor(profSubs)){
            System.out.println("Professor já cadastrado.");
        } else {
            try{
                String sql = "INSERT INTO professor_subs (idProfSubs, nomeProfSubs, codContrato) VALUES (?,?,?)";

                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, profSubs.getId());
                stmt.setString(2, profSubs.getNome());
                stmt.setInt(3, profSubs.getCodContrato());
                stmt.execute();
                setProfessor(profSubs, prof.getTipoProfessor());
                } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void updateProfessorPorID(Professor prof){
        if(!verificarProfessor(prof)){
            System.out.println("Este ID de professor não está cadastrado.");
        } else{
            try{
                String sql = "UPDATE professor SET nomeProfessor=? WHERE idProfessor=?";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, prof.getNome());
                stmt.setInt(2, prof.getId());
                stmt.execute();

                System.out.println("Dados alterados com sucesso.");
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public String getProfessorPorID(Professor prof){
        if (!verificarProfessor(prof)){
            return "Este ID de professor não está cadastrado.";
        } else{
            try{
                String sql = "SELECT * FROM professor WHERE idProfessor=?";
                PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1, prof.getId());
                ResultSet rs = stmt.executeQuery();

                rs.next();
                return rs.getString("nomeProfessor");
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
                String sql = "DELETE FROM professor WHERE nomeProfessor=?";
                PreparedStatement stmt = con.prepareStatement(sql);

                stmt.setString(1, prof.getNome());
                stmt.execute();
                stmt.close();
                System.out.println("Professor removido.");
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}