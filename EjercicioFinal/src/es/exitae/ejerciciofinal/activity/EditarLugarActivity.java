package es.exitae.ejerciciofinal.activity;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
	private Boolean 	isCrear = false;
	private static int 	SELECT_PICTURE = 2;
	private Intent 		igaleria;
	private AdministrarCamara admCam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_lugares);
		this.db=new LugaresDAO(this);
		this.admCam = new AdministrarCamara(this);
		//inicializamos lass variabes de la ventana		
		txtNombre 		= (EditText) findViewById(R.id.txtNomLugar);
		txtDescripcion 	= (EditText) findViewById(R.id.txtDescripcion);
		txtLatitud 		= (EditText) findViewById(R.id.txtLatitud);
		txtLongitud 	= (EditText) findViewById(R.id.txtLongitud);
		
		iFoto			= (ImageView)findViewById(R.id.imgFoto);
		btnGuardar 		= (Button) findViewById(R.id.btnGuardar);
		btnCrear 		= (Button) findViewById(R.id.btnCrear);
		btnEliminar 	= (Button) findViewById(R.id.btnEliminar);
		btnSalir		= (Button) findViewById(R.id.btnSalir);
		
		btnGuardar.setOnClickListener(this);
		btnCrear.setOnClickListener(this);
		btnEliminar.setOnClickListener(this);
		btnSalir.setOnClickListener(this);
		iFoto.setOnClickListener(this);
		
		this.cargaDatosExtras();
		this.visualizarDatosView();
		
	}
	/**
	 * Metodo que verifica si nos envian datos desde
	 * otra activity
	 * */
	public void cargaDatosExtras(){
		this.lugar		= (Lugar) getIntent().getExtras().getSerializable("lugar");
		this.isCrear 	= getIntent().getExtras().getBoolean("crear");
	}
	
	public void nuevoLugar(){
		this.txtLongitud.setText(String.valueOf(this.lugar.getLongitud()));
		this.txtLatitud.setText(String.valueOf(this.lugar.getLatitud()));
	}
	public void modificarLugar(){
		this.txtNombre.setText(this.lugar.getNombreLugar());
		this.txtDescripcion.setText(this.lugar.getDescrLugar());
		this.txtLongitud.setText(String.valueOf(this.lugar.getLongitud()));
		this.txtLatitud.setText(String.valueOf(this.lugar.getLatitud()));
		
		if (this.lugar.getFoto()!=null && !this.lugar.getFoto().equals("")) {
			 this.admCam.asignarFotoView(this.iFoto, this.lugar.getFoto(), 400);
		} 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("+++++ onActivityResult: ", "mostrar imagen");
		Toast.makeText(this, "onActivityResult: "+resultCode+", requestCode: "+requestCode,Toast.LENGTH_SHORT).show();
		if(resultCode ==RESULT_OK){
			this.lugar.setFoto(data.getDataString());
			this.admCam.asignarFotoView(this.iFoto, this.lugar.getFoto(), 400);
    	}
	}
	
	public void asignaDatos(){
		this.lugar.setDescrLugar(this.txtNombre.getText().toString());
		this.lugar.setNombreLugar(this.txtDescripcion.getText().toString());
	}
	/**
	 * metodo que se encarga de visualizar los botones
	 * */
	public void visualizarDatosView(){
		
		if(this.isCrear){
			btnEliminar.setVisibility(View.INVISIBLE);
			btnGuardar.setVisibility(View.INVISIBLE);
			btnCrear.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
			
			this.nuevoLugar();
		}
		else{
			btnEliminar.setVisibility(View.VISIBLE);
			btnGuardar.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
			btnCrear.setVisibility(View.INVISIBLE);
			
			this.modificarLugar();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.imgFoto: 
				Toast.makeText(this, " selected,  Foto", Toast.LENGTH_LONG).show();
				// si se hace click en la imagen mostramos la galeria
				this.igaleria = new Intent(Intent.ACTION_PICK, android.provider.
										   MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				if (this.igaleria.resolveActivity(getPackageManager()) != null) {
					startActivityForResult(this.igaleria, SELECT_PICTURE);
			    }
				
				break;
				
			case R.id.btnGuardar: 
				asignaDatos();
				this.db.update(this.lugar);
				break;
				
			case R.id.btnCrear: 
				asignaDatos();
				this.db.insert(this.lugar);	
				break;
				
			case R.id.btnEliminar: 
				int resp=this.db.delete(this.lugar);
				Toast.makeText(this, " Lugar Eliminado: "+resp, Toast.LENGTH_LONG).show();
				break;
				
			case R.id.btnSalir:
				System.exit(0);
				break;
		}
		
	}
}
