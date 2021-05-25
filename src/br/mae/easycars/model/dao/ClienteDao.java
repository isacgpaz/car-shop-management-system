/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.model.dao;

import br.mae.easycars.connection.ConnectionFactory;
import br.mae.easycars.model.entidades.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Erick
 */
public class ClienteDao {

    private final Connection conexao;

    public ClienteDao() {
        this.conexao = new ConnectionFactory().getConnection();
    }

    public void adicionar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, email, cpf, rg, telefone, cidade, estado, cnh) VALUES (?,?,?,?,?,?,?,?)";

        try {
            try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getEmail());
                ps.setString(3, cliente.getCpf());
                ps.setString(4, cliente.getRg());
                ps.setString(5, cliente.getTelefone());
                ps.setString(6, cliente.getCidade());
                ps.setString(7, cliente.getEstado());
                ps.setString(8, cliente.getCnh());

                ps.execute();

                JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso.", "EasyCars", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Cliente> listar() {
        String sql = "SELECT * FROM cliente";

        try {
            List<Cliente> clientes;
            try (PreparedStatement ps = this.conexao.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                clientes = new ArrayList<>();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setRg(rs.getString("rg"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setCidade(rs.getString("cidade"));
                    cliente.setEstado(rs.getString("estado"));
                    cliente.setCnh(rs.getString("cnh"));

                    clientes.add(cliente);
                }

                return clientes;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try {
            try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
                ps.setInt(1, id);

                ps.execute();

                JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.", "EasyCars", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void atualizar(Cliente cliente, int id) {
        String sql = "UPDATE cliente SET nome = ?, email = ?, cpf = ?, rg = ?, telefone = ?, cidade = ?,  estado = ?, cnh = ? where id = ?";

        try {
            try (PreparedStatement ps = this.conexao.prepareStatement(sql)) {
                ps.setString(1, cliente.getNome());
                ps.setString(2, cliente.getEmail());
                ps.setString(3, cliente.getCpf());
                ps.setString(4, cliente.getRg());
                ps.setString(5, cliente.getTelefone());
                ps.setString(6, cliente.getCidade());
                ps.setString(7, cliente.getEstado());
                ps.setString(8, cliente.getCnh());
                ps.setInt(9, id);

                ps.execute();

                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso.", "EasyCars", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
