package es.exitae.ejerciciofinal.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LugarReaderDbHelper  extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "GestionLugares.db";
    public static final int DATABASE_VERSION = 1;

	public LugarReaderDbHelper(Context context) {
		//Creamos la base de datos 
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Creamos la tabla lugares
		 db.execSQL(LugaresDataSourceContract.CREATE_LUGARES_SCRIPT);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
