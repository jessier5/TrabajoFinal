package es.exitae.ejerciciofinal.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

@SuppressLint("NewApi")
public class MapaLugaresActivity extends Activity 
	implements LocationListener,OnMarkerClickListener,OnMarkerDragListener, OnMapClickListener{
	
	// definimos las variables para acceder a la lista de lugares
	 private LugaresDAO db;
	 private List<Lugar> Lugares;
	 //definimos la variable para el manejo del mapa
	 private GoogleMap mapa	=	null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			
			mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			mapa.getUiSettings().setZoomControlsEnabled(true);
			mapa.getUiSettings().setCompassEnabled(true);
			mapa.getUiSettings().setMyLocationButtonEnabled(true);
			
			mapa.setOnMarkerClickListener(this);
			mapa.setOnMarkerDragListener(this);
		}
	}
	 
	@Override
	public void onLocationChanged(Location location) {
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
		
		
	}
	 
	@Override
	public void onMarkerDrag(Marker arg0) {}
	@Override
	public void onMarkerDragEnd(Marker arg0) {}
	@Override
	public void onMarkerDragStart(Marker arg0) {}
	@Override
	public boolean onMarkerClick(Marker arg0) {
		// esto se ejecutara cuando el usuario de un click en cualquiera de los lugares existentes
		// y se debera ejecutar la activity editar lugar para su edicion
		return false;
		
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onProviderDisabled(String provider) {}

	@Override
	public void onMapClick(LatLng arg0) {
		// esto se ejecuta cuando el usuario da click en cualquier lugar del mapa
		// con lo que se tendra que ejecutar el activity Editar lugar pero para crear 
		// un nuevo lugar para el usuario
		
	}

}
