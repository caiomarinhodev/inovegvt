package models;

import javax.persistence.*;

/**
 * Created by Caio on 29/03/2015.
 */
@Entity
@Table(name = "PLANO")
public class Plano {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String text;
    @Column
    private double value;
    @Column
    private int tipo;

    public Plano() {

    }

    public Plano(String text, double value, int tipo) {
        this.text = text;
        this.value = value;
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
