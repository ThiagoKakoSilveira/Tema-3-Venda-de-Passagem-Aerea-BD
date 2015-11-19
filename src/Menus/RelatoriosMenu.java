package Menus;

public class RelatoriosMenu {

    public static final int OP_VisualizarPorCliente = 1;
    public static final int OP_VisualizarPorVoo = 2;
    public static final int OP_VisualizarPorOrigem = 3;
    public static final int OP_VisualizarPorDestino = 4;
    public static final int OP_VisualizarPorPeriodoDeVoo = 5;
    public static final int OP_Voltar = 0;

    public static void mostrarMenu() {
        System.out.println("\n--------------------------------------------\n"
                + "1- Relatório de venda por Cliente\n"
                + "2- Relatório de venda por Voo\n"
                + "3- Relatório de venda por Origem de Voo\n"
                + "4- Relatório de venda por Destino de Voo\n"
                + "5- Relatório de venda por Mês e Ano\n"
                + "0- Voltar"
                + "\n--------------------------------------------");
    }
}
