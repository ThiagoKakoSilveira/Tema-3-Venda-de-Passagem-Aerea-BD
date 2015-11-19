package model;

public class Ponte_Aerea {

    private int id;
    private Cidade destino, origem;

    public int getId() {
        return id;
    }

    public Cidade getDestino() {
        return destino;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Ponte_Aerea(int id, Cidade destino, Cidade origem) {
        this.id = id;
        this.destino = destino;
        this.origem = origem;
    }

    @Override
    public String toString() {
        String ponte = "ID: " + this.id + " \nCidade de Origem: " + this.origem.getNome() + " UF: " + this.origem.getUf() + " \nCidade de Destino: "
                + this.destino.getNome() + " UF: " + destino.getUf();
        return ponte;
    }
}
