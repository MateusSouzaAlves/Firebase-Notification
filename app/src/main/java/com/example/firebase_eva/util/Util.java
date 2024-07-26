package com.example.firebase_eva.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

public class Util {



    public static boolean statusInternet_MoWi(Context context) {

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conexao != null) {
            // PARA DISPOSTIVOS NOVOS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities recursosRede = conexao.getNetworkCapabilities(conexao.getActiveNetwork());
                if (recursosRede != null) {//VERIFICAMOS SE RECUPERAMOS ALGO
                    if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        //VERIFICAMOS SE DISPOSITIVO TEM 3G
                        return true;
                    } else if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        //VERIFICAMOS SE DISPOSITIVO TEM WIFFI
                        return true;
                    }
                    //NÃO POSSUI UMA CONEXAO DE REDE VÁLIDA
                    return false;
                }
            } else {//COMECO DO ELSE
                // PARA DISPOSTIVOS ANTIGOS  (PRECAUÇÃO)
                NetworkInfo informacao = conexao.getActiveNetworkInfo();

                if (informacao != null && informacao.isConnected()) {
                    return true;
                } else
                    return false;


            }//FIM DO ELSE
        }


        return false;
    }








    public static String getTopico(Context context, String id, String nomeTopico){


        SharedPreferences preferences = context.getSharedPreferences(nomeTopico,0);

        String valor = preferences.getString(id,"");

        return valor;



    }


    public static void setTopico(Context context, String id, String nomeTopico){


        SharedPreferences preferences = context.getSharedPreferences(id,0);

        SharedPreferences.Editor editor = preferences.edit();


        editor.putString(id,nomeTopico);

        editor.commit();

    }








}
