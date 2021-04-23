package br.edu.bsi.projetoquizalgoritmo.Modelo;

public class Medalhas {
    private String nome;
    private String descricao;
    private int imagem;

    public Medalhas(){

    }
    public Medalhas(String nome, String descricao,int imagem){
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Medalhas{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", imagem=" + imagem +
                '}';
    }
}
