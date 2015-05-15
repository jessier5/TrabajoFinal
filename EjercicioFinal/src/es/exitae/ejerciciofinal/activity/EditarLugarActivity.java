package es.exitae.ejerciciofinal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	//private EditText txtLatitud;
	//private EditText txtLongitud;
	private ImageView	iFoto;
	private Boolean 	isCrear = false;
	private static int 	SELECT_PICTURE = 2;
	private Intent 		igaleria;
	private AdministrarCamara admCam;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editar_lugares);
		this.db		=new LugaresDAO(this);
		this.admCam = new AdministrarCamara(this);
		
		
		//inicializamos lass variabes de la ventana		
		txtNombre 		= (EditText) findViewById(R.id.txtNomLugar);
		txtDescripcion 	= (EditText) findViewById(R.id.txtDescripcion);
		//txtLatitud 		= (EditText) findViewById(R.id.txtLatitud);
		//txtLongitud 	= (EditText) findViewById(R.id.txtLongitud);
		
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
	
	/*public void nuevoLugar(){
		this.txtLongitud.setText(String.valueOf(this.lugar.getLongitud()));
		this.txtLatitud.setText(String.valueOf(this.lugar.getLatitud()));
	}*/
	public void modificarLugar(){
		this.txtNombre.setText(this.lugar.getNombreLugar());
		this.txtDescripcion.setText(this.lugar.getDescrLugar());
		//this.txtLongitud.setText(String.valueOf(this.lugar.getLongitud()));
		//this.txtLatitud.setText(String.valueOf(this.lugar.getLatitud()));
		
		if (this.lugar.getFoto()!=null && !this.lugar.getFoto().equals("")) {
			 this.admCam.asignarFotoView(this.iFoto, this.lugar.getFoto(), 200, false);
		} 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("+++++ onActivityResult: ", "mostrar imagen");
		Toast.makeText(this, "onActivityResult: "+resultCode+", requestCode: "+requestCode,Toast.LENGTH_SHORT).show();
		if(resultCode ==RESULT_OK){
			this.lugar.setFoto(data.getDataString());
			this.admCam.asignarFotoView(this.iFoto, this.lugar.getFoto(), 200, false);
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
			btnEliminar.setVisibility(View.GONE);
			btnGuardar.setVisibility(View.GONE);
			btnCrear.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
			
			//this.nuevoLugar();
		}
		else{
			btnEliminar.setVisibility(View.VISIBLE);
			btnGuardar.setVisibility(View.VISIBLE);
			btnSalir.setVisibility(View.VISIBLE);
			btnCrear.setVisibility(View.GONE);
			
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
				actualizarLugar();
				break;
				
			case R.id.btnCrear: 
				this.crearLugar();
				break;
				
			case R.id.btnEliminar: 
				this.eliminar();
				break;
				
			case R.id.btnSalir:
				System.exit(0);
				break;
		}
	}
	
	public void eliminar(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("¿Desea Eliminar el Lugar?")
        .setTitle("Información")
        .setCancelable(false)
		.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
		.setPositiveButton("Continuar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	eliminarLugar();
                    }
                });
		AlertDialog alert = builder.create();
		alert.show(); 
	}
	public void eliminarLugar(){
		int resp=this.db.delete(this.lugar);
		if (resp==1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("El Lugar se ha Eliminado.")
	        .setTitle("Información.")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();	
	                        callListarLugar();
	                  }
	                });
			AlertDialog alert = builder.create();
			alert.show();
			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("El Lugar no se ha Eliminado.")
	        .setTitle("Error ...")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();	
	                  }
	                });
			AlertDialog alert = builder.create();
			alert.show();
			
		}
	}

	public void crearLugar(){
		asignaDatos();
		long resp = this.db.insert(this.lugar);	
		if (resp!=-1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("El Lugar se ha creado correctamente.")
	        .setTitle("Información ...")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                        callMapaLugares();
	                  }
	                });
			AlertDialog alert = builder.create();
			alert.show();
			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No Se ha podido crear el Lugar.")
	        .setTitle("Error ...")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();	
	                  }
	                });
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	public void actualizarLugar(){
		asignaDatos();
		int resp=this.db.update(this.lugar);
		if (resp==1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("El Lugar se ha Actualizado")
	        .setTitle("Información.")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();
	                        callMostrarLugares(lugar);
	                  }
	         });
			AlertDialog alert = builder.create();
			alert.show();
			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("No Se ha podido Actualizar el Lugar")
	        .setTitle("Error ...")
	        .setCancelable(false)
	        .setNeutralButton("Aceptar",
	               new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        dialog.cancel();	
	                  }
	                });
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	public void callListarLugar( ){
		Intent listaLugar = new Intent(this, ListaLugaresActivity.class);
		startActivity(listaLugar);
	}
	public void callMapaLugares(){
		Intent mapaLugar = new Intent(this, MapaLugaresActivity.class);
		startActivity(mapaLugar);
	}
	public void callMostrarLugares(Lugar lugar){
		Intent mapaLugar = new Intent(this, MostrarLugarActivity.class);
		mapaLugar.putExtra("lugar",lugar);
		startActivity(mapaLugar);
	}
}
