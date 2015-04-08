package es.exitae.ejerciciofinal.activity;

import android.app.Activity;
import es.exitae.ejerciciofinal.beans.Lugar;

public class EditarLugarActivity extends Activity {
	
	
	/**
	 * Metodo que verifica si nos envian datos desde
	 * otra activity
	 * */
	public void cargaDatosExtras(){
		Lugar lugar= (Lugar) getIntent().getExtras().getSerializable("lugar");
	}

}
