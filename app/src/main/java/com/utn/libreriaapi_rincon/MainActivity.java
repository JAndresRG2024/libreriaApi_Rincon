package com.utn.libreriaapi_rincon;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText txtId,txtNombre,txtPais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtId = findViewById(R.id.txtId);
        txtNombre = findViewById(R.id.txtNombre);
        txtPais = findViewById(R.id.txtPais);
    }

    public void cmdCrear_onClick(View v)throws Exception {
        try {
            autor autor = new autor();
            autor.aut_id = Integer.parseInt(txtId.getText().toString());
            autor.aut_nombre = txtNombre.getText().toString();
            autor.aut_pais = txtPais.getText().toString();
            autor nuevo = ApiClient.CreateAutor(autor);
            if(nuevo != null){
                Toast.makeText(this, "Autor creado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al crear el autor", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al crear el autor", Toast.LENGTH_SHORT).show();
        }
    }
    public void cmdLeer_onClick(View v) throws Exception {
        try {
            int id = Integer.parseInt(txtId.getText().toString());
            autor autor = ApiClient.getAutor(id);
            if (autor != null) {
                txtNombre.setText(autor.aut_nombre);
                txtPais.setText(autor.aut_pais);
            } else {
                Toast.makeText(this, "Autor no encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al leer el autor", Toast.LENGTH_SHORT).show();
        }
    }
    public void cmdActualizar_onClick(View v) throws Exception {
        try {
            autor autor = new autor();
            autor.aut_id = Integer.parseInt(txtId.getText().toString());
            autor.aut_nombre = txtNombre.getText().toString();
            autor.aut_pais = txtPais.getText().toString();
            ApiClient.UpdateAutor(autor);
            Toast.makeText(this, "Autor actualizado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al actualizar el autor", Toast.LENGTH_SHORT).show();
        }
    }
    public void cmdEliminar_onClick(View v) throws Exception {
        try {
            ApiClient.DeleteAutor(Integer.parseInt(txtId.getText().toString()));
            txtId.setText("");
            txtNombre.setText("");
            txtPais.setText("");
            Toast.makeText(this, "Autor eliminado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar el autor", Toast.LENGTH_SHORT).show();
        }
    }
}