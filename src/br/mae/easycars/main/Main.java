/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.main;

import br.mae.easycars.controller.ControllerFuncionario;
import br.mae.easycars.controller.ControllerGerente;
import br.mae.easycars.view.ViewPrincipal;

/**
 *
 * @author Isac Paz
 */
public class Main {

    public static void main(String[] args) {
        ViewPrincipal viewPrincipal = new ViewPrincipal();
        ControllerGerente controllerGerente = new ControllerGerente(viewPrincipal);
        ControllerFuncionario controllerFuncionario = new ControllerFuncionario(viewPrincipal);
        viewPrincipal.setVisible(true);
    }
}
