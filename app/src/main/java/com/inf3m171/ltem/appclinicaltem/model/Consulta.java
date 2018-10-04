package com.inf3m171.ltem.appclinicaltem.model;

public class Consulta {
    private String id, nome, data, horario, medico, idUsuario;

    @Override
    public String toString(){return nome;}


    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getData () {return data;}

    public void setData(String data) {this.data = data;}

    public String getHorario () {return horario;}

    public void setHorario(String horario) {this.horario = horario;}

    public String getMedico() {return medico;}

    public void setMedico (String medico) {this.medico = medico;}

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
