package es.exitae.ejerciciofinal.activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

public class AdministrarCamara {

	private Context ctx;

	public AdministrarCamara(Context ct){
		this.ctx=ct;
	}

	public void asignarFotoView(ImageView iFoto, String filePath, int with){
		BitmapFactory.Options options 	= new BitmapFactory.Options();
        options.inJustDecodeBounds 		= true;
        InputStream is = null;
        try {
            is = this.ctx.getContentResolver().openInputStream(Uri.parse(filePath));
            BitmapFactory.decodeStream(is, null, options);
            is.close();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* asociamos el bitmap al imageview */
        iFoto.setImageBitmap(scaleImage(options,Uri.parse(filePath),with));
		
	}
	
	
	//escalando imagen
	private Bitmap scaleImage(BitmapFactory.Options options, Uri uri, int targetWidth) {
        
        Bitmap bitmap 		= null;
        double ratioWidth 	= ((float) targetWidth) / (float) options.outWidth;
        double ratioHeight 	= ((float) targetWidth) / (float) options.outHeight;
        double ratio 		= Math.min(ratioWidth, ratioHeight);
        int dstWidth 		= (int) Math.round(ratio * options.outWidth);
        int dstHeight 		= (int) Math.round(ratio * options.outHeight);
        
        ratio = Math.floor(1.0 / ratio);
        int sample = nearest2pow((int) ratio);
 
        options.inJustDecodeBounds = false;
        if (sample <= 0) {
            sample = 1;
        }
        options.inSampleSize = (int) sample;
        options.inPurgeable = true;
        try {
            InputStream is;
            is 		= this.ctx.getContentResolver().openInputStream(uri);
            bitmap 	= BitmapFactory.decodeStream(is, null, options);
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, dstWidth,dstHeight, true);
            bitmap = bitmap2;
            is.close();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        return bitmap;
    }
	
	private static int nearest2pow(int value) {
        return value == 0 ? 0  : (32 - Integer.numberOfLeadingZeros(value - 1)) / 2;
    }
}
