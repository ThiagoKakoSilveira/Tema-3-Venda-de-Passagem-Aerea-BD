package Menus;

public class ClienteMenu {

    public static final int OP_CADASTRAR = 1;
    public static final int OP_LISTAR = 2;
    public static final int OP_EDITAR = 3;
    public static final int OP_DELETAR = 4;
    public static final int OP_VOLTAR = 0;

    public static void MostrarMenuCliente() {
        System.out.println("\n--------------------------------------\n"
                + "1- Cadastrar Cliente\n"
                + "2- Listar Clientes\n"
                + "3- Editar Cliente\n"
                + "4- Menu Deletar Cliente\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }
}
