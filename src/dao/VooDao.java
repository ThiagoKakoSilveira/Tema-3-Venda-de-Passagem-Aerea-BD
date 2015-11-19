package dao;

import java.util.Date;
import java.util.List;
import model.Voo;
import model.Ponte_Aerea;

public interface VooDao {

    public void inserir(Voo v);

    public void deletar(Voo v);

    public void atualizar(Voo v);

    public List<Voo> listar();

    public Voo procurarPorCodigo(int cod);

    public List<Voo> procurarPorOrigem(int origem);

    public List<Voo> procurarPorDestino(int destino);

    public List<Voo> procurarPorHorario(Date data);
    
    public Ponte_Aerea pontePorId(int id);
    
    public List<Ponte_Aerea> listarPontes();    
}
