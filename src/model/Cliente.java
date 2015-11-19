package model;

public class Cliente {

    private int id;
    private String nome, RG, telefone;

    public String getNome() {
        return nome;
    }

    public String getRG() {
        return RG;
    }

    public String getTelefone() {
        return telefone;
    }

    public Cliente(String nome, String RG, String telefone) {
        this.nome = nome;
        this.RG = RG;
        this.telefone = telefone;
    }

    public Cliente(int id, String nome, String RG, String telefone) {
        this.id = id;
        this.nome = nome;
        this.RG = RG;
        this.telefone = telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String cliente = "ID: " + this.id + " Nome: " + this.nome + " RG: " + this.RG + " Telefone: " + this.telefone;
        return cliente;
    }

}
