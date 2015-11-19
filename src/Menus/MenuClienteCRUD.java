package Menus;

public class MenuClienteCRUD {
    
    public static final int OP_ID = 1;
    public static final int OP_RG = 2;
    public static final int OP_NOME = 3;
    public static final int OP_TELEFONE = 4;
    public static final int OP_VOLTAR = 0;
    
    public static void MostrarMenuClienteCRUD() {
        System.out.println("\n----------- Deletar Por: ------------\n"
                + "1- ID do Cliente\n"
                + "2- RG do Cliente\n"
                + "3- NOME do Cliente\n"
                + "4- TELEFONE do Cliente\n"
                + "0- Voltar"
                + "\n--------------------------------------");
    }    
}
