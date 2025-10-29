package ucb.estudo.app;

import ucb.estudo.dao.CategoriaDAO;
import ucb.estudo.model.Categoria;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoriaMenu {
    private final Scanner sc;
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public CategoriaMenu(Scanner sc) { this.sc = sc; }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- CRUD Categoria ---");
            System.out.println("1 - Inserir categoria");
            System.out.println("2 - Atualizar categoria");
            System.out.println("3 - Deletar categoria");
            System.out.println("4 - Buscar por ID");
            System.out.println("5 - Listar todas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = InputUtils.readInt(sc);

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = InputUtils.readLine(sc);
                        System.out.print("Descrição: ");
                        String descricao = InputUtils.readLine(sc);
                        Categoria c = new Categoria(0, nome, descricao);
                        categoriaDAO.inserir(c);
                    }
                    case 2 -> {
                        System.out.print("ID da categoria a atualizar: ");
                        int id = InputUtils.readInt(sc);
                        Categoria atual = categoriaDAO.buscarPorId(id);
                        if (atual == null) System.out.println("Categoria não encontrada.");
                        else {
                            System.out.print("Nome [" + atual.getNome() + "]: ");
                            String nome = InputUtils.readLine(sc);
                            if (nome.isBlank()) nome = atual.getNome();
                            System.out.print("Descrição [" + atual.getDescricao() + "]: ");
                            String desc = InputUtils.readLine(sc);
                            if (desc.isBlank()) desc = atual.getDescricao();
                            atual.setNome(nome);
                            atual.setDescricao(desc);
                            categoriaDAO.atualizar(atual);
                        }
                    }
                    case 3 -> {
                        System.out.print("ID a deletar: ");
                        int id = InputUtils.readInt(sc);
                        categoriaDAO.deletar(id);
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        int id = InputUtils.readInt(sc);
                        System.out.println();
                        System.out.print("Categoria encontrada:");
                        Categoria c = categoriaDAO.buscarPorId(id);
                        if (c == null) System.out.println("Não encontrada.");
                        else {
                            System.out.println();
                            System.out.println(c);
                        }
                    }
                    case 5 -> {
                        List<Categoria> lista = categoriaDAO.listarTodos();
                        System.out.println();
                        System.out.println("Lista de todas as categorias:");
                        lista.forEach(System.out::println);
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
