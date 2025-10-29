package ucb.estudo.app;

import ucb.estudo.dao.ProdutoDAO;
import ucb.estudo.model.Produto;

import java.sql.SQLException;
import java.util.Scanner;

public class ProdutoMenu {
    private final Scanner sc;
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public ProdutoMenu(Scanner sc) {
        this.sc = sc;
    }

    public void mostrarMenu() {
        while (true) {
            System.out.println("\n--- CRUD Produto ---");
            System.out.println("1 - Inserir produto");
            System.out.println("2 - Atualizar produto");
            System.out.println("3 - Deletar produto");
            System.out.println("4 - Buscar produto por ID");
            System.out.println("5 - Listar todos os produtos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = InputUtils.readInt(sc);

            try {
                switch (opcao) {
                    case 1 -> {
                        Produto p = lerProduto(true);
                        produtoDAO.inserir(p);
                    }
                    case 2 -> {
                        System.out.print("ID do produto a atualizar: ");
                        int idAtualizar = InputUtils.readInt(sc);
                        Produto existente = produtoDAO.buscarPorId(idAtualizar);
                        if (existente == null) {
                            System.out.println("Produto com ID " + idAtualizar + " não encontrado.");
                        } else {
                            System.out.println("Informe novos valores (enter para manter o atual).");
                            Produto atualizado = lerProdutoComDefaults(existente);
                            produtoDAO.atualizar(atualizado);
                        }
                    }
                    case 3 -> {
                        System.out.print("ID do produto a deletar: ");
                        int idDeletar = InputUtils.readInt(sc);
                        produtoDAO.deletar(idDeletar);
                    }
                    case 4 -> {
                        System.out.print("ID do produto: ");
                        int idBuscar = InputUtils.readInt(sc);
                        Produto encontrado = produtoDAO.buscarPorId(idBuscar);
                        if (encontrado == null) System.out.println("Produto não encontrado.");
                        else {
                            System.out.println();
                            System.out.println("Produto encontrado: ");
                            System.out.println(encontrado);
                        }
                    }
                    case 5 -> {
                        System.out.println();
                        System.out.println("Lista de todos os produtos:");
                        for (Produto p : produtoDAO.listarTodos()) {
                            System.out.println(p);
                        }
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Erro de banco de dados: " + e.getMessage());
            }
        }
    }

    private Produto lerProduto(boolean requerId) {
        int id = 0;
        if (requerId) {
            System.out.print("ID: ");
            id = InputUtils.readInt(sc);
        }

        System.out.print("Nome: ");
        String nome = InputUtils.readLine(sc);

        System.out.print("Preço de Venda: ");
        double precoVenda = InputUtils.readDouble(sc);

        System.out.print("Preço de Custo: ");
        double precoCusto = InputUtils.readDouble(sc);

        System.out.print("Peso: ");
        double peso = InputUtils.readDouble(sc);

        System.out.print("Quantidade em Estoque: ");
        int quantidade = InputUtils.readInt(sc);

        System.out.print("Categoria ID: ");
        int categoriaId = InputUtils.readInt(sc);

        Produto produto = new Produto(id, nome, precoVenda, precoCusto, peso, categoriaId);
        produto.setQuantidadeEstoque(quantidade);
        return produto;
    }

    private Produto lerProdutoComDefaults(Produto atual) {
        System.out.print("Nome [" + atual.getNome() + "]: ");
        String nome = InputUtils.readLine(sc);
        if (nome.isBlank()) nome = atual.getNome();

        System.out.print("Preço de Venda [" + atual.getPrecoVenda() + "]: ");
        String pv = InputUtils.readLine(sc);
        double precoVenda = pv.isBlank() ? atual.getPrecoVenda() : InputUtils.parseDoubleOrDefault(pv, atual.getPrecoVenda());

        System.out.print("Preço de Custo [" + atual.getPrecoCusto() + "]: ");
        String pc = InputUtils.readLine(sc);
        double precoCusto = pc.isBlank() ? atual.getPrecoCusto() : InputUtils.parseDoubleOrDefault(pc, atual.getPrecoCusto());

        System.out.print("Peso [" + atual.getPeso() + "]: ");
        String ps = InputUtils.readLine(sc);
        double peso = ps.isBlank() ? atual.getPeso() : InputUtils.parseDoubleOrDefault(ps, atual.getPeso());

        System.out.print("Quantidade em Estoque [" + atual.getQuantidadeEstoque() + "]: ");
        String qt = InputUtils.readLine(sc);
        int quantidade = qt.isBlank() ? atual.getQuantidadeEstoque() : InputUtils.parseIntOrDefault(qt, atual.getQuantidadeEstoque());

        System.out.print("Categoria ID [" + atual.getCategoriaId() + "]: ");
        String cid = InputUtils.readLine(sc);
        int categoriaId = cid.isBlank() ? atual.getCategoriaId() : InputUtils.parseIntOrDefault(cid, atual.getCategoriaId());

        Produto resultado = new Produto(atual.getId(), nome, precoVenda, precoCusto, peso, categoriaId);
        resultado.setQuantidadeEstoque(quantidade);
        return resultado;
    }
}
