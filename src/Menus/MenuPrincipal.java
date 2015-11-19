package Menus;

public class MenuPrincipal {

    public static final int OP_Menu_Cliente = 1;
    public static final int OP_Menu_Avioes = 2;
    public static final int OP_Menu_Voo = 3;
    public static final int OP_Menu_Passagens = 4;
    public static final int OP_Menu_Relatorios = 5;
    public static final int OP_SAIR = 0;

    public static void mostrarMenu() {
        System.out.println("\n--------------------------------------\n"
                + "1- Menu de Cliente\n"
                + "2- Menu de Aviões\n"
                + "3- Menu de Vôo\n"
                + "4- Menu de Venda de Passagem\n"
                + "5- Relatórios de Venda\n"
                + "0- Sair da Aplicação"
                + "\n--------------------------------------");
    }

}
