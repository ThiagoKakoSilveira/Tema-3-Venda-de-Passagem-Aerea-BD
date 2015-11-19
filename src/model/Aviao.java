package model;

public class Aviao {

    private int codigo;
    private String nome;

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigo(int id) {
        this.codigo = id;
    }

    public Aviao(String nome) {
        this.nome = nome;
    }

    public Aviao(int cod, String nome) {
        this.nome = nome;
        this.codigo = cod;
    }

    @Override
    public String toString() {
        return "Código: " + this.codigo + "Nome: " + this.nome;
    }
}
