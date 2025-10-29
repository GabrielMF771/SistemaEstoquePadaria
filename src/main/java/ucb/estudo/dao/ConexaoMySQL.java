package ucb.estudo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/padaria";
    private static final String USUARIO = "root";
    private static final String SENHA = "gabriel";

    public static Connection obterConexao(){
        try {
            // Retorna a conexão
            Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);

            return conexao;
        } catch (SQLException e) {
            throw new RuntimeException("Falha na conexão com o Banco de Dados: " + e.getMessage(), e);
        }
    }

    public static void fecharConexao(Connection conexao) {
        if(conexao != null){
            try {
                conexao.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
