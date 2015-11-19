package model;

import java.util.Date;
import util.DateUtil;

public class Passagem {

    private int codigo;
    private Cliente cliente;
    private Voo voo;
    private Date horaVenda;

    public Passagem(int codigo, Cliente cliente, Voo voo, Date horaVenda) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.voo = voo;
        this.horaVenda = horaVenda;
    }

    public Passagem(Cliente cliente, Voo voo, Date horaVenda) {
        this.cliente = cliente;
        this.voo = voo;
        this.horaVenda = horaVenda;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    public Date getHoraVenda() {
        return horaVenda;
    }

    public void setHoraVenda(Date horaVenda) {
        this.horaVenda = horaVenda;
    }

    @Override
    public String toString() {
        String horaVendida = DateUtil.dateHourToString(horaVenda);
        String venda = " Código de Venda: " + this.codigo + " Vendida às " + horaVendida + " horas."
                + "\n Passageiro: " + this.cliente
                + "\n " + this.voo;
                
        return venda;
    }

}
