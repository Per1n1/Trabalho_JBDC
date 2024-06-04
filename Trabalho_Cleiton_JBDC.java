package br.unipar;

import java.sql.*;

public class ATIVIDADE_4_JBDC {
    private static final String url = "jdbc:postgresql://localhost:5432/Exemplo 1";
    private static final String user = "postgres";
    private static final String password = "admin123";

    public static void main(String[] args) {
        criarTabelaUsuario();
        listarUsuarios();
    }

    public static Connection connection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void criarTabelaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                + "codigo SERIAL PRIMARY KEY, "
                + "username VARCHAR(50) UNIQUE NOT NULL, "
                + "password VARCHAR(300) NOT NULL, "
                + "nome VARCHAR(50) NOT NULL, "
                + "nascimento DATE)";
        executarUpdate(sql);
    }

    public static void inserirUsuario(String username, String password, String nome, Date nascimento) {
        String sql = "INSERT INTO usuarios (username, password, nome, nascimento) VALUES (?, ?, ?, ?)";
        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, nome);
            pstmt.setDate(4, nascimento);
            pstmt.executeUpdate();
            System.out.println("Usuário inserido!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void listarUsuarios() {
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = connection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Codigo: %d, Username: %s, Password: %s, Nome: %s, Nascimento: %s%n",
                        rs.getInt("codigo"), rs.getString("username"), rs.getString("password"), rs.getString("nome"), rs.getDate("nascimento"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void deletarUsuario(int codigo) {
        String sql = "DELETE FROM usuarios WHERE codigo = ?";
        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            pstmt.executeUpdate();
            System.out.println("Usuário deletado!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void executarUpdate(String sql) {
        try (Connection conn = connection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Operação realizada!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void atualizarUsuario(int i, String Renanperini) {
    }
}
