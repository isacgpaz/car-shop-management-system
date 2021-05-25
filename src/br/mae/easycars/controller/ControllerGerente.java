/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.controller;

import br.mae.easycars.model.dao.UsuarioDao;
import br.mae.easycars.model.entidades.Usuario;
import br.mae.easycars.view.ViewLoginGerente;
import br.mae.easycars.view.ViewMenuGerente;
import br.mae.easycars.view.ViewPrincipal;
import javax.swing.JOptionPane;

/**
 *
 * @author Isac Paz
 */
public class ControllerGerente {

    private final ControllerFuncionario controllerFuncionario;
    private final ViewPrincipal viewPrincipal;
    private ViewLoginGerente viewLoginGerente;
    private ViewMenuGerente viewMenuGerente;
    private final UsuarioDao usuarioDao;

    public ControllerGerente(ViewPrincipal viewPrincipal) {
        this.viewPrincipal = viewPrincipal;
        this.usuarioDao = new UsuarioDao();
        this.controllerFuncionario = new ControllerFuncionario();
        this.viewPrincipal.getBtnGerente().addActionListener(e -> abrirTelaLogin());
    }

    public void abrirTelaLogin() {
        viewPrincipal.dispose();
        viewLoginGerente = new ViewLoginGerente();
        viewLoginGerente.getBtnEntrar().addActionListener(e -> autenticar());
        viewLoginGerente.getBtnVoltar().addActionListener(e -> controllerFuncionario.abrirTela(viewPrincipal, viewLoginGerente));
        viewLoginGerente.setVisible(true);
    }

    public void autenticar() {
        String login = viewLoginGerente.getTxtLogin().getText();
        String senha = String.valueOf(viewLoginGerente.getTxtSenha().getPassword());

        boolean usuarioAutenticado = usuarioDao.buscar(login, senha, Usuario.papel.gerente);

        if (login.equals("") || senha.equals("")) {
            JOptionPane.showMessageDialog(viewLoginGerente, "Preencha todos os campos.", "EasyCars", JOptionPane.WARNING_MESSAGE);
        } else {
            if (usuarioAutenticado) {
                viewLoginGerente.dispose();
                viewMenuGerente = new ViewMenuGerente();
                viewMenuGerente.getBtnAddCliente().addActionListener(e -> controllerFuncionario.abrirTelaAdicionarCliente(viewMenuGerente));
                viewMenuGerente.getBtnListarClientes().addActionListener(e -> controllerFuncionario.abrirTelaListarClientes(viewMenuGerente));
                viewMenuGerente.getBtnVoltar().addActionListener(e -> controllerFuncionario.abrirTela(viewPrincipal, viewMenuGerente));
                viewMenuGerente.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(viewLoginGerente, "Login ou senha incorretos.", "EasyCars", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
