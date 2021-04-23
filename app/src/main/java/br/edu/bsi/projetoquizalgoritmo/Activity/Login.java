package br.edu.bsi.projetoquizalgoritmo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Usuario;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Login extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar;
    private ProgressBar pbLogin;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificacaoUsuarioLogado();
        inicializarComponentes();

        //Login de Usuário
        pbLogin.setVisibility(View.GONE);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);
                        validarLogin(usuario);
                    }else{
                        Toast.makeText(Login.this, "Preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Preencha o email!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void abrirCadastro(View view) {
        Intent cadastroIntent = new Intent(Login.this, Cadastro.class);
        startActivity(cadastroIntent);
    }

    void inicializarComponentes() {
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        pbLogin = findViewById(R.id.pbLogin);

        edtEmail.requestFocus();
    }

    void verificacaoUsuarioLogado(){
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    void validarLogin(Usuario usuario){

        pbLogin.setVisibility(View.VISIBLE);
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();

        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    pbLogin.setVisibility(View.GONE);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(Login.this,"Erro ao fazer Login",Toast.LENGTH_SHORT).show();
                    pbLogin.setVisibility(View.GONE);
                }
            }
        });
    }
}