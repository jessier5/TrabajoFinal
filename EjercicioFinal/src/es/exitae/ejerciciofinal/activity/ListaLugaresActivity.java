/*
 * Actividad que vaya a visualizar un ListView ha de heredar de ListActivity
 */

package es.exitae.ejerciciofinal.activity;

import es.exitae.ejerciciofinal.R;
import android.app.ListActivity;

import android.os.Bundle;


public class ListaLugaresActivity extends ListActivity {
	
	//Declara objeto de la clase AdaptadorLugares
	public AdaptadorLugares adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Layout que contendrá la lista de lugares
		setContentView(R.layout.listar_lugares);
		
		adaptador= new AdaptadorLugares(this);
		//Indicar el adaptador con la lista de elementos a visualizar
	    setListAdapter(adaptador);
		
	}
}
