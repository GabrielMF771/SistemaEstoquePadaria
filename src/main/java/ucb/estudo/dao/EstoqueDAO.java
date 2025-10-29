// language: java
package ucb.estudo.dao;

import ucb.estudo.model.Estoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public void inserir(Estoque estoque) throws SQLException {
        String sql = "INSERT INTO estoque (quantidade, produto_id) VALUES (?, ?)";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estoque.getQuantidade());
            stmt.setInt(2, estoque.getProdutoId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Estoque estoque) throws SQLException {
        String sql = "UPDATE estoque SET quantidade = ?, produto_id = ? WHERE estoque_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, estoque.getQuantidade());
            stmt.setInt(2, estoque.getProdutoId());
            stmt.setInt(3, estoque.getEstoqueId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM estoque WHERE estoque_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Estoque buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM estoque WHERE estoque_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Estoque(
                            rs.getInt("estoque_id"),
                            rs.getInt("quantidade"),
                            rs.getInt("produto_id")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Estoque> listarPorProdutoId(int produtoId) throws SQLException {
        List<Estoque> lista = new ArrayList<>();
        String sql = "SELECT estoque_id, quantidade, produto_id FROM estoque WHERE produto_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produtoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estoque e = new Estoque();
                    e.setEstoqueId(rs.getInt("estoque_id"));
                    e.setQuantidade(rs.getInt("quantidade"));
                    e.setProdutoId(rs.getInt("produto_id"));
                    lista.add(e);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar estoques: " + e.getMessage());
            throw e;
        }
        return lista;
    }

    public List<Estoque> listarTodos() throws SQLException {
        List<Estoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM estoque";
        try (Connection conn = ConexaoMySQL.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Estoque(
                        rs.getInt("estoque_id"),
                        rs.getInt("quantidade"),
                        rs.getInt("produto_id")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
