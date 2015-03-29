package models;

import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Caio on 28/03/2015.
 */
@Entity
@Table(name = "CHAMADA")
public class Chamada {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long idCliente;
    @Column
    private String nota;
    @Column
    private String horainicial;
    @Column
    private String horafinal;
    @Column
    private int status;
    @Column
    private Long idOperador;
    @Column
    private GregorianCalendar data;
    @Column
    private String dataAgendamento;
    @Column
    private String horaAgendamento;


    public Chamada(){

    }

    public Chamada(Cliente cliente, String nota, String horainicial, String horafinal,
                   int status, Usuario operador, GregorianCalendar data, String dataAgendamento, String horaAgendamento) {
        this.idCliente = cliente.getId();
        this.idOperador = operador.getId();
        this.nota = nota;
        this.horainicial = horainicial;
        this.horafinal = horafinal;
        this.status = status;
        this.data = data;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getHoraAgendamento() {
        return horaAgendamento;
    }

    public void setHoraAgendamento(String horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getHorainicial() {
        return horainicial;
    }

    public void setHorainicial(String horainicial) {
        this.horainicial = horainicial;
    }

    public String getHorafinal() {
        return horafinal;
    }

    public void setHorafinal(String horafinal) {
        this.horafinal = horafinal;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Long idOperador) {
        this.idOperador = idOperador;
    }

    public GregorianCalendar getData() {
        return data;
    }

    public void setData(GregorianCalendar data) {
        this.data = data;
    }
}
