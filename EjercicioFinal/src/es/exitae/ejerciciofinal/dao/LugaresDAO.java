package es.exitae.ejerciciofinal.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import es.exitae.ejerciciofinal.beans.Lugar;

public class LugaresDAO {
	 private SQLiteDatabase 			database;
	 private LugarReaderDbHelper openHelper;
	 
	 public LugaresDAO(Context context){
		 //inicializamos los datos para la conexion a la base de datos
		 openHelper = new LugarReaderDbHelper(context);
	     database 	 = openHelper.getWritableDatabase();
	 }
	 /**
	  * Metodo que inserta un nuevo lugar del usuario
	  * @param Lugar
	  * */
	 public  long  insert(Lugar lugar){
		 ContentValues values = new ContentValues();
		 //Mapeamos los datos para el insertarlos
		 values.put(LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR, lugar.getNombreLugar());
		 values.put(LugaresDataSourceContract.ColumnLugares.DESCRIPCION_LUGAR, lugar.getDescrLugar());
		 values.put(LugaresDataSourceContract.ColumnLugares.LATITUD_LUGAR, lugar.getLatitud());
		 values.put(LugaresDataSourceContract.ColumnLugares.LONGITUD_LUGAR, lugar.getLongitud());
		 values.put(LugaresDataSourceContract.ColumnLugares.FOTO_LUGAR, lugar.getFoto());
		 // insertamos en la tabla		
		 return  this.database.insert(LugaresDataSourceContract.TABLE_NAME, null, values);
	 }
	 /**
	  * Metodo que devuelve un listado de lugares del usuario
	  * @param Lugar
	  * */
	 public List<Lugar> selectAll(){
		 List<Lugar> lugares=new ArrayList<Lugar>();		 
		 String query="Select * from "+LugaresDataSourceContract.TABLE_NAME;
		 Cursor cLugares=database.rawQuery(query, null);
		 
		 while(cLugares.moveToNext()){
			 Lugar lugar = new Lugar();
			 lugar.setId(cLugares.getInt(0));
			 lugar.setNombreLugar(cLugares.getString(1));
			 lugar.setDescrLugar(cLugares.getString(2));
			 lugar.setLatitud(cLugares.getFloat(3));
			 lugar.setLongitud(cLugares.getFloat(4));
			 lugar.setFoto(cLugares.getString(5));
			 
			 lugares.add(lugar);
		 }
		 return lugares;
	 }
	 
	 /**
	  * Metodo que devuelve un listado de lugares del usuario
	  * @param Lugar
	  * */	 
	 public Lugar findLugarId(String idLugar){
		 Lugar lugar = null;
		 String query="Select * from "+LugaresDataSourceContract.TABLE_NAME+" "
		 			  + "where "+LugaresDataSourceContract.ColumnLugares.ID_LUGAR+"=?";
		 Cursor cLugares=database.rawQuery(query, new String[]{idLugar});
		 
		 if(cLugares.moveToNext()){
			 lugar = new Lugar();
			 lugar.setId(cLugares.getInt(0));
			 lugar.setNombreLugar(cLugares.getString(1));
			 lugar.setDescrLugar(cLugares.getString(2));
			 lugar.setLatitud(cLugares.getFloat(3));
			 lugar.setLongitud(cLugares.getFloat(4));
			 lugar.setFoto(cLugares.getString(5));
		 }
		 		 
		 return lugar;
		 
	 }
	 
	 
	 /**
	  * Metodo que actualiza un lugar del usuario
	  * @param Lugar
	  * */
	 public int update(Lugar upLugar){
		 //
		 ContentValues values = new ContentValues();
		 
		 String selection=""; 
		 String where	 ="";
		 
		 //seteamos los nuevos valores para el lugar
	 
		 values.put(LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR, upLugar.getNombreLugar());	 
		 values.put(LugaresDataSourceContract.ColumnLugares.DESCRIPCION_LUGAR, upLugar.getDescrLugar());
	 	 //values.put(LugaresDataSourceContract.ColumnLugares.LATITUD_LUGAR, upLugar.getLatitud());
	 	 //values.put(LugaresDataSourceContract.ColumnLugares.LONGITUD_LUGAR, upLugar.getLongitud());
	 	 values.put(LugaresDataSourceContract.ColumnLugares.FOTO_LUGAR, upLugar.getFoto());
	
		 selection=LugaresDataSourceContract.ColumnLugares.ID_LUGAR+" =?";
		 where=String.valueOf(upLugar.getId());
		 String [] datosWhere={where};	
		 return this.database.update(LugaresDataSourceContract.TABLE_NAME, values, selection, datosWhere);
	 }
	 /**
	  * Metodo que elimina un lugar del usuario
	  * @param Lugar
	  * */
	 public int delete(Lugar lugar){
		 //declaramos las variables que contendran los datos
		 // del registro a eliminar
		 String selection=""; 
		 selection=LugaresDataSourceContract.ColumnLugares.ID_LUGAR+" =?";
 		 
		 String [] datosWhere={String.valueOf(lugar.getId())};	
		 
		 return this.database.delete(LugaresDataSourceContract.TABLE_NAME, selection, datosWhere);
	 }

}
