package es.exitae.ejerciciofinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import es.exitae.ejerciciofinal.R;

public class PrincipalActivity extends Activity implements OnClickListener {
	
	private Button btnLista, btnMapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("++++ Iniciando PrincipalActivity: ", Thread.currentThread().getName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		//Lanzamos evento del botón Lista
		btnLista = (Button) findViewById(R.id.btnLista);
		btnLista.setOnClickListener(this);
		
		//Lanzamos evento del botón Mapa
		btnMapa = (Button) findViewById(R.id.btnMapa);
		btnMapa.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnLista:
			//Lanza la actividad ListaLugaresActivity
			Intent actLista = new Intent(this, ListaLugaresActivity.class);
			startActivity(actLista);
			
			break;
		case R.id.btnMapa:
			//Lanza la actividad MapaLugaresActivity
			Intent actMapa = new Intent(this, MapaLugaresActivity.class);
			startActivity(actMapa);
			
			break;
		}
		
	}
}
