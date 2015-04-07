package es.exitae.ejerciciofinal.activity;

import es.exitae.ejerciciofinal.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PrincipalActivity extends Activity implements OnClickListener {
	
	private Button btnLista, btnMapa;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		btnLista = (Button) findViewById(R.id.btnLista);
		btnLista.setOnClickListener(this);
		
		
		btnMapa = (Button) findViewById(R.id.btnMapa);
		btnMapa.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
