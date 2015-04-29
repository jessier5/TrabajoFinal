package es.exitae.ejerciciofinal.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class MostrarLugarActivity extends Activity implements OnClickListener {
	//variable que no permitira acceder a la base de datos
	private LugaresDAO 	db;
	private Lugar 		lugar;
	// variables para el manejo del UI
	private Button 	 btnEditar;
	private EditText txtNombre;
	private EditText txtDescripcion;
	private EditText txtLatitud;
	private EditText txtLongitud;
	private ImageView	 iFoto;
	private URL		  urlFoto;
	private Bitmap loadedImage;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_lugar);
		this.db=new LugaresDAO(this);
		//inicializamos lass variabes de la ventana
		
		btnEditar = (Button) findViewById(R.id.btnEditarLugar);
		btnEditar.setOnClickListener(this);
		
		txtNombre = (EditText) findViewById(R.id.txtNomLugar);
		txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
		txtLatitud = (EditText) findViewById(R.id.txtLatitud);
		txtLongitud = (EditText) findViewById(R.id.txtLongitud);
		
		iFoto		= (ImageView)findViewById(R.id.imgFoto);
		iFoto.setOnClickListener(this);
		
		this.cargaDatosLugar();
	}
	/** metodo que carga los datos del lugar a traves de un id que nos envian*/
	public void cargaDatosLugar(){	
		String idLugar = getIntent().getExtras().getString("id");
		this.lugar=db.findLugarId(idLugar);
		this.txtNombre.setText(lugar.getNombreLugar());
		this.txtDescripcion.setText(lugar.getDescrLugar());
		this.txtLongitud.setText(String.valueOf(lugar.getLongitud()));
		this.txtLatitud.setText(String.valueOf(lugar.getLatitud()));
		
		//Cargamos la imagen desde la url
		try{
			this.urlFoto=new URL(lugar.getFoto());
			HttpURLConnection conn = (HttpURLConnection) this.urlFoto.openConnection();
	        conn.connect();
	        this.loadedImage = BitmapFactory.decodeStream(conn.getInputStream());
			this.iFoto.setImageBitmap(loadedImage);
		} catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()){
			case R.id.btnEditarLugar: 
				Intent editarLugar = new Intent(this, EditarLugarActivity.class);
				editarLugar.putExtra("lugar",lugar);
				startActivity(editarLugar);
			break;
		}
		
	}
}
