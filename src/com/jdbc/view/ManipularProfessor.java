package com.jdbc.view;

import com.jdbc.controller.ProfessorDAO;
import com.jdbc.model.Prof_efetivo;
import com.jdbc.model.Prof_subs;
import com.jdbc.model.Professor;

import java.util.Scanner;

public class ManipularProfessor {
    public static void manipularProfessor() {
        Scanner scn = new Scanner(System.in);
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor prof = new Professor();
        Prof_subs profSubs = new Prof_subs();
        Prof_efetivo profEfetivo = new Prof_efetivo();

        System.out.println("Digite 1 para inserir dados.");
        System.out.println("Digite 2 para alterar dados já inseridos.");
        System.out.println("Digite 3 para buscar um professor pelo ID.");
        System.out.println("Digite 4 para listar todos os professores.");
        System.out.println("Digite 5 para remover um professor.");
        System.out.println("Digite 0 para sair. ");
        System.out.print("Qual operação deseja fazer?: ");
        int escolha = scn.nextInt();
        scn.nextLine();

        int tipoProfessor;
        switch (escolha){
            case 0 -> System.out.println("Saindo...");

            case 1 -> {
                System.out.print("Insira 1 se o professor é TEMPORÁRIO ou 2 se é EFETIVO: ");
                tipoProfessor = scn.nextInt();
                scn.nextLine();

                switch (tipoProfessor){
                    case 1 -> {
                        System.out.print("Insira o NOME do professor: ");
                        profSubs.setNome(scn.next());
                        scn.nextLine();
                        System.out.print("Insira o ID do professor: ");
                        profSubs.setId(scn.nextInt());
                        scn.nextLine();
                        System.out.print("Insira o CÓDIGO DE CONTRATO do professor: ");
                        profSubs.setCodContrato(scn.nextInt());

                        professorDAO.setProfessor(profSubs, tipoProfessor);
                    }
                    case 2 -> {
                        System.out.print("Insira o NOME do professor: ");
                        profEfetivo.setNome(scn.next());
                        scn.nextLine();
                        System.out.print("Insira o ID do professor: ");
                        profEfetivo.setId(scn.nextInt());
                        scn.nextLine();
                        System.out.print("Insira o NÚMERO SIAPE do professor: ");
                        profEfetivo.setNumSiape(scn.nextInt());

                        professorDAO.setProfessor(profEfetivo, tipoProfessor);
                    }

                    default -> System.out.println("Insira uma opção válida.");
                }
            }

            case 2 -> {
                System.out.print("Insira o ID do professor a ser alterado: ");
                profSubs.setId(scn.nextInt());
                scn.nextLine();
                System.out.print("Insira o novo nome refente a este ID: ");
                profSubs.setNome(scn.next());
                scn.nextLine();

                professorDAO.updateProfessorPorID(profSubs);
            }

            case 3 -> {
                System.out.print("Insira o ID do professor a ser buscado: ");
                profSubs.setId(scn.nextInt());
                scn.nextLine();

                System.out.println(professorDAO.getProfessorPorID(profSubs));
            }

            case 4 -> {
                System.out.println("Buscando lista de professores cadastrados...");
                System.out.println(professorDAO.getList());
            }

            case 5 -> {
                System.out.print("Digite o NOME do professor a ser removido: ");
                prof.setNome(scn.next());
                scn.nextLine();

                professorDAO.deletePorNome(prof);
            }

            default -> System.out.println("Insira uma opção válida.");
        }
    }
}
