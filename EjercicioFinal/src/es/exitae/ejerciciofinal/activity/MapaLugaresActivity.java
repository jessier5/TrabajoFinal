package es.exitae.ejerciciofinal.activity;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

//@SuppressLint("NewApi")
public class MapaLugaresActivity extends FragmentActivity 
	implements LocationListener,OnMarkerClickListener,OnMarkerDragListener, OnMapClickListener, OnMyLocationChangeListener{
	
	// definimos las variables para acceder a la lista de lugares
	 private LugaresDAO db;
	 private List<Lugar> Lugares;
	 //definimos la variable para el manejo del mapa
	 private GoogleMap mapa	=	null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("++++ Iniciando PrincipalActivity: ", Thread.currentThread().getName());
		setContentView(R.layout.activity_mapa_lugares);
		//obtenemos la conexion a la base de datos
		this.db	=	new LugaresDAO(this);
		// obtenemos el estado de conexion a la red
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
		
		/**
		 * verificamos si tenemos conexion*/
		if(status != ConnectionResult.SUCCESS){ 
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
			dialog.show();
		}
		else{
			LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
			Log.d("++++ Conexion ok: ", Thread.currentThread().getName());
			mapa = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();	
			
			mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mapa.getUiSettings().setZoomControlsEnabled(true);
			mapa.getUiSettings().setCompassEnabled(true);
			mapa.getUiSettings().setMyLocationButtonEnabled(true);
			
			mapa.setOnMarkerClickListener(this);
			mapa.setOnMarkerDragListener(this);
			mapa.setOnMapClickListener(this);
			Log.d("++++ Conexion fin: ", Thread.currentThread().getName());
		}
	}
	 
	
	
	
	
	@Override
	public void onLocationChanged(Location location) {
		Log.d("++++ onLocationChanged ", Thread.currentThread().getName());
		//Limpiamos el mapa
		mapa.clear();
		// obtenemos los lugares de la base de datos
		Lugares=this.db.selectAll();
		//recorremos los lugares y los mostramos en el mapa
		for (Lugar lugar : Lugares){
			LatLng posLugar = new LatLng(lugar.getLatitud(),lugar.getLongitud());
			mapa.addMarker(new MarkerOptions().position(posLugar)
							              .title(lugar.getNombreLugar())
							              .icon(BitmapDescriptorFactory 
							              .fromResource(R.drawable.flag)));
		}
		Log.d("++++ FinonLocationChanged ", Thread.currentThread().getName());
	}
	 

	@Override
	public boolean onMarkerClick(Marker datos) {
		// esto se ejecutara cuando el usuario de un click en cualquiera de los lugares existentes
		// y se debera ejecutar la activity editar lugar para su edicion
		Log.d("++++ onMarkerClick ", Thread.currentThread().getName());
		for(Lugar lugar: Lugares){
			if(lugar.getNombreLugar().equals(datos.getTitle()) &&
			   lugar.getLatitud()==(long)datos.getPosition().latitude &&
			   lugar.getLongitud() == (long)datos.getPosition().longitude){
				
				Intent modiLugar = new Intent(this, EditarLugarActivity.class);
				modiLugar.putExtra("lugar",lugar);
				modiLugar.putExtra("crear", false);
				startActivity(modiLugar);
				break;
			}
		}
		Log.d("++++ FinonMarkerClick ", Thread.currentThread().getName());
		return true;
		
	}
	
	@Override
	public void onMapClick(LatLng coordenadas) {
		// esto se ejecuta cuando el usuario da click en cualquier lugar del mapa
		// con lo que se tendra que ejecutar el activity Editar lugar pero para crear 
		// un nuevo lugar para el usuario
		
		Log.d("++++ onMapClick ", Thread.currentThread().getName());
		
		Intent crearLugar = new Intent(this, EditarLugarActivity.class);
		
		Lugar lugar = new Lugar();
		lugar.setLatitud((long)coordenadas.latitude);
		lugar.setLongitud((long)coordenadas.longitude);
		crearLugar.putExtra("lugar",lugar);
		crearLugar.putExtra("crear", true);
		startActivity(crearLugar);
		Log.d("++++ FinonMapClick ", Thread.currentThread().getName());
	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		Log.d("++++ onMarkerDrag ", Thread.currentThread().getName());
		
	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		Log.d("++++ onMarkerDragEnd ", Thread.currentThread().getName());
		
	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		Log.d("++++ onMarkerDragStart ", Thread.currentThread().getName());
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("++++ onStatusChanged ", Thread.currentThread().getName());
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d("++++ onProviderEnabled ", Thread.currentThread().getName());
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d("++++ onProviderDisabled ", Thread.currentThread().getName());
		
	}





	@Override
	public void onMyLocationChange(Location location) {
		Log.d("++++ onMyLocationChange ", Thread.currentThread().getName());
		//Te da la nueva posición
				LatLng posicion = new LatLng(location.getLatitude(), location.getLongitude());
								
				//decirle al mapa que se centre en este punto. Mueve nuestro punto de visión
				mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 7));	
				
				//desenganchar el listener, que lo desasocie. Solo se ejecuta una vez
				mapa.setOnMyLocationChangeListener(null);
		Log.d("++++ FinonMyLocationChange ", Thread.currentThread().getName());
		
	}


}
