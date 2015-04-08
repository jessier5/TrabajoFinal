package es.exitae.ejerciciofinal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

public class MostrarLugarActivity extends Activity {
	//variable que no permitira acceder a la base de datos
	private LugaresDAO 	db;
	private Lugar 		lugar;
	// variables para el manejo del UI
	private Button 	 btbEditar;
	private EditText txtNombre;
	private EditText txtDescripcion;
	private EditText txtLatitud;
	private EditText txtLongitud;
	private String	 urlFoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_lugar);
		this.db=new LugaresDAO(this);
	}
}
