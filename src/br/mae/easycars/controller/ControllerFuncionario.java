/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.controller;

import br.mae.easycars.model.dao.ClienteDao;
import br.mae.easycars.model.dao.UsuarioDao;
import br.mae.easycars.model.entidades.Cliente;
import br.mae.easycars.model.entidades.ClienteTableModel;
import br.mae.easycars.model.entidades.Usuario;
import br.mae.easycars.view.ViewAdicionarCliente;
import br.mae.easycars.view.ViewEditarCliente;
import br.mae.easycars.view.ViewListarClientes;
import br.mae.easycars.view.ViewLoginFuncionario;
import br.mae.easycars.view.ViewMenuFuncionario;
import br.mae.easycars.view.ViewPrincipal;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Cauã Freitas
 */
public class ControllerFuncionario implements KeyListener{

    private ViewPrincipal viewPrincipal;
    private ViewLoginFuncionario viewLoginFuncionario;
    private ViewMenuFuncionario viewMenuFuncionario;
    private ViewAdicionarCliente viewAdicionarCliente;
    private ViewListarClientes viewListarClientes;
    private ViewEditarCliente viewEditarCliente;
    private final UsuarioDao usuarioDao;
    private final ClienteDao clienteDao;
    private Cliente cliente;
    private final ClienteTableModel clienteTableModel;
    private List<Cliente> clientes;

    public ControllerFuncionario(ViewPrincipal viewPrincipal) {
        this.viewPrincipal = viewPrincipal;
        this.usuarioDao = new UsuarioDao();
        this.clienteDao = new ClienteDao();
        this.clienteTableModel = new ClienteTableModel();
        this.viewPrincipal.getBtnFuncionario().addActionListener(e -> abrirTelaLogin());
    }

    public ControllerFuncionario() {
        this.usuarioDao = new UsuarioDao();
        this.clienteDao = new ClienteDao();
        this.clienteTableModel = new ClienteTableModel();
    }

    protected void abrirTelaLogin() {
        viewPrincipal.dispose();
        viewLoginFuncionario = new ViewLoginFuncionario();
        viewLoginFuncionario.getBtnEntrar().addActionListener(e -> autenticar());
        viewLoginFuncionario.getBtnVoltar().addActionListener(e -> abrirTela(viewPrincipal, viewLoginFuncionario));
        viewLoginFuncionario.setVisible(true);
    }

