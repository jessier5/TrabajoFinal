/*
 * Actividad que vaya a visualizar un ListView ha de heredar de ListActivity
 */

package es.exitae.ejerciciofinal.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;


public class ListaLugaresActivity extends ListActivity  {
	
	//Declara objeto de la clase AdaptadorLugares
	public AdaptadorLugares adaptador;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		//Layout que contendrá la lista de lugares
		setContentView(R.layout.listar_lugares);
		
		adaptador= new AdaptadorLugares(this);
		//Indicar el adaptador con la lista de elementos a visualizar
		setListAdapter(adaptador);
			   
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    Lugar lugar = (Lugar) getListAdapter().getItem(position);
	    //Toast.makeText(this, lugar.getNombreLugar() + " selected", Toast.LENGTH_LONG).show();
	    Log.d("++++ onClick: ", this.toString());
		Intent mostrarLugar = new Intent(this, MostrarLugarActivity.class);
		mostrarLugar.putExtra("lugar",lugar);
		mostrarLugar.putExtra("crear", false);
		this.startActivity(mostrarLugar);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.listar_lugares, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		new RetrieveLugaresListTask().execute();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.return_menu:
	        	Intent principal = new Intent(this, PrincipalActivity.class);
				startActivity(principal);
				this.finish();
	        	return true;
	    }
	    return true;
	}

	class RetrieveLugaresListTask extends AsyncTask <Void, Lugar, Void>{
		private LugaresDAO db;
		private List<Lugar> Lugares;
		private int count=0;
		@Override
		protected void onPreExecute() {
			setProgressBarIndeterminate(false);
			setProgressBarVisibility(true);
		}
		@Override
		protected Void doInBackground(Void... params) {
			this.db		 = new LugaresDAO(ListaLugaresActivity.this);
			this.Lugares = db.selectAll();
			for(Lugar lugar : this.Lugares){
			        publishProgress(lugar);
			        try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Lugar... values) {
			count++;
			setProgress((int)((double)count/this.Lugares.size())*10000);
			adaptador.addLugar(values[0]);
			adaptador.notifyDataSetChanged();
		}
		
		@Override
		protected void onPostExecute(Void result) {
			adaptador.notifyDataSetChanged();
			setProgressBarVisibility(false);
		}
	
	}

}
