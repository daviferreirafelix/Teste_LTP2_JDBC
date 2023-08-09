package com.jdbc.view;

import java.util.Scanner;

public class TestePrincipal {
	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);

		int novaOperacao = 0, encerrarOperacao = 0;

		do {
			System.out.println("Deseja acessar um CURSO, PROFESSOR ou DISCIPLINA?");
			System.out.println("Digite 1 para CURSO");
			System.out.println("Digite 2 para PROFESSOR");
			System.out.println("Digite 3 para DISCIPLINA");
			System.out.println("Ou digite 0 para SAIR");
			System.out.print("Escolha: ");
			int selecionarAcesso = scn.nextInt();
			scn.nextLine();

			switch (selecionarAcesso){
				case 0 -> System.out.println("Saindo...");
				case 1 -> {
					ManipularCurso.manipularCurso();
					System.out.print("Deseja uma nova operação? 0 para NÃO ou QUALQUER NÚMERO para SIM: ");
					novaOperacao = scn.nextInt();
					scn.nextLine();
				}
				case 2 -> {
					ManipularProfessor.manipularProfessor();
					System.out.print("Deseja uma nova operação? 0 para NÃO ou QUALQUER NÚMERO para SIM: ");
					novaOperacao = scn.nextInt();
					scn.nextLine();
				}
				case 3 -> {
					ManipularDisciplina.manipularDisciplina();
					System.out.print("Deseja uma nova operação? 0 para NÃO ou QUALQUER NÚMERO para SIM: ");
					novaOperacao = scn.nextInt();
					scn.nextLine();
				}
				default -> {
					System.out.println("Digite um comando válido.");
					System.out.print("Deseja uma nova operação? 0 para NÃO ou QUALQUER NÚMERO para SIM: ");
					novaOperacao = scn.nextInt();
					scn.nextLine();
				}
			}

		} while (novaOperacao != encerrarOperacao);

		System.out.println("Encerrado.");
	}
}
