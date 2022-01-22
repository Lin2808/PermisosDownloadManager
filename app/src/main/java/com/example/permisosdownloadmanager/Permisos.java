package com.example.permisosdownloadmanager;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;


//Hay que extender de AppCompatActivity
public class Permisos extends Activity {

    private Activity activity;
    public void Permisos(Activity activity)
    {
        this.activity = activity;
    }

    public ArrayList<String> permisosNoAprobados(ArrayList<String> listaPermisos)
    {
        ArrayList<String> list = new ArrayList<String>();
        try {

            for (String permiso : listaPermisos) {
                //Verifica la versión del SDK
                if (Build.VERSION.SDK_INT >= 23) {
                    int revisarPermiso = ContextCompat.checkSelfPermission(MainActivity.mainActivity, permiso);
                    //Verifica si tiene o no los permisos concedidos
                    if (revisarPermiso != PackageManager.PERMISSION_GRANTED) ;
                    {
                        //Si no están concedidos los permisos, los agrega a una lista
                        list.add(permiso);
                    }
                }
            }
            return list;
        }
        catch(Exception ex)
        {
            String mensaje = ex.toString();
        }
        return list;
    }

    public void otorgarPermisos(ArrayList<String> permisosSolicitados)
    {
        try
        {
            ArrayList<String> listaPermisosNoAprobados = permisosNoAprobados(permisosSolicitados);
            if (listaPermisosNoAprobados.size()>0)
            {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    //LLama al cuadro de diálogo para conceder o denega permisos
                    //en donde aparecerán todos los permisos que se van a solicitar
                    ActivityCompat.requestPermissions(MainActivity.mainActivity, listaPermisosNoAprobados.toArray(new String[listaPermisosNoAprobados.size()]), 1);
                }
            }
        }
        catch(Exception ex)
        {
            String mensaje = ex.toString();
        }
    }

    public void BajarDoc(String nombre, String url)
    {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Descargando " + nombre);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Descargando");
        request.setVisibleInDownloadsUi(true);
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        //request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //}
        request.setDestinationInExternalFilesDir(this.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "file.pdf");
        //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filedownload.pdf");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        try
        {
            manager.enqueue(request);
        }
        catch (Exception e)
        {
            Toast.makeText(this.getApplicationContext(),"Error: "  + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
