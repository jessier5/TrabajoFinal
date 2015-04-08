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
	 public int update(Lugar dataWhere, Lugar LugarNuevo){
		 //
		 ContentValues values = new ContentValues();
		 
		 String selection=""; 
		 String where	 ="";
		 
		 //seteamos los nuevos valores para el lugar
		 if(LugarNuevo.getNombreLugar()!=null && 
		    !LugarNuevo.getNombreLugar().equals("")){
			 values.put(LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR, LugarNuevo.getNombreLugar());
		 }
		 if(LugarNuevo.getDescrLugar()!=null && 
			!LugarNuevo.getDescrLugar().equals("")){
			 values.put(LugaresDataSourceContract.ColumnLugares.DESCRIPCION_LUGAR, LugarNuevo.getDescrLugar());
		 }
		 if(String.valueOf(LugarNuevo.getLatitud())!=null && 
			String.valueOf(LugarNuevo.getLatitud()).length()>0){
			 values.put(LugaresDataSourceContract.ColumnLugares.LATITUD_LUGAR, LugarNuevo.getLatitud());
		 }
		 if(String.valueOf(LugarNuevo.getLongitud())!=null && 
			String.valueOf(LugarNuevo.getLongitud()).length()>0){
			 values.put(LugaresDataSourceContract.ColumnLugares.LONGITUD_LUGAR, LugarNuevo.getLongitud());
		 }
		 if(LugarNuevo.getFoto()!=null && 
			!LugarNuevo.getFoto().equals("")){
			 values.put(LugaresDataSourceContract.ColumnLugares.FOTO_LUGAR, LugarNuevo.getFoto());
		 }
		 // indicamos los valores que iran en el where
		 
		//Verificamos si nos viene informado el ID
		 if(Integer.valueOf(dataWhere.getId())!=null){
			 selection=LugaresDataSourceContract.ColumnLugares.ID_LUGAR+" =?";
			 where=String.valueOf(dataWhere.getId());
		 }
		 //Verificamos si nos viene informado el Nombre del Lugar
		 if(dataWhere.getNombreLugar() !=null && 
		    !dataWhere.getNombreLugar().equals("")){
			 selection=selection.equals("")?
					 	LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR+" = ?":
					 	selection+" and "+LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR+" =?";
			 where=where.equals("")?dataWhere.getNombreLugar():where+","+dataWhere.getNombreLugar();
		 }
		 
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
		 String where	 ="";
		
		 //obtenemos los datos del registro a eliminar
		 //Verificamos si nos viene informado el ID
		 if(Integer.valueOf(lugar.getId())!=null){
			 selection=LugaresDataSourceContract.ColumnLugares.ID_LUGAR+" =?";
			 where=String.valueOf(lugar.getId());
		 }
		 //Verificamos si nos viene informado el Nombre del Lugar
		 if(lugar.getNombreLugar() !=null && 
		    !lugar.getNombreLugar().equals("")){
			 selection=selection.equals("")?
					 	LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR+" = ?":
					 	selection+" and "+LugaresDataSourceContract.ColumnLugares.NOMBRE_LUGAR+" =?";
			 where=where.equals("")?lugar.getNombreLugar():where+","+lugar.getNombreLugar();
		 }
		 //verificamos si nos viene informado la latitud
		 
		 
		 String [] datosWhere={where};	
		 this.database.delete(LugaresDataSourceContract.TABLE_NAME, selection, datosWhere);
		 
		 return 0;
	 }

}
