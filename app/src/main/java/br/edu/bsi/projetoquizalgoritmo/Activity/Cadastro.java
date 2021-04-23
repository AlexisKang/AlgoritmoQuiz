package br.edu.bsi.projetoquizalgoritmo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Pontos;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Usuario;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Cadastro extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;
    private ProgressBar pbCadastro;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();

        //Cadastrar Usuário
        pbCadastro.setVisibility(View.GONE);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!nome.isEmpty()){
                    if(!email.isEmpty()){
                        if(!senha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            cadastrarUsuario(usuario);

                        }else{
                            Toast.makeText(Cadastro.this, "Preencha o campo!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Cadastro.this, "Preencha o email!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Cadastro.this, "Preencha o campo!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void cadastrarUsuario(final Usuario usuario){
        pbCadastro.setVisibility(View.VISIBLE);
        autenticacao = ConfiguracaoFirebase.getReferenciaAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(
                this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            try {
                                pbCadastro.setVisibility(View.GONE);
                                //salvar dados do usuário
                                String idUsuario = task.getResult().getUser().getUid();

                                Pontos p = new Pontos();
                                p.setFacil(0);
                                p.setModerado(0);
                                p.setDificil(0);
                                usuario.setId(idUsuario);
                                usuario.setPontos(p);
                                usuario.salvar();

                                Toast.makeText(Cadastro.this,"Cadastro com sucesso" , Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            pbCadastro.setVisibility(View.GONE);
                            String erroExcecao ="";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e ){
                                erroExcecao = "Digite uma senha mais forte!";
                            } catch (FirebaseAuthInvalidCredentialsException e ){
                                erroExcecao = "Por favor, digite um email válido";
                            } catch (FirebaseAuthUserCollisionException e ){
                                erroExcecao = "Esta conta já foi cadastrada";
                            } catch (Exception e) {
                                erroExcecao = "Ao cadastrar usuário: " + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(Cadastro.this,"Erro: "+ erroExcecao, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    void inicializarComponentes() {
        edtEmail = findViewById(R.id.edtCadastroEmail);
        edtNome = findViewById(R.id.edtCadastroNome);
        edtSenha = findViewById(R.id.edtCadastroSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        pbCadastro = findViewById(R.id.pbCadastro);

        edtNome.requestFocus();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Vc quer realmente sair?");
        // alert.setMessage("Message");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(Cadastro.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();
    }
}
