package dao;
import java.util.Date;
import java.util.List;
import model.Passagem;

public interface PassagemDao {
    public void inserir(Passagem v);

    public void deletar(Passagem v);

    public void atualizar(Passagem v);

    public List<Passagem> listar();

    public Passagem procurarPorCodigo(int cod);
    
    public List<Passagem> procurarPorVoo(int cod);
    
    public List<Passagem> procurarPorCliente(int cod);
    
    public List<Passagem> procurarPorHoraVenda(Date data);
    
}
