package com.utn.libreriaapi_rincon;

import com.google.gson.annotations.SerializedName;

public class autor_libro {
    @SerializedName("aut_lib_id")
    public int aut_lib_id;
    
    @SerializedName("aut_id")
    public int aut_id;
    
    @SerializedName("lib_id")
    public int lib_id;
    
    @SerializedName("aut_lib_autor_principal")
    public boolean aut_lib_autor_principal;

    // Campos para visualización (vienen del JOIN en el API)
    @SerializedName("lib_titulo")
    public String lib_titulo;
    
    @SerializedName("aut_nombre")
    public String aut_nombre;
}
