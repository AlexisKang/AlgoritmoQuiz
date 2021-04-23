package br.edu.bsi.projetoquizalgoritmo.Modelo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.Comparator;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;

public class Usuario implements Comparable<Usuario>{
    private String id;
    private String nome;
    private String email;
    private String senha;
    private Pontos pontos;

    public Usuario(){

    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(getId());
        usuarioRef.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Pontos getPontos() {
        return pontos;
    }

    public void setPontos(Pontos pontos) {
        this.pontos = pontos;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public static Comparator<Usuario> Pontuacao = new Comparator<Usuario>() {
        @Override
        public int compare(Usuario u1, Usuario u2) {
            return - Integer.valueOf(u1.getPontos().getSobrevivencia()).compareTo(Integer.valueOf(u2.getPontos().getSobrevivencia()));
        }
    };


    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", pontos=" + pontos +
                '}';
    }

    @Override
    public int compareTo(Usuario usuario) {
        return 0;
    }
}
