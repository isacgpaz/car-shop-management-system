/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.model.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gabriel Soares
 */
public class ClienteTableModel extends AbstractTableModel {

    private List<Cliente> clientes;
    private final List<String> colunas;
    private Cliente cliente;

    public ClienteTableModel() {
        this.clientes = new ArrayList<>();
        this.colunas = Arrays.asList("Nome", "Email", "CPF", "RG", "Telefone", "Cidade", "Estado", "CNH");
    }

    public void adicionarClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public String getColumnName(int column) {
        return this.colunas.get(column);
    }

    @Override
    public int getRowCount() {
        return this.clientes.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return this.clientes.get(row).getNome();
            case 1:
                return this.clientes.get(row).getEmail();
            case 2:
                return this.clientes.get(row).getCpf();
            case 3:
                return this.clientes.get(row).getRg();
            case 4:
                return this.clientes.get(row).getTelefone();
            case 5:
                return this.clientes.get(row).getCidade();
            case 6:
                return this.clientes.get(row).getEstado();
            case 7:
                return this.clientes.get(row).getCnh();
            default:
                throw new ArrayIndexOutOfBoundsException("Não existe esta coluna na tabela clientes");
        }
    }

    public Cliente getCliente(int row) {
        cliente = new Cliente();
        cliente.setId(this.clientes.get(row).getId());
        cliente.setNome(this.clientes.get(row).getNome());
        cliente.setEmail(this.clientes.get(row).getEmail());
        cliente.setCpf(this.clientes.get(row).getCpf());
        cliente.setRg(this.clientes.get(row).getRg());
        cliente.setTelefone(this.clientes.get(row).getRg());
        cliente.setCidade(this.clientes.get(row).getCidade());
        cliente.setEstado(this.clientes.get(row).getEstado());
        cliente.setCnh(this.clientes.get(row).getCnh());

        return cliente;
    }

    public void removerLinha(int row) {
        this.clientes.remove(clientes.get(row));
        this.fireTableDataChanged();
    }
}
