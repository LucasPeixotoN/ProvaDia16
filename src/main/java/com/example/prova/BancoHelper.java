package com.example.prova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "meubanco.db";
    private static final int DATABASE_VERSION = 1;
    // Nome da tabela e colunas – Para o caso de tabela única
    private static final String TABLE_FILME = "filme";
    private static final String COLUMN_TITULO  = "Titulo";
    private static final String COLUMN_DIRETOR = "Diretor";
    private static final String COLUMN_ANO = "Ano";
    private static final String COLUMN_NOTA = "Nota";
    private static final String COLUMN_GENERO = "Genero";

    public BancoHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_FILME + "(" + COLUMN_TITULO + " TEXT, " + COLUMN_DIRETOR + ", " + COLUMN_ANO + " TEXT, " + COLUMN_NOTA + " TEXT, " + COLUMN_GENERO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILME);
        onCreate(db);
    }

    public long inserirFilme(String titulo, String diretor, String ano, String nota, String genero)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO , titulo);
        values.put(COLUMN_DIRETOR, diretor);
        values.put(COLUMN_ANO, ano);
        values.put(COLUMN_NOTA, nota);
        values.put(COLUMN_GENERO, genero);

        return db.insert(TABLE_FILME, null, values);
    }

    public Cursor listarFilmes()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FILME, null);
    }

    public int atualizarFilme(String titulo, String diretor, String ano, String nota, String genero)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO , titulo);
        values.put(COLUMN_DIRETOR, diretor);
        values.put(COLUMN_ANO, ano);
        values.put(COLUMN_NOTA, nota);
        values.put(COLUMN_GENERO, genero);
        return db.update(TABLE_FILME, values, COLUMN_TITULO + "=?", new String[]{String.valueOf(titulo)});
    }

    public int excluirUsuario(String titulo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FILME, COLUMN_TITULO + "=?", new String[]{String.valueOf(titulo)});
    }





}
