package com.jdbc.view;

import com.jdbc.controller.CursoDAO;
import com.jdbc.model.Curso;

import java.util.Scanner;

public class ManipularCurso {
        public static void manipularCurso(){
            Scanner scn = new Scanner(System.in);
            Curso curso = new Curso();
            CursoDAO cursoDAO = new CursoDAO();

            System.out.println("Digite 1 para inserir dados.");
            System.out.println("Digite 2 para alterar dados já inseridos.");
            System.out.println("Digite 3 para buscar um curso pelo ID.");
            System.out.println("Digite 4 para listar todos os cursos.");
            System.out.println("Digite 5 para remover um curso.");
            System.out.println("Digite 0 para sair. ");
            System.out.print("Qual operação deseja fazer?: ");
            int escolha = scn.nextInt();

            switch (escolha) {
                case 0 -> System.out.println("Saindo...");

                case 1 -> {
                    System.out.print("Insira o nome do curso: ");
                    curso.setNome(scn.next());
                    scn.nextLine();
                    System.out.print("Insira o ID do curso: ");
                    curso.setId(scn.nextInt());
                    cursoDAO.adicionarCurso(curso);
                    scn.nextLine();
                }
                case 2 -> {
                    System.out.print("Insira o ID do curso a ser alterado: ");
                    curso.setId(scn.nextInt());
                    scn.nextLine();
                    System.out.print("Insira o novo nome do curso: ");
                    curso.setNome(scn.next());
                    cursoDAO.alterarCursoPorID(curso);
                    scn.nextLine();
                }
                case 3 -> {
                    System.out.print("Escreva o ID do curso: ");
                    curso.setId(scn.nextInt());
                    System.out.println(cursoDAO.buscarPorID(curso));
                    scn.nextLine();
                }
                case 4 -> System.out.println(cursoDAO.getLista());

                case 5 -> {
                    System.out.print("Insira o nome do curso a ser removido: ");
                    curso.setNome(scn.next());
                    cursoDAO.removerPorNome(curso);
                    scn.nextLine();
                }
                default -> System.out.println("ERRO. Insira uma opção válida.");

            }
        }
    }
