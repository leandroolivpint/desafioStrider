package com.example.desafiostriderm;

public class Tarefa {
    private String descricao;
    private boolean flag;
    private String id;

    public Tarefa(String id, String descricao, boolean flag) {
        this.id = id;
        this.descricao = descricao;
        this.flag = flag;
    }

    public String getDescricao() {
        return descricao;
    }

    public String  getId() {
        return id;
    }

    public boolean isFlag() {
        return flag;
    }
}
