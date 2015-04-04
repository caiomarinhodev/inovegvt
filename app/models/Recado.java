package models;

import javax.persistence.*;

/**
 * Created by Caio on 04/04/2015.
 */
@Entity
@Table(name = "RECADO")
public class Recado {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long idAutor;
    @Column
    private Long idReceptor;
    @Lob
    private String recado;
    @Column
    private String titulo;
    @Column
    private int status;
    @Column
    private int tipo;

    public Recado() {

    }

    public Recado(Long idAutor, Long idReceptor, String titulo, String recado, int status, int tipo) {
        this.idAutor = idAutor;
        this.idReceptor = idReceptor;
        this.titulo = titulo;
        this.recado = recado;
        this.status = status;
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public Long getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(Long idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getRecado() {
        return recado;
    }

    public void setRecado(String recado) {
        this.recado = recado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
