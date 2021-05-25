/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.model.dao;

import br.mae.easycars.connection.ConnectionFactory;
import br.mae.easycars.model.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Isac Paz
 */
public class UsuarioDao {
    private final Connection conexao;
    
    public UsuarioDao(){
        this.conexao = new ConnectionFactory().getConnection();
    }
    
    public boolean buscar(String login, String senha, Usuario.papel papel){
        String sql = "select * from usuario where login = ? and senha = ? and papel = ?";
        
        try{
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2,senha);
            ps.setString(3, papel.toString());
            
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
