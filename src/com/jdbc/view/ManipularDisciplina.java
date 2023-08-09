package com.jdbc.view;

import com.jdbc.controller.CursoDAO;
import com.jdbc.controller.DisciplinaDAO;
import com.jdbc.model.Curso;
import com.jdbc.model.Disciplina;
import java.util.Scanner;

public class ManipularDisciplina {
    public static void manipularDisciplina(){
        Scanner scn = new Scanner(System.in);
        Curso curso = new Curso();
        CursoDAO cursoDAO = new CursoDAO();
        Disciplina disc = new Disciplina();
        DisciplinaDAO discDAO = new DisciplinaDAO();

        System.out.println("Digite 1 para inserir dados.");
        System.out.println("Digite 2 para alterar dados já inseridos.");
        System.out.println("Digite 3 para buscar uma disciplina pelo ID.");
        System.out.println("Digite 4 para listar todas as disciplinas.");
        System.out.println("Digite 5 para remover uma disciplina.");
        System.out.println("Digite 0 para sair. ");
        System.out.print("Qual operação deseja fazer?: ");
        int escolha = scn.nextInt();
        scn.nextLine();

        switch (escolha){
            case 0 -> System.out.println("Saindo....");
            case 1 -> {
                System.out.print("Insira o NOME da disciplina: ");
                disc.setNome(scn.next());
                scn.nextLine();

                System.out.print("Insira o ID desta disciplina: ");
                disc.setId(scn.nextInt());
                scn.nextLine();

                String listaCurso = cursoDAO.getLista();

                System.out.println("A qual destes cursos a disciplina pertence?");
                System.out.println(listaCurso);
                System.out.print("Digite o ID do curso: ");
                curso.setId(scn.nextInt());
                scn.nextLine();

                String nomeCurso = cursoDAO.buscarPorID(curso);

                for (int i = 0; i < listaCurso.length(); i++) {
                    if (listaCurso.contains(nomeCurso)) {
                        discDAO.setDisciplina(disc);
                        break;
                    }
                }
            }
            case 2 -> {
                System.out.print("Insira o ID da disciplina a ser alterada: ");
                disc.setId(scn.nextInt());
                scn.nextLine();
                System.out.print("Agora, de o novo NOME desta disciplina: ");
                disc.setNome(scn.next());
                scn.nextLine();
                discDAO.updateDiscPorID(disc);
            }
            case 3 -> {
                System.out.print("Dê o ID da disciplina desejada: ");
                disc.setId(scn.nextInt());
                scn.nextLine();
                System.out.println(discDAO.getDiscPorID(disc));
            }
            case 4 -> System.out.println(discDAO.getList(disc));
            case 5 -> {
                System.out.print("Insira o NOME da disciplina a ser deletada: ");
                disc.setNome(scn.next());
                scn.nextLine();
                discDAO.deletePorNome(disc);
            }
            default -> System.out.println("Insira uma opção válida.");
        }
    }
}
