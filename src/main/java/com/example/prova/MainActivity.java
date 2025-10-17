package com.example.prova;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtTitulo, edtDiretor, edtAno, edtGenero, edtNota;
    CheckBox checkBox;
    Button salvar;
    ListView listViewFilmes;
    BancoHelper databaseHelper;
    ArrayAdapter<String> adapter;
    ArrayList<String> listaFilmes;
    ArrayList<String> listaTitulos;

    private void carregarFilmes() {
        Cursor cursor = databaseHelper.listarFilmes();
        listaFilmes = new ArrayList<>();
        listaTitulos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(0);
                String diretor = cursor.getString(1);
                String ano = cursor.getString(2);
                String nota = cursor.getString(3);
                String genero = cursor.getString(4);
                listaFilmes.add(titulo + " - " + diretor + " - " + ano + " - " + nota + " - " + genero);
                listaTitulos.add(titulo);
            } while (cursor.moveToNext());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFilmes);
        listViewFilmes.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** EditText titulo = findViewById(R.id.edtTitulo);
        EditText Diretor = findViewById(R.id.edtDiretor);
        EditText ano = findViewById(R.id.edtAno);
        EditText genero = findViewById(R.id.edtGenero);
        EditText nota = findViewById(R.id.edtNota);

        titulo.setHint("Titulo");
        Diretor.setHint("Diretor");
        ano.setHint("Ano");
        genero.setHint("Genero");
        nota.setHint("Nota");

         **/

        try{
            edtTitulo = findViewById(R.id.edtTitulo);
            edtDiretor = findViewById(R.id.edtDiretor);
            edtAno = findViewById(R.id.edtAno);
            edtGenero = findViewById(R.id.edtGenero);
            edtNota = findViewById(R.id.edtNota);
            checkBox = findViewById(R.id.checkBox);
            salvar = findViewById(R.id.button);

            listViewFilmes = findViewById(R.id.edtLista);
            databaseHelper = new BancoHelper(this);

            salvar.setOnClickListener(v -> {
                String titulo = edtTitulo.getText().toString();
                String diretor = edtDiretor.getText().toString();
                String ano = edtAno.getText().toString();
                String genero = edtGenero.getText().toString();
                String nota = edtNota.getText().toString();

                if (!titulo.isEmpty() && !diretor.isEmpty()) {
                    long resultado = databaseHelper.inserirFilme(titulo, diretor, ano, nota, genero);
                    if (resultado != -1) {
                        Toast.makeText(this, "Filme salvo!", Toast.LENGTH_LONG).show();
                        edtTitulo.setText("");
                        edtDiretor.setText("");
                        edtAno.setText("");
                        edtGenero.setText("");
                        edtNota.setText("");
                        carregarFilmes();

                    } else {
                        Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                }
            });

            listViewFilmes.setOnItemClickListener((parent, view, position, id) -> {
                String titulo = listaTitulos.get(position);
                String diretor = listaFilmes.get(position).split(" - ")[1];
                String ano = listaFilmes.get(position).split(" - ")[2];
                String nota = listaFilmes.get(position).split(" - ")[3];
                String genero = listaFilmes.get(position).split(" - ")[4];
                edtTitulo.setText(titulo);
                edtDiretor.setText(diretor);
                edtAno.setText(ano);
                edtGenero.setText(genero);
                edtNota.setText(nota);
                salvar.setText("Atualizar");

                salvar.setOnClickListener(v ->
                {
                    String novoTitulo = edtTitulo.getText().toString();
                    String novoDiretor = edtDiretor.getText().toString();
                    String novoAno = edtAno.getText().toString();
                    String novoGenero = edtGenero.getText().toString();
                    String novaNota = edtNota.getText().toString();


                    if (!novoTitulo.isEmpty() && !novoDiretor.isEmpty()) {
                        int resultado = databaseHelper.atualizarFilme(titulo, diretor, ano, nota, genero);
                        if (resultado > 0) {
                            Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
                            carregarFilmes();
                            edtTitulo.setText("");
                            edtDiretor.setText("");
                            edtAno.setText("");
                            edtGenero.setText("");
                            edtNota.setText("");
                            salvar.setText("Salvar");

                        } else {
                            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                listViewFilmes.setOnItemLongClickListener((adapterView, view1, pos, l) -> {
                    String tituloFilmes = listaTitulos.get(pos);
                    int deletado = databaseHelper.excluirUsuario(tituloFilmes);
                    if (deletado > 0) {
                        Toast.makeText(this, "Usuário excluído!", Toast.LENGTH_SHORT).show();
                        carregarFilmes();
                    }
                    return true;
                });
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }
}