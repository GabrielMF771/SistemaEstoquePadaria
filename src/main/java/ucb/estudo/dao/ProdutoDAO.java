// java
package ucb.estudo.dao;

import ucb.estudo.model.Produto;
import ucb.estudo.model.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto) throws SQLException {
        String sqlProd = "INSERT INTO produto (produto_id, nome, preco_venda, preco_custo, peso, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlEst = "INSERT INTO estoque (quantidade, produto_id) VALUES (?, ?)";
        Connection conn = null;
        try {
            conn = ConexaoMySQL.obterConexao();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtProd = conn.prepareStatement(sqlProd)) {
                stmtProd.setInt(1, produto.getId());
                stmtProd.setString(2, produto.getNome());
                stmtProd.setDouble(3, produto.getPrecoVenda());
                stmtProd.setDouble(4, produto.getPrecoCusto());
                stmtProd.setDouble(5, produto.getPeso());
                stmtProd.setInt(6, produto.getCategoriaId());
                stmtProd.executeUpdate();
            }

            // se houver quantidade, insere um registro em estoque
            if (produto.getQuantidadeEstoque() > 0) {
                try (PreparedStatement stmtEst = conn.prepareStatement(sqlEst)) {
                    stmtEst.setInt(1, produto.getQuantidadeEstoque());
                    stmtEst.setInt(2, produto.getId());
                    stmtEst.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("Produto inserido e estoque atualizado.");
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Erro ao salvar produto no BD: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); ConexaoMySQL.fecharConexao(conn); } catch (SQLException ignored) {}
            }
        }
    }

    public void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produto SET nome = ?, preco_venda = ?, preco_custo = ?, peso = ?, categoria_id = ?, WHERE produto_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoVenda());
            stmt.setDouble(3, produto.getPrecoCusto());
            stmt.setDouble(4, produto.getPeso());
            stmt.setInt(5, produto.getCategoriaId());
            stmt.setInt(6, produto.getId());

            int linhas = stmt.executeUpdate();
            System.out.println("Produto atualizado. Linhas afetadas: " + linhas);
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto no BD: " + e.getMessage());
            throw e;
        }
    }

    public void deletar(int id) throws SQLException {
        String sqlDelEst = "DELETE FROM estoque WHERE produto_id = ?";
        String sqlDelProd = "DELETE FROM produto WHERE produto_id = ?";
        Connection conn = null;
        try {
            conn = ConexaoMySQL.obterConexao();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtEst = conn.prepareStatement(sqlDelEst)) {
                stmtEst.setInt(1, id);
                stmtEst.executeUpdate();
            }

            try (PreparedStatement stmtProd = conn.prepareStatement(sqlDelProd)) {
                stmtProd.setInt(1, id);
                int linhas = stmtProd.executeUpdate();
                System.out.println("Produto removido. Linhas afetadas: " + linhas);
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
            }
            System.err.println("Erro ao deletar produto no BD: " + e.getMessage());
            throw e;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); ConexaoMySQL.fecharConexao(conn); } catch (SQLException ignored) {}
            }
        }
    }

    public Produto buscarPorId(int id) throws SQLException {
        String sql = """
                SELECT p.produto_id, p.nome, p.preco_venda, p.preco_custo, p.peso,
                       p.categoria_id,
                       COALESCE(SUM(e.quantidade), 0) AS quantidade_estoque
                FROM produto p
                LEFT JOIN estoque e ON e.produto_id = p.produto_id
                WHERE p.produto_id = ?
                GROUP BY p.produto_id, p.nome, p.preco_venda, p.preco_custo, p.peso, p.categoria_id
                """;
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("produto_id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setPrecoVenda(rs.getDouble("preco_venda"));
                    produto.setPrecoCusto(rs.getDouble("preco_custo"));
                    produto.setPeso(rs.getDouble("peso"));
                    produto.setCategoriaId(rs.getInt("categoria_id"));

                    // quantidade total agregada a partir de estoque.quantidade
                    produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));

                    // carregar registros de estoque associados (lista detalhada)
                    EstoqueDAO estoqueDAO = new EstoqueDAO();
                    List<Estoque> estoques = estoqueDAO.listarPorProdutoId(produto.getId());
                    produto.setEstoques(estoques);

                    return produto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto no BD: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = """
            SELECT p.produto_id, p.nome, p.preco_venda, p.preco_custo, p.peso, p.categoria_id,
                   COALESCE(SUM(e.quantidade), 0) AS quantidade_estoque
            FROM produto p
            LEFT JOIN estoque e ON e.produto_id = p.produto_id
            GROUP BY p.produto_id, p.nome, p.preco_venda, p.preco_custo, p.peso, p.categoria_id
            ORDER BY p.produto_id
            """;
        try (Connection conn = ConexaoMySQL.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            EstoqueDAO estoqueDAO = new EstoqueDAO();
            while (rs.next()) {
                int id = rs.getInt("produto_id");
                String nome = rs.getString("nome");
                double precoVenda = rs.getDouble("preco_venda");
                double precoCusto = rs.getDouble("preco_custo");
                double peso = rs.getDouble("peso");
                int categoriaId = rs.getInt("categoria_id");
                int quantidadeEstoque = rs.getInt("quantidade_estoque");

                Produto p = new Produto(id, nome, precoVenda, precoCusto, peso, categoriaId);
                p.setQuantidadeEstoque(quantidadeEstoque);

                // carregar registros detalhados de estoque para o produto
                List<Estoque> estoques = estoqueDAO.listarPorProdutoId(id);
                p.setEstoques(estoques);

                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos no BD: " + e.getMessage());
            throw e;
        }
        return lista;
    }
}
