package Menus;

public class PassagensMenu {

    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_EDITAR = 3;
    public static final int OP_DELETAR = 4;
    public static final int OP_VOLTAR = 0;

    public static void mostrarMenu() {
        System.out.println("\n--------------------------------------\n"
                + "1- Cadastrar Venda de Passagem\n"
                + "2- Listar Passagens Vendidas\n"
                + "3- Editar Passagens Vendidas\n"
                + "4- Deletar Passagens Vendidas\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }
}
