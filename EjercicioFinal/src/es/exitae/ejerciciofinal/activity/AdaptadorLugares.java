package es.exitae.ejerciciofinal.activity;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import es.exitae.ejerciciofinal.R;
import es.exitae.ejerciciofinal.beans.Lugar;
import es.exitae.ejerciciofinal.dao.LugaresDAO;

public class AdaptadorLugares extends BaseAdapter implements OnClickListener{
	//creamos las variables necesarias 
	private LayoutInflater inflador; //Permite crear un objeto Java a partir de un fichero XML
    private TextView  nombre, descripcion;
    private ImageView foto;
 
    // variable para la conexion a la base de datos
    private LugaresDAO db;
    private List<Lugar> Lugares;
    
    private Context context;
    
    public AdaptadorLugares(Context contexto) {
    	
    	inflador = LayoutInflater.from(contexto);
    	this.context = (Activity) this.context;
    	Log.d("++++ Iniciando adpatador: ", Thread.currentThread().getName());
    	Log.d("++++ contexto: ", contexto.toString());
        //inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.db=new LugaresDAO(contexto);
        this.Lugares=db.selectAll();
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
	    	 holder.foto.setOnClickListener(this);
	    	 
	    	 holder.nombre = (TextView) view.findViewById(R.id.nombre);
	    	 holder.nombre.setOnClickListener(this);
	    	 
	    	 holder.descripcion = (TextView) view.findViewById(R.id.descripcion);
	    	 holder.descripcion.setOnClickListener(this);
	    	 
	    	 view.setTag(holder);
	     }
	     else{
	    	 view = convertView;
			 holder = (ViewHolder)view.getTag();
	     }
	     
	     Lugar lugar = Lugares.get(posicion);
	    // holder.foto.setImageBitmap(lugar.getFoto());
	     holder.nombre.setText(lugar.getNombreLugar());  
	     holder.descripcion.setText(lugar.getDescrLugar());   
	       
	     return view;
	}
	
	private class ViewHolder {
		public ImageView foto;
		public TextView nombre, descripcion;
	}

	@Override
	public void onClick(View v) {	
		//Lanza la actividad EditarLugarActivity
		Log.d("++++ onClick: ", this.context.toString());
		Intent editarLugar = new Intent(this.context, EditarLugarActivity.class);
		context.startActivity(editarLugar);
		
	}
}