    protected void autenticar() {
        String usuario = this.viewLoginFuncionario.getTxtLogin().getText();
        String senha = new String(this.viewLoginFuncionario.getTxtSenha().getPassword());

        boolean usuarioAutenticado = usuarioDao.buscar(usuario, senha, Usuario.papel.funcionario);

        if (usuario.equals("") || senha.equals("")) {
            JOptionPane.showMessageDialog(viewLoginFuncionario, "Preencha todos os campos.", "EasyCars", JOptionPane.WARNING_MESSAGE);
        } else {
            if (usuarioAutenticado) {
                viewLoginFuncionario.dispose();
                viewMenuFuncionario = new ViewMenuFuncionario();
                viewMenuFuncionario.getBtnAddCliente().addActionListener(e -> abrirTelaAdicionarCliente(viewMenuFuncionario));
                viewMenuFuncionario.getBtnListarClientes().addActionListener(e -> abrirTelaListarClientes(viewMenuFuncionario));
                viewMenuFuncionario.getBtnVoltar().addActionListener(e -> abrirTela(viewPrincipal, viewMenuFuncionario));
                viewMenuFuncionario.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(viewLoginFuncionario, "Login ou senha incorretos.", "EasyCars", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    protected void abrirTelaAdicionarCliente(JFrame viewMenu) {
        viewAdicionarCliente = new ViewAdicionarCliente();
        viewAdicionarCliente.getBtnSalvar().addActionListener(e -> adicionarCliente());
        viewAdicionarCliente.getBtnListar().addActionListener(e -> abrirTelaListarClientes(viewMenu));
        viewAdicionarCliente.getBtnListar().addActionListener(e -> viewAdicionarCliente.dispose());
        viewAdicionarCliente.getBtnVoltar().addActionListener(e -> abrirTela(viewMenu, viewAdicionarCliente));
        abrirTela(viewAdicionarCliente, viewMenu);
    }

    protected void adicionarCliente() {
        String nome = viewAdicionarCliente.getTxtNome().getText();
        String email = viewAdicionarCliente.getTxtEmail().getText();
        String cpf = viewAdicionarCliente.getTxtCpf().getText();
        cpf = cpf.replace(".", "").replace("-", "");
        String rg = viewAdicionarCliente.getTxtRg().getText();
        rg = rg.replace(".", "").replace("-", "");
        String telefone = viewAdicionarCliente.getTxtTelefone().getText();
        telefone = telefone.replace("(", "").replace(")", "").replace("-", "");
        String cidade = viewAdicionarCliente.getTxtCidade().getText();
        String estado = String.valueOf(viewAdicionarCliente.getCbEstado().getSelectedItem());
        String cnh = viewAdicionarCliente.getTxtCnh().getText();

        if (nome.equals("") || email.equals("") || cpf.equals("") || rg.equals("") || telefone.equals("") || cidade.equals("") || estado.equals("") || cnh.equals("")) {
            JOptionPane.showMessageDialog(viewAdicionarCliente, "Preencha todos os campos.", "EasyCars", JOptionPane.WARNING_MESSAGE);
        } else {
            cliente = new Cliente(nome, email, cpf, rg, telefone, cidade, estado, cnh);
            clienteDao.adicionar(cliente);
        }
    }

    protected void abrirTelaListarClientes(JFrame viewMenu) {
        viewListarClientes = new ViewListarClientes();
        atualizarTabela();

        viewListarClientes.getTxtPesquisa().addKeyListener(this);
        viewListarClientes.getBtnExcluirCliente().addActionListener(e -> excluirCliente());
        viewListarClientes.getBtnEditarCliente().addActionListener(e -> selecionarClienteParaEdicao());
        viewListarClientes.getBtnVoltar().addActionListener(e -> abrirTela(viewMenu, viewListarClientes));
        abrirTela(viewListarClientes, viewMenu);
    }

    protected void excluirCliente() {
        int linha = viewListarClientes.getTbClientes().getSelectedRow();
        cliente = clienteTableModel.getCliente(linha);
        int id = cliente.getId();

        int select = JOptionPane.showConfirmDialog(viewListarClientes, "Excluir cliente " + cliente.getNome() + "?", "EasyCars", JOptionPane.YES_OPTION);
        if (select == 0) {
            clienteTableModel.removerLinha(linha);
            clienteDao.excluir(id);
        }

        atualizarTabela();
    }

    protected void selecionarClienteParaEdicao() {
        viewEditarCliente = new ViewEditarCliente();
        abrirTela(viewEditarCliente, viewListarClientes);

        int linha = viewListarClientes.getTbClientes().getSelectedRow();
        cliente = clienteTableModel.getCliente(linha);
        int id = cliente.getId();

        viewEditarCliente.getLblId().setText("Cliente ID: " + String.valueOf(id));

        viewEditarCliente.getTxtNome().setText(cliente.getNome());
        viewEditarCliente.getTxtEmail().setText(cliente.getEmail());
        viewEditarCliente.getTxtCpf().setText(cliente.getCpf());
        viewEditarCliente.getTxtRg().setText(cliente.getRg());
        viewEditarCliente.getTxtTelefone().setText(cliente.getTelefone());
        viewEditarCliente.getTxtCidade().setText(cliente.getCidade());
        viewEditarCliente.getCbEstado().setSelectedItem(cliente.getEstado());
        viewEditarCliente.getTxtCnh().setText(cliente.getCnh());

        viewEditarCliente.getBtnSalvar().addActionListener(e -> atualizarCliente(id));
        viewEditarCliente.getBtnVoltar().addActionListener(e -> abrirTela(viewListarClientes, viewEditarCliente));
    }

    protected void atualizarCliente(int id) {
        String nome = viewEditarCliente.getTxtNome().getText();
        String email = viewEditarCliente.getTxtEmail().getText();
        String cpf = viewEditarCliente.getTxtCpf().getText();
        cpf = cpf.replace(".", "").replace("-", "");
        String rg = viewEditarCliente.getTxtRg().getText();
        rg = rg.replace(".", "").replace("-", "");
        String telefone = viewEditarCliente.getTxtTelefone().getText();
        telefone = telefone.replace("(", "").replace(")", "").replace("-", "");
        String cidade = viewEditarCliente.getTxtCidade().getText();
        String estado = String.valueOf(viewEditarCliente.getCbEstado().getSelectedItem());
        String cnh = viewEditarCliente.getTxtCnh().getText();

        cliente = new Cliente(nome, email, cpf, rg, telefone, cidade, estado, cnh);
        clienteDao.atualizar(cliente, id);

        atualizarTabela();
    }

    protected void atualizarTabela() {
        clientes = clienteDao.listar();
        clienteTableModel.adicionarClientes(clientes);
        viewListarClientes.getTbClientes().setModel(clienteTableModel);
    }

    protected void abrirTela(JFrame to, JFrame from) {
        if (to.getTitle().equals("EasyCars") && from.getTitle().equals("Home - Funcionário - EasyCars")
                || to.getTitle().equals("EasyCars") && from.getTitle().equals("Home - Gerente - EasyCars")) {
            int select = JOptionPane.showConfirmDialog(from, "Fazer logout?", "EasyCars", JOptionPane.YES_OPTION);
            if (select == 0) {
                to.setVisible(true);
                from.dispose();
            }
        } else {
            to.setVisible(true);
            from.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        String pesquisa = viewListarClientes.getTxtPesquisa().getText();
        System.out.println(pesquisa);
    }
}
