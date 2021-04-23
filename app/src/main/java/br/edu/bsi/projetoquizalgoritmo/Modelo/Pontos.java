package br.edu.bsi.projetoquizalgoritmo.Modelo;

public class Pontos {
    private int facil, moderado, dificil, sobrevivencia;

    public Pontos(){

    }

    public int getFacil() {
        return facil;
    }

    public void setFacil(int facil) {
        this.facil = facil;
    }

    public int getModerado() {
        return moderado;
    }

    public void setModerado(int moderado) {
        this.moderado = moderado;
    }

    public int getDificil() {
        return dificil;
    }

    public void setDificil(int dificil) {
        this.dificil = dificil;
    }

    public int getSobrevivencia() {
        return sobrevivencia;
    }

    public void setSobrevivencia(int sobrevivencia) {
        this.sobrevivencia = sobrevivencia;
    }

    @Override
    public String toString() {
        return "Pontos{" +
                "facil=" + facil +
                ", moderado=" + moderado +
                ", dificil=" + dificil +
                ", sobrevivencia=" + sobrevivencia +
                '}';
    }
}
