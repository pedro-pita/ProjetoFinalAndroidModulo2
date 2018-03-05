package com.zicronofandroid.projetofinalmodulo2android.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.zicronofandroid.projetofinalmodulo2android.model.Clientes;
import com.zicronofandroid.projetofinalmodulo2android.model.Imoveis;
import com.zicronofandroid.projetofinalmodulo2android.model.Users;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DataBaseHelper.class.getSimpleName();
    public static final String DB_NAME = "imoveisDB";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_LEVEL = "level";

    public static final String IMOVEL_TABLE = "imoveis";
    public static final String IMOVEL_ID = "id";
    public static final String IMOVEL_DESCRICAO = "descricao";
    public static final String IMOVEL_TIPOLOGIA = "tipologia";
    public static final String IMOVEL_LOCALIZACAO = "localizacao";
    public static final String IMOVEL_URL = "url_foto";

    private static final String CLIENT_TABLE = "clientes";
    private static final String CLIENT_ID = "id";
    private static final String CLIENT_NAME = "nome";
    private static final String CLIENT_AGE = "idade";
    private static final String CLIENT_URL = "url_foto";

    private static final String LOG = "DatabaseHelper";

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_LOGIN + " TEXT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PASS + " TEXT,"
            + COLUMN_LEVEL + " TEXT);";

    public static final String CREATE_TABLE_ClIENT = "CREATE TABLE " + CLIENT_TABLE + "("
            + CLIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CLIENT_NAME + " TEXT,"
            + CLIENT_AGE + " TEXT,"
            + CLIENT_URL + " TEXT);";

    public static final String CREATE_TABLE_IMOVEIS = "CREATE TABLE " + IMOVEL_TABLE + "("
            + IMOVEL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + IMOVEL_DESCRICAO + " TEXT,"
            + IMOVEL_TIPOLOGIA + " TEXT,"
            + IMOVEL_LOCALIZACAO + " TEXT,"
            + IMOVEL_URL + " TEXT);";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_IMOVEIS);
        db.execSQL(CREATE_TABLE_ClIENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + IMOVEL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CLIENT_TABLE);
        onCreate(db);
    }

    /*public void addUser(String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_LEVEL, 3); //level de cliente
        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "Utilizador inserido" + id);
    }*/
    public void addUser(String login, String password, String name, String level) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LOGIN, login);
        values.put(COLUMN_PASS, password);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LEVEL, level);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "Utilizador inserido" + id);
    }

    public boolean getUser(String login, String pass){
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM  " + USER_TABLE + " WHERE " +
                COLUMN_LOGIN + " = " + "'"+login+"'" + " AND " + COLUMN_PASS + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    public Users getUserById(String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_ID + " = " + user_id;
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Users user = new Users();
        user.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)));
        user.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASS)));
        user.setLevel(cursor.getString(cursor.getColumnIndex(COLUMN_LEVEL)));
        return user;
    }

    public boolean verifyUserByRegiste(String login) {
        String selectQuery = "SELECT * FROM  " + USER_TABLE + " WHERE " +
                COLUMN_LOGIN + " = " + "'" + login + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
    public Users getUserByLogin(String login) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_LOGIN + " = " + "'"+login+"'";
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Users user = new Users();
        user.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)));
        return user;
    }
    public Users getUserToEdit(String login) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_LOGIN + " = " + "'"+login+"'";
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Users user = new Users();
        user.setLogin(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)));
        user.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASS)));
        user.setLevel(cursor.getString(cursor.getColumnIndex(COLUMN_LEVEL)));
        user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        return user;
    }
    public boolean updateUser(String oldLogin, String login, String password, String name, String level){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "UPDATE " + USER_TABLE
                + " SET " + COLUMN_LOGIN
                + " = " + "'"+login+"'"
                + ", " + COLUMN_NAME
                + " = " + "'"+name+"'"
                + ", " + COLUMN_PASS
                + " = " + "'"+password+"'"
                + ", " + COLUMN_LEVEL
                + " = " + "'"+level+"'"
                + " WHERE " + COLUMN_LOGIN
                + " = " + "'"+oldLogin+"'";
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return true;
    }
    public List<Users> getAllUsers() {
        String query;
        List<Users> albuns = new ArrayList<Users>();
        query = "SELECT * FROM " + USER_TABLE;

        Log.e(LOG, query);

        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                Users user = new Users();
                user.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                user.setNome(c.getString(c.getColumnIndex(COLUMN_NAME)));
                user.setLogin(c.getString(c.getColumnIndex(COLUMN_LOGIN)));
                user.setPassword(c.getString(c.getColumnIndex(COLUMN_PASS)));
                albuns.add(user);
            } while (c.moveToNext());
        }
        return albuns;
    }
    /* public boolean getUserIfExist(String login){
         //HashMap<String, String> user = new HashMap<String, String>();
         String query = "SELECT * FROM " + USER_TABLE + " WHERE " + COLUMN_LOGIN + " = " + "'"+login+"'";

         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(query, null);
         cursor.moveToFirst();
         if (cursor.getCount() > 0) {
             return true;
         }
         cursor.close();
         db.close();

         return false;
     }*/
    public int removeUserByLogin(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE, COLUMN_LOGIN + " = ?", new String[]{login});
    }

    /*IMOVEIS*/
    public Imoveis addImovel(String descricao,String localizacao,String tipologia, String url_foto) {

        Imoveis imovel = new Imoveis(url_foto,descricao,localizacao,tipologia);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMOVEL_DESCRICAO, imovel.getDescricao());
        values.put(IMOVEL_URL, imovel.getUrl_foto());
        values.put(IMOVEL_LOCALIZACAO, imovel.getLocalizacao());
        values.put(IMOVEL_TIPOLOGIA, imovel.getTipologia());

        int id = (int) db.insert(IMOVEL_TABLE, null, values);
        imovel.setId(id);

        return imovel;
    }
    public Imoveis getImovelById(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + IMOVEL_TABLE + " WHERE " + IMOVEL_ID + " = " + user_id;
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Imoveis imovel = new Imoveis();
        imovel.setLocalizacao(cursor.getString(cursor.getColumnIndex(IMOVEL_LOCALIZACAO)));
        imovel.setId(cursor.getInt(cursor.getColumnIndex(IMOVEL_ID)));
        imovel.setDescricao(cursor.getString(cursor.getColumnIndex(IMOVEL_DESCRICAO)));
        imovel.setTipologia(cursor.getString(cursor.getColumnIndex(IMOVEL_TIPOLOGIA)));
        return imovel;
    }
    public void addImovelByUser(String descricao,String localizacao,String tipologia, String url_foto) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMOVEL_DESCRICAO, descricao);
        values.put(IMOVEL_URL, localizacao);
        values.put(IMOVEL_LOCALIZACAO, tipologia);
        values.put(IMOVEL_TIPOLOGIA, url_foto);

        int id = (int) db.insert(IMOVEL_TABLE, null, values);
        db.close();
        Log.d(TAG, "Imovel inserido" + id);
    }
    public Imoveis getImovelByDesc(String descricao) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + IMOVEL_TABLE + " WHERE " + IMOVEL_DESCRICAO + " = " + "'" +descricao+"'";

        Log.e(LOG, query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Imoveis imovel = new Imoveis();
        imovel.setId(cursor.getInt(cursor.getColumnIndex(IMOVEL_ID)));
        imovel.setDescricao(cursor.getString(cursor.getColumnIndex(IMOVEL_DESCRICAO)));
        imovel.setTipologia(cursor.getString(cursor.getColumnIndex(IMOVEL_TIPOLOGIA)));
        imovel.setLocalizacao(cursor.getString(cursor.getColumnIndex(IMOVEL_LOCALIZACAO)));
        imovel.setUrl_foto(cursor.getString(cursor.getColumnIndex(IMOVEL_URL)));

        return imovel;
    }
    public boolean verifyImovelByDesc(String descricao) {
        String selectQuery = "SELECT * FROM  " + IMOVEL_TABLE + " WHERE " +
                IMOVEL_DESCRICAO + " = " + "'" + descricao + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
    public int removeImovelByDesc(String descricao) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(IMOVEL_TABLE, IMOVEL_DESCRICAO + " = ?", new String[]{descricao});
    }
    public boolean updateImovel(String oldDesc, String descricao, String localizacao, String tipologia, String url){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "UPDATE " + IMOVEL_TABLE
                + " SET " + IMOVEL_DESCRICAO
                + " = " + "'"+descricao+"'"
                + ", " + IMOVEL_LOCALIZACAO
                + " = " + "'"+localizacao+"'"
                + ", " + IMOVEL_TIPOLOGIA
                + " = " + "'"+tipologia+"'"
                + ", " + IMOVEL_URL
                + " = " + "'"+url+"'"
                + " WHERE " + IMOVEL_DESCRICAO
                + " = " + "'"+oldDesc+"'";
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return true;
    }

    public List<Imoveis> getAllImoveis() {
        String query;
        List<Imoveis> imoveis = new ArrayList<Imoveis>();


        query = "SELECT * FROM " + IMOVEL_TABLE;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Imoveis imovel = new Imoveis();
                imovel.setId(c.getInt(c.getColumnIndex(IMOVEL_ID)));
                imovel.setDescricao(c.getString(c.getColumnIndex(IMOVEL_DESCRICAO)));
                imovel.setTipologia(c.getString(c.getColumnIndex(IMOVEL_TIPOLOGIA)));
                imovel.setLocalizacao(c.getString(c.getColumnIndex(IMOVEL_LOCALIZACAO)));
                imovel.setUrl_foto(c.getString(c.getColumnIndex(IMOVEL_URL)));
                imoveis.add(imovel);
            } while (c.moveToNext());
        }
        return imoveis;
    }

    public Clientes addCliente(String nome, String idade, String url_foto) {

        Clientes cliente = new Clientes(nome,idade,url_foto);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLIENT_NAME, cliente.getNome());
        values.put(CLIENT_AGE, cliente.getIdade());
        values.put(CLIENT_URL, cliente.getUrl_foto());

        int id = (int) db.insert(CLIENT_TABLE, null, values);
        cliente.setId(id);

        return cliente;
    }
    public List<Clientes> getAllClientes() {
        String query;
        List<Clientes> clientes = new ArrayList<Clientes>();


        query = "SELECT * FROM " + CLIENT_TABLE;

        Log.e(LOG, query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Clientes cliente = new Clientes();
                cliente.setId(c.getInt(c.getColumnIndex(CLIENT_ID)));
                cliente.setNome(c.getString(c.getColumnIndex(CLIENT_NAME)));
                cliente.setIdade(c.getString(c.getColumnIndex(CLIENT_AGE)));
                cliente.setUrl_foto(c.getString(c.getColumnIndex(CLIENT_URL)));
                clientes.add(cliente);
            } while (c.moveToNext());
        }
        return clientes;
    }
    public Clientes getClienteById(long user_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + CLIENT_TABLE + " WHERE " + CLIENT_ID + " = " + user_id;
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Clientes cliente = new Clientes();
        cliente.setNome(cursor.getString(cursor.getColumnIndex(CLIENT_NAME)));
        cliente.setIdade(cursor.getString(cursor.getColumnIndex(CLIENT_AGE)));
        cliente.setId(cursor.getInt(cursor.getColumnIndex(CLIENT_ID)));
        return cliente;
    }
    public Clientes getClienteByName(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT  * FROM " + CLIENT_TABLE + " WHERE " + CLIENT_NAME + " = " + "'" +nome+"'";

        Log.e(LOG, query);
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Clientes cliente = new Clientes();
        cliente.setId(cursor.getInt(cursor.getColumnIndex(CLIENT_ID)));
        cliente.setNome(cursor.getString(cursor.getColumnIndex(CLIENT_NAME)));
        cliente.setIdade(cursor.getString(cursor.getColumnIndex(CLIENT_AGE)));
        cliente.setUrl_foto(cursor.getString(cursor.getColumnIndex(CLIENT_URL)));
        return cliente;
    }
    public boolean verifyClienteByName(String nome) {
        String selectQuery = "SELECT * FROM  " + CLIENT_TABLE + " WHERE " +
                CLIENT_NAME + " = " + "'" + nome + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
    public boolean updateCliente(String oldNome, String nome, String idade, String url){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "UPDATE " + CLIENT_TABLE
                + " SET " + CLIENT_NAME
                + " = " + "'"+nome+"'"
                + ", " + CLIENT_AGE
                + " = " + "'"+idade+"'"
                + ", " + CLIENT_URL
                + " = " + "'"+url+"'"
                + " WHERE " + CLIENT_NAME
                + " = " + "'"+oldNome+"'";
        Log.e(LOG, query);

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return true;
    }
    public int removeClienteByName(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CLIENT_TABLE, CLIENT_NAME + " = ?", new String[]{nome});
    }
    public void closeBD() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}