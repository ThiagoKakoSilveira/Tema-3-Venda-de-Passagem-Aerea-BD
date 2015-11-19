package Menus;

public class AvioesMenu {

    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_EDITAR = 3;
    public static final int OP_DELETAR = 4;    
    public static final int OP_VOLTAR = 0;

    public static void mostrarMenu() {
        System.out.println("\n--------------------------------------\n"
                + "1- Cadastrar Aviões\n"
                + "2- Listar Aviões\n"
                + "3- Editar Aviões\n"
                + "4- Deletar Aviões\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }
}
