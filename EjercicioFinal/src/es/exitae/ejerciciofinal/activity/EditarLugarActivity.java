package es.exitae.ejerciciofinal.activity;

import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

public class EditarLugarActivity extends Activity {
	
	//variable que no permitira acceder a la base de datos
	private LugaresDAO 	db;
	private Lugar 		lugar;
	// variables para el manejo del UI
	private Button 	 btnGuardar;
	private Button 	 btnCrear;
	private Button 	 btnEliminar;
	private EditText txtNombre;
	private EditText txtDescripcion;
	private EditText txtLatitud;
	private EditText txtLongitud;
	private ImageView	 iFoto;
	private URL		  urlFoto;
	private Bitmap loadedImage;
	private Boolean isCrear = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_lugares);
		this.db=new LugaresDAO(this);
		//inicializamos lass variabes de la ventana		
		this.txtNombre 		= (EditText) findViewById(R.id.txtNomLugar);
		this.txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
		this.txtLatitud 	= (EditText) findViewById(R.id.txtLatitud);
		this.txtLongitud 	= (EditText) findViewById(R.id.txtLongitud);
		this.iFoto			= (ImageView)findViewById(R.id.imgFoto);
		
		this.btnGuardar 	= (Button) findViewById(R.id.btnGuardar);
		this.btnCrear 		= (Button) findViewById(R.id.btnCrear);
		this.btnEliminar 	= (Button) findViewById(R.id.btbEliminar);
		this.cargaDatosExtras();
		this.visualizaBotones();
		
	}
	/**
	 * Metodo que verifica si nos envian datos desde
	 * otra activity
	 * */
	public void cargaDatosExtras(){
		this.lugar	= (Lugar) getIntent().getExtras().getSerializable("lugar");
		this.isCrear=getIntent().getExtras().getBoolean("crear");
		
		if(this.isCrear){
			// si es crear rellenamos las coordenadas en los txt
		}
		else{
			// en caso contrario rellenamos todos los datos 
		}
		
	}
	public void visualizaBotones(){
		if(this.isCrear){
			this.btnEliminar.setVisibility(0);
			this.btnGuardar.setVisibility(0);
			this.btnCrear.setVisibility(1);
		}
		else{
			this.btnEliminar.setVisibility(1);
			this.btnGuardar.setVisibility(1);
			this.btnCrear.setVisibility(0);
		}
	}
}
