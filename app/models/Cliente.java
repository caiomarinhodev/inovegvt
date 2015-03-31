package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caio on 28/03/2015.
 */
@Entity
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String numero1;
    @Column
    private String numero2;
    @Column
    private String celular;
    @OneToMany
    private List<Chamada> listaDeChamadas;
    @Column
    private String endereco;
    @Column
    private String cidade;
    @Column
    private String uf;
    @Column
    private String planoGVT;
    @Column
    private String cpf;
    @Column
    private String rg;
    @Column
    private String cep;

    public Cliente() {

    }

    public Cliente(String nome, String numero1,
                   String numero2, String celular, String endereco,
                   String cidade, String uf, String planoGVT, String cpf, String rg, String cep) {
        this.nome = nome;
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.celular = celular;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.planoGVT = planoGVT;
        this.cpf = cpf;
        this.rg = rg;
        this.listaDeChamadas = new ArrayList<>();
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }

    public String getNumero2() {
        return numero2;
    }

    public void setNumero2(String numero2) {
        this.numero2 = numero2;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public List<Chamada> getListaDeChamadas() {
        return listaDeChamadas;
    }

    public void setListaDeChamadas(List<Chamada> listaDeChamadas) {
        this.listaDeChamadas = listaDeChamadas;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getPlanoGVT() {
        return planoGVT;
    }

    public void setPlanoGVT(String planoGVT) {
        this.planoGVT = planoGVT;
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
}
