/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.connection;

// conexão SQL para Java 
import java.sql.Connection; 
// driver de conexão SQL para Java 
import java.sql.DriverManager; 
// classe para tratamento de exceções
import java.sql.SQLException; 

/**
 *
 * @author Gabriel Soares
 */
public class ConnectionFactory{
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/concessionaria","root","");
        }         
        catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}   