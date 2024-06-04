package br.unipar;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static br.unipar.ATIVIDADE_EXTRA_JBDC.*;

public class ATIVIDADE_EXTRA_JBDCTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        criarTabelaUsuario();

        System.out.println("Inserindo usuário...");
        System.out.print("Digite o username: ");
        String username = scanner.nextLine();
        System.out.print("Digite o password: ");
        String password = scanner.nextLine();
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a data de nascimento (AAAA-MM-DD): ");
        Date nascimento = Date.valueOf(scanner.nextLine());

        inserirUsuarioComVerificacao(username, password, nome, nascimento);

        System.out.println("Listando usuários...");
        listarUsuarios();

        System.out.println("Atualizando usuário...");
        System.out.print("Digite o código do usuário a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Digite o novo username: ");
        String novoUsername = scanner.nextLine();
        System.out.print("Digite o novo password: ");
        String novoPassword = scanner.nextLine();
        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();
        System.out.print("Digite a nova data de nascimento (AAAA-MM-DD): ");
        Date novaDataNascimento = Date.valueOf(scanner.nextLine());

        atualizarUsuario(codigo, novoUsername, novoPassword, novoNome, novaDataNascimento);

        System.out.println("Listando usuários após atualização...");
        listarUsuarios();

        System.out.println("Deletando usuário...");
        System.out.print("Digite o código do usuário a ser deletado: ");
        int codigoDeletar = scanner.nextInt();
        deletarUsuario(codigoDeletar);

        System.out.println("Listando usuários após exclusão...");
        listarUsuarios();
    }

    public static void inserirUsuarioComVerificacao(String username, String password, String nome, Date nascimento) {
        try {
            // Verifica se o usuário já existe na tabela
            if (!verificarUsuarioExistente(username)) {
                // Se o usuário não existir, insere o novo usuário
                inserirUsuario(username, password, nome, nascimento);
                System.out.println("Usuário inserido com sucesso!");
            } else {
                System.out.println("Usuário com nome de usuário '" + username + "' já existe.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean verificarUsuarioExistente(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
        try (PreparedStatement pstmt = connection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
