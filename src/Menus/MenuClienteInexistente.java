package Menus;

public class MenuClienteInexistente {

    public static final int OP_CADASTRAR = 1;
    public static final int OP_BUSCARNOME = 2;
    public static final int OP_VOLTAR = 0;

    public static void mostrarMenu() {
        System.out.println("\n--------------------------------------\n"
                + "1- Cadastrar um Novo Cliente\n"
                + "2- Tentar buscar por Nome\n"
                + "0- Voltar para o Menu de Venda de Passagem"
                + "\n--------------------------------------");
    }

}
