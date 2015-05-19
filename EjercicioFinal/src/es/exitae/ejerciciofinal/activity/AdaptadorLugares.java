package es.exitae.ejerciciofinal.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

public class AdaptadorLugares extends BaseAdapter {
	
	//implements OnClickListener
	//creamos las variables necesarias 
	private LayoutInflater inflador; //Permite crear un objeto Java a partir de un fichero XML
    private TextView  nombre, descripcion;
    private ImageView foto;
 
    // variable para la conexion a la base de datos
    private LugaresDAO db;
    private List<Lugar> Lugares;
    
    private Context context;
    private AdministrarCamara admCam;
    private Context ctx;
    
    public AdaptadorLugares(Context contexto) {
    	
    	inflador = LayoutInflater.from(contexto);
    	this.context = (Activity) this.context;
    	Log.d("++++ Iniciando adpatador: ", Thread.currentThread().getName());
    	//inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	this.db			= new LugaresDAO(contexto);
        this.admCam 	= new AdministrarCamara(contexto);
        this.Lugares 	= new ArrayList<Lugar>();
        this.ctx		= contexto;
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
		return Lugares.get(position);
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
	 * @param convertView Vista con la información adecuada del elemento a insertar.
	 * @param padre	Layout contenedor donde se insertará el elemento.
	 * @return convertView 
	 */
	@Override
	public View getView(int posicion, View convertView, ViewGroup parent) {
		
		View view;
		ViewHolder holder;
		
	     if (convertView == null) {
	    	 view = inflador.inflate(R.layout.elemento_lista_lugares, parent, false);
	    	 holder = new ViewHolder();
	    	 holder.foto = (ImageView) view.findViewById(R.id.foto);
	    	 holder.nombre = (TextView) view.findViewById(R.id.nombre);
	    	 holder.descripcion = (TextView) view.findViewById(R.id.descripcion);
	    	 holder.imgEliminar = (ImageView) view.findViewById(R.id.imgEliminar);
	    	 holder.imgEliminar.setOnClickListener(new OnItemClickListener(posicion));
	    	 view.setTag(holder);
	     }
	     else{
	    	 view = convertView;
			 holder = (ViewHolder)view.getTag();
	     }
	     
	     Lugar lugar = Lugares.get(posicion);
	     holder.nombre.setText(lugar.getNombreLugar());  
	     holder.descripcion.setText(lugar.getDescrLugar());
	     
	     if (lugar.getFoto()!=null && !lugar.getFoto().equals("")) {
			 this.admCam.asignarFotoView(holder.foto, lugar.getFoto(), 400, true);
		}   
	    return view;
	}
	
	private class ViewHolder {
		public ImageView foto,imgEliminar;
		public TextView nombre, descripcion;
	}
	//metodo que agrega un elemento a lista
	
	public void addLugar(Lugar lugar){
		this.Lugares.add(lugar);
	}
	public void cleanList(){
		this.Lugares.clear();
	}
	
	private class OnItemClickListener implements OnClickListener{ 
		private int mPosition; 
		
		OnItemClickListener(int position){
				mPosition = position; 
		} 
		
		@Override 
		public void onClick(View arg0) { 
			int resp=db.delete(Lugares.get(mPosition));
			if (resp==1) {
				Lugares.remove(mPosition);
				Toast.makeText(ctx, "Lugar eliminado", Toast.LENGTH_LONG).show();
				notifyDataSetChanged();
			} else {
				Toast.makeText(ctx, "Error: al eliminar el lugar..", Toast.LENGTH_LONG).show();
			}
		} 
	}
}

