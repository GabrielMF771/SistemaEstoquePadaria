package ucb.estudo.app;

import java.util.Scanner;

public class SistemaPadaria {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProdutoMenu produtoMenu = new ProdutoMenu(sc);
        EstoqueMenu estoqueMenu = new EstoqueMenu(sc);
        CategoriaMenu categoriaMenu = new CategoriaMenu(sc);

        while (true) {
            System.out.println("\n=== Sistema Padaria ===");
            System.out.println("1 - Gerenciar Produto");
            System.out.println("2 - Gerenciar Estoque");
            System.out.println("3 - Gerenciar Categoria");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = InputUtils.readInt(sc);

            switch (opcao) {
                case 1 -> produtoMenu.mostrarMenu();
                case 2 -> estoqueMenu.mostrarMenu();
                case 3 -> categoriaMenu.mostrarMenu();
                case 0 -> {
                    System.out.println("Encerrando...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}
