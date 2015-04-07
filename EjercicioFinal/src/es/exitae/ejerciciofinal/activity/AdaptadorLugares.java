package es.exitae.ejerciciofinal.activity;

import es.exitae.ejerciciofinal.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class AdaptadorLugares extends BaseAdapter {

	private LayoutInflater inflador; //Permite crear un objeto Java a partir de un fichero XML
    TextView nombre, direccion;
    ImageView foto;
    RatingBar valoracion; 
    
    public AdaptadorLugares(Context contexto) {
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    
    /**
	 * Indica cuántos elementos queremos mostrar
	 */
	@Override
	public int getCount() {
		return Lugares.size();
	}

	/**
	 * Devolverá el objeto libro que se muestra en una determinada posición
	 */
	@Override
	public Object getItem(int position) {
		return Lugares.elemento(position);
	}

	/**
	 * Devolverá el id que se muestra en una determinada posición
	 */
	@Override
	public long getItemId(int position) {
		 return position;
	}

	/**
	 * Vista que usa el sistema para pedir cada uno de los elementos a insertar
	 * @param posicion Posición del elemento a insertar.
	 * @param vistaReciclada Vista con la información adecuada del elemento a insertar.
	 * @param padre	Layout contenedor donde se insertará el elemento.
	 * @return vistaReciclada 
	 */
	@Override
	public View getView(int posicion, View vistaReciclada, ViewGroup padre) {
		 Lugar lugar = Lugares.elemento(posicion);
	        if (vistaReciclada == null) {
	            vistaReciclada =
	                   inflador.inflate(R.layout.elemento_lista_lugares, null);
	        }
	        nombre = (TextView) vistaReciclada.findViewById(R.id.nombre);
	        direccion =
	                 (TextView) vistaReciclada.findViewById(R.id.direccion);
	        foto = (ImageView) vistaReciclada.findViewById(R.id.foto);
	        valoracion =
	                 (RatingBar) vistaReciclada.findViewById(R.id.valoracion);
	        nombre.setText(lugar.getNombre());
	        direccion.setText(lugar.getDireccion());
	        int id = R.drawable.ic_launcher;//R.drawable.otros;
	        switch(lugar.getTipo()) {
	             case RESTAURANTE: id = R.drawable.ic_launcher;//R.drawable.restaurante; 
	             		break;         		 
	             case BAR:    id = R.drawable.ic_launcher;//R.drawable.bar;     
	             		break;
	             case COPAS:   id = R.drawable.ic_launcher;//R.drawable.copas;    
	             		break;
	             case ESPECTACULO: id = R.drawable.ic_launcher;//R.drawable.espectaculos; 
	             		break;
	             case HOTEL: id = R.drawable.ic_launcher;//id = R.drawable.hotel;    
	             		break;
	             case COMPRAS: id = R.drawable.ic_launcher;// R.drawable.compras;   
	             		break;
	             case EDUCACION: id = R.drawable.ic_launcher; //id = R.drawable.educacion;  
	             		break;
	             case DEPORTE: id = R.drawable.ic_launcher;//id = R.drawable.deporte;   
	             		break;
	             case NATURALEZA: id = R.drawable.ic_launcher;//R.drawable.naturaleza; 	
	             		break;
	             case GASOLINERA: id = R.drawable.ic_launcher;//R.drawable.gasolinera; 
	             		break;
	        }
	        foto.setImageResource(id);
	        foto.setScaleType(ImageView.ScaleType.FIT_END);
	        valoracion.setRating(lugar.getValoracion());
	        return vistaReciclada;
	}

}
