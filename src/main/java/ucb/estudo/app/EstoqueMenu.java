package ucb.estudo.app;

import ucb.estudo.dao.EstoqueDAO;
import ucb.estudo.model.Estoque;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EstoqueMenu {
    private final Scanner sc;
    private final EstoqueDAO estoqueDAO = new EstoqueDAO();

    public EstoqueMenu(Scanner sc) { this.sc = sc; }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- Gerenciar Estoque ---");
            System.out.println("1 - Inserir registro de estoque");
            System.out.println("2 - Atualizar registro de estoque");
            System.out.println("3 - Deletar registro de estoque");
            System.out.println("4 - Listar por Produto ID");
            System.out.println("5 - Listar todos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = InputUtils.readInt(sc);

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Produto ID: ");
                        int pid = InputUtils.readInt(sc);
                        System.out.print("Quantidade: ");
                        int q = InputUtils.readInt(sc);
                        Estoque e = new Estoque(0, q, pid);
                        estoqueDAO.inserir(e);
                    }
                    case 2 -> {
                        System.out.print("Estoque ID a atualizar: ");
                        int id = InputUtils.readInt(sc);
                        Estoque atual = estoqueDAO.buscarPorId(id);
                        if (atual == null) System.out.println("Registro não encontrado.");
                        else {
                            System.out.print("Quantidade [" + atual.getQuantidade() + "]: ");
                            String qt = InputUtils.readLine(sc);
                            int q = qt.isBlank() ? atual.getQuantidade() : InputUtils.parseIntOrDefault(qt, atual.getQuantidade());
                            atual.setQuantidade(q);
                            estoqueDAO.atualizar(atual);
                        }
                    }
                    case 3 -> {
                        System.out.print("Estoque ID a deletar: ");
                        int id = InputUtils.readInt(sc);
                        estoqueDAO.deletar(id);
                    }
                    case 4 -> {
                        System.out.print("Produto ID: ");
                        int pid = InputUtils.readInt(sc);
                        List<Estoque> list = estoqueDAO.listarPorProdutoId(pid);
                        System.out.println();
                        System.out.println("Produto encontrado:");
                        list.forEach(System.out::println);
                    }
                    case 5 -> {
                        List<Estoque> todos = estoqueDAO.listarTodos();
                        System.out.println();
                        System.out.println("Lista de todo o estoque:");
                        todos.forEach(System.out::println);
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Erro de BD: " + e.getMessage());
            }
        }
    }
}
