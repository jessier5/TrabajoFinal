package es.exitae.ejerciciofinal.activity;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

public class EditarLugarActivity extends Activity implements OnClickListener{
	
	//variable que no permitira acceder a la base de datos
	private LugaresDAO 	db;
	private Lugar 		lugar;
	// variables para el manejo del UI
	private Button 	 btnGuardar;
	private Button 	 btnCrear;
	private Button 	 btnEliminar;
	private Button   btnSalir;
	private EditText txtNombre;
	private EditText txtDescripcion;
	private EditText txtLatitud;
	private EditText txtLongitud;
	private ImageView	iFoto;
	private URI		  	uriFoto;
	private Bitmap loadedImage;
	private Boolean isCrear = false;
	private static int SELECT_PICTURE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_lugares);
		this.db=new LugaresDAO(this);
		
		//inicializamos lass variabes de la ventana		
		txtNombre 		= (EditText) findViewById(R.id.txtNomLugar);
		txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
		txtLatitud 	= (EditText) findViewById(R.id.txtLatitud);
		txtLongitud 	= (EditText) findViewById(R.id.txtLongitud);
		
		iFoto			= (ImageView)findViewById(R.id.imgFoto);
		iFoto.setOnClickListener(this);
		
		btnGuardar 	= (Button) findViewById(R.id.btnGuardar);
		btnGuardar.setOnClickListener(this);
		
		btnCrear 		= (Button) findViewById(R.id.btnCrear);
		btnCrear.setOnClickListener(this);
		
		btnEliminar 	= (Button) findViewById(R.id.btnEliminar);
		btnEliminar.setOnClickListener(this);
		
		btnSalir = (Button) findViewById(R.id.btnSalir);
		btnSalir.setOnClickListener(this);
		
		cargaDatosExtras();
		
		
	}
	/**
	 * Metodo que verifica si nos envian datos desde
	 * otra activity
	 * */
	public void cargaDatosExtras(){
		this.lugar	= (Lugar) getIntent().getExtras().getSerializable("lugar");
		this.isCrear = getIntent().getExtras().getBoolean("crear");
		
		if(this.isCrear){
			
			visualizaBotones();
			// si es crear rellenamos las coordenadas en los txt
			this.txtLatitud.setText(String.valueOf(lugar.getLatitud()));
			this.txtLongitud.setText(String.valueOf(lugar.getLongitud()));
			
		}
		else{
			//this.visualizaBotones();
			// en caso contrario rellenamos todos los datos 
			this.txtNombre.setText(lugar.getNombreLugar());
			this.txtDescripcion.setText(lugar.getDescrLugar());
			this.txtLongitud.setText(String.valueOf(lugar.getLongitud()));
			this.txtLatitud.setText(String.valueOf(lugar.getLatitud()));
			
			//Cargamos la imagen desde la url
			this.mostrarImagen(Uri.parse(lugar.getFoto()));
		}
		
	}
	/**
	 * metodo que se encarga de visualizar los botones
	 * */
	public void visualizaBotones(){
		
		if(this.isCrear){
			btnEliminar.setVisibility(View.INVISIBLE);
			btnGuardar.setVisibility(View.INVISIBLE);
			btnCrear.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
		}
		else{
			btnEliminar.setVisibility(View.VISIBLE);
			btnGuardar.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
			btnCrear.setVisibility(View.INVISIBLE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.imgFoto: 
				// si se hace click en la imagen mostramos la galeria
				Intent igaleria = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(igaleria, SELECT_PICTURE);
				break;
				
			case R.id.btnGuardar: 
				asignaDatos();
				updateLugar();
				break;
				
			case R.id.btnCrear: 
				asignaDatos();
				crearLugar();		
				break;
				
			case R.id.btnEliminar: 
				eliminar();
				break;
				
			case R.id.btnSalir:
				System.exit(0);
				break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == SELECT_PICTURE){
			//mostramos la imagen seleccionada
    		Uri selectedImage = data.getData();
    		this.mostrarImagen(selectedImage);
    		this.lugar.setFoto(selectedImage.toString());
    	}
	}
	//Metodo que muestra la imagen
	public void mostrarImagen(Uri uriImg){
		Uri selectedImage = uriImg; //data.getData();
		InputStream is;
		try {
			is = getContentResolver().openInputStream(selectedImage);
	    	BufferedInputStream bis = new BufferedInputStream(is);
	    	Bitmap bitmap = BitmapFactory.decodeStream(bis);            
	    	ImageView iv = (ImageView)findViewById(R.id.imgFoto);
	    	iv.setImageBitmap(bitmap);	
	    		    	
		} catch (FileNotFoundException e) {}
		
	}
	public void asignaDatos(){
		this.lugar.setDescrLugar(this.txtNombre.getText().toString());
		this.lugar.setNombreLugar(this.txtDescripcion.getText().toString());
	}
	public void updateLugar(){
		this.db.update(this.lugar);
	}
	public void crearLugar(){
		this.db.insert(this.lugar);
		Toast.makeText(this, " Lugar se ha creado correctamente ",Toast.LENGTH_SHORT).show();
	}
	public void eliminar(){
		this.db.delete(this.lugar);
	}
	
}
