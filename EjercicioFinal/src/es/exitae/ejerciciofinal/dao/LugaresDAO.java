package es.exitae.ejerciciofinal.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
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
	  * @param LugarBean
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
	  * @param LugarBean
	  * */
	 public List<Lugar> select(){
		 return null;
	 }
	 /**
	  * Metodo que actualiza un lugar del usuario
	  * @param LugarBean
	  * */
	 public int update(Lugar lugar){
		 return 0;
	 }
	 /**
	  * Metodo que elimina un lugar del usuario
	  * @param LugarBean
	  * */
	 public int delete(Lugar dataWhere, Lugar LugarNuevo){
		 //
		 String seleccion=""; 
		 
		 String selection = LugaresDataSourceContract.ColumnLugares.ID_LUGAR + " = ?";
		 String[] selectionArgs = { "3" };
		 
		 if(dataWhere.getNombreLugar() !=null && !dataWhere.getNombreLugar().equals("") ){
			 seleccion = dataWhere.getNombreLugar()+" =? ";
		 }
		if(Integer.valueOf(dataWhere.getId()) != null){
			seleccion = seleccion.equals("")? dataWhere.getNombreLugar()+" =? ":seleccion+" and "+dataWhere.getNombreLugar();
		}
		 String sWhere ="";		 
		 String [] datosWhere=null;
		 		 
		 this.database.delete(LugaresDataSourceContract.TABLE_NAME, selection, datosWhere);
		 
		 return 0;
	 }

}
