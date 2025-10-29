package ucb.estudo.dao;

import ucb.estudo.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO Categoria (nome, descricao) VALUES (?, ?)";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categoria SET nome = ?, descricao = ? WHERE categoria_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getCategoriaId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE categoria_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Categoria buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Categoria WHERE categoria_id = ?";
        try (Connection conn = ConexaoMySQL.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Categoria> listarTodos() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = ConexaoMySQL.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Categoria(
                        rs.getInt("categoria_id"),
                        rs.getString("nome"),
                        rs.getString("descricao")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lista;
    }
}
