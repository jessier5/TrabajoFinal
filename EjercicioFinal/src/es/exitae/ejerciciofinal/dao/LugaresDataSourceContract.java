package es.exitae.ejerciciofinal.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LugaresDataSourceContract {

	
	/**
	 * Meta información de la base de datos 
	 * */
	public static final String TABLE_NAME 	= "lugares";
	public static final String TYPE_STRING 	= "text";
	public static final String TYPE_INT 		= "integer";
	public static final String TYPE_FLOAT 	= "real";
	
	
	//Defeinimos las columnas de la tabla
	public static class ColumnLugares{
		public static final String ID_LUGAR					="id";
		public static final String NOMBRE_LUGAR			="nombre";
		public static final String DESCRIPCION_LUGAR	="descripcion";
		public static final String LATITUD_LUGAR			="latitud";
		public static final String LONGITUD_LUGAR		="longitud";
		public static final String FOTO_LUGAR				="foto";	
	}
	// script de creacion de la tabla
	
	public static final String CREATE_LUGARES_SCRIPT=" create table "+ TABLE_NAME +" (" +
													ColumnLugares.ID_LUGAR+" "+TYPE_INT+" primary key autoincrement," +
													ColumnLugares.NOMBRE_LUGAR+" "+TYPE_STRING+", "+
													ColumnLugares.DESCRIPCION_LUGAR+" "+TYPE_STRING+", "+
													ColumnLugares.LATITUD_LUGAR+" "+TYPE_FLOAT+", "+
													ColumnLugares.LONGITUD_LUGAR+" "+TYPE_FLOAT+", "+
													ColumnLugares.FOTO_LUGAR+" "+TYPE_STRING+")";
   
  }
