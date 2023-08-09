package com.jdbc.model;

public abstract class Professor {
    private int id;
    private String nome;
    private int tipoProfessor;
    
    public int getTipoProfessor() {
		return tipoProfessor;
	}

	public void setTipoProfessor(int tipoProfessor) {
		this.tipoProfessor = tipoProfessor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
