/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.mae.easycars.model.entidades;

/**
 *
 * @author Guilherme Vieira
 */
public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String rg;
    private String telefone;
    private String cidade;
    private String estado;
    private String cnh;

    public Cliente(String nome, String email, String cpf, String rg, String telefone, String cidade, String estado, String cnh) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
        this.telefone = telefone;
        this.cidade = cidade;
        this.estado = estado;
        this.cnh = cnh;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }
}
