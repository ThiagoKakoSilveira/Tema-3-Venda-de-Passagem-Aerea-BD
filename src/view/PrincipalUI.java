package view;

import util.Console;
import Menus.MenuPrincipal;

public class PrincipalUI {

    public void executar() {
        int opcao = 0;
        do {
            MenuPrincipal.mostrarMenu();
            try {
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case MenuPrincipal.OP_Menu_Cliente:
                        new ClienteUI().executar();
                        break;
                    case MenuPrincipal.OP_Menu_Avioes:
                        new AviaoUI().executar();
                        break;
                    case MenuPrincipal.OP_Menu_Voo:
                        new VooUI().executar();
                        break;
                    case MenuPrincipal.OP_Menu_Passagens:
                        new PassagemUI().executar();
                        break;
                    case MenuPrincipal.OP_Menu_Relatorios:
                        new Relatorio_de_VendaUI().executar();
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (Exception ex) {
                System.out.println("Coloque apenas dígitos...");
                opcao = 100;
            }
        } while (opcao != MenuPrincipal.OP_SAIR);
    }
}
