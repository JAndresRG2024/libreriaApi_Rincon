package com.utn.libreriaapi_rincon;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText txtId, txtCampo2, txtCampo3;
    private TextInputLayout tilCampo2, tilCampo3;
    private CheckBox chkPrincipal;
    private Spinner spnTablas;
    private TableLayout tlDatos;
    private String tablaSeleccionada = "Autor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Configurar el padding para EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtId = findViewById(R.id.txtId);
        txtCampo2 = findViewById(R.id.txtCampo2);
        txtCampo3 = findViewById(R.id.txtCampo3);
        tilCampo2 = findViewById(R.id.tilCampo2);
        tilCampo3 = findViewById(R.id.tilCampo3);
        chkPrincipal = findViewById(R.id.chkPrincipal);
        spnTablas = findViewById(R.id.spnTablas);
        tlDatos = findViewById(R.id.tlDatos);

        setupSpinner();
    }

    private void setupSpinner() {
        String[] opciones = {"Autor", "Libro", "Autor_Libro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTablas.setAdapter(adapter);

        spnTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tablaSeleccionada = opciones[position];
                actualizarUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void actualizarUI() {
        txtId.setText("");
        txtCampo2.setText("");
        txtCampo3.setText("");
        chkPrincipal.setChecked(false);
        tlDatos.removeAllViews();

        switch (tablaSeleccionada) {
            case "Autor":
                tilCampo2.setHint("Nombre");
                tilCampo3.setHint("País");
                tilCampo3.setVisibility(View.VISIBLE);
                chkPrincipal.setVisibility(View.GONE);
                break;
            case "Libro":
                tilCampo2.setHint("Título");
                tilCampo3.setHint("Editorial");
                tilCampo3.setVisibility(View.VISIBLE);
                chkPrincipal.setVisibility(View.GONE);
                break;
            case "Autor_Libro":
                tilCampo2.setHint("ID Autor");
                tilCampo3.setHint("ID Libro");
                tilCampo3.setVisibility(View.VISIBLE);
                chkPrincipal.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void cmdCrear_onClick(View v) {
        try {
            String idStr = txtId.getText().toString();
            int id = idStr.isEmpty() ? 0 : Integer.parseInt(idStr);

            if (tablaSeleccionada.equals("Autor")) {
                autor a = new autor();
                a.aut_id = id;
                a.aut_nombre = txtCampo2.getText().toString();
                a.aut_pais = txtCampo3.getText().toString();
                ApiClient.CreateAutor(a);
            } else if (tablaSeleccionada.equals("Libro")) {
                Libro l = new Libro();
                l.lib_id = id;
                l.lib_titulo = txtCampo2.getText().toString();
                l.lib_editorial = txtCampo3.getText().toString();
                ApiClient.createLibro(l);
            } else {
                autor_libro al = new autor_libro();
                al.aut_lib_id = id;
                al.aut_id = Integer.parseInt(txtCampo2.getText().toString());
                al.lib_id = Integer.parseInt(txtCampo3.getText().toString());
                al.aut_lib_autor_principal = chkPrincipal.isChecked();
                ApiClient.createAutorLibro(al);
            }
            Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
            txtId.setText("");
            txtCampo2.setText("");
            txtCampo3.setText("");
            cmdListar_onClick(null);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cmdLeer_onClick(View v) {
        try {
            String idStr = txtId.getText().toString();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(idStr);
            if (tablaSeleccionada.equals("Autor")) {
                autor a = ApiClient.getAutor(id);
                if (a != null) {
                    txtCampo2.setText(a.aut_nombre);
                    txtCampo3.setText(a.aut_pais);
                } else Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
            } else if (tablaSeleccionada.equals("Libro")) {
                Libro l = ApiClient.getLibro(id);
                if (l != null) {
                    txtCampo2.setText(l.lib_titulo);
                    txtCampo3.setText(l.lib_editorial);
                } else Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
            } else {
                autor_libro al = ApiClient.getAutorLibro(id);
                if (al != null) {
                    txtCampo2.setText(String.valueOf(al.aut_id));
                    txtCampo3.setText(String.valueOf(al.lib_id));
                    chkPrincipal.setChecked(al.aut_lib_autor_principal);
                } else Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cmdActualizar_onClick(View v) {
        try {
            String idStr = txtId.getText().toString();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "El ID es obligatorio para actualizar", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(idStr);

            if (tablaSeleccionada.equals("Autor")) {
                autor a = new autor();
                a.aut_id = id;
                a.aut_nombre = txtCampo2.getText().toString();
                a.aut_pais = txtCampo3.getText().toString();
                ApiClient.UpdateAutor(a);
            } else if (tablaSeleccionada.equals("Libro")) {
                Libro l = new Libro();
                l.lib_id = id;
                l.lib_titulo = txtCampo2.getText().toString();
                l.lib_editorial = txtCampo3.getText().toString();
                ApiClient.updateLibro(l);
            } else {
                autor_libro al = new autor_libro();
                al.aut_lib_id = id;
                al.aut_id = Integer.parseInt(txtCampo2.getText().toString());
                al.lib_id = Integer.parseInt(txtCampo3.getText().toString());
                al.aut_lib_autor_principal = chkPrincipal.isChecked();
                ApiClient.updateAutorLibro(al);
            }
            Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
            cmdListar_onClick(null);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cmdEliminar_onClick(View v) {
        try {
            int id = Integer.parseInt(txtId.getText().toString());
            if (tablaSeleccionada.equals("Autor")) {
                ApiClient.DeleteAutor(id);
            } else if (tablaSeleccionada.equals("Libro")) {
                ApiClient.deleteLibro(id);
            } else {
                ApiClient.deleteAutorLibro(id);
            }
            Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
            actualizarUI();
            cmdListar_onClick(null);
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void cmdListar_onClick(View v) {
        try {
            tlDatos.removeAllViews();
            if (tablaSeleccionada.equals("Autor")) {
                List<autor> lista = ApiClient.getAutores();
                agregarFilaHeader("ID", "Nombre", "País");
                if (lista != null) {
                    int i = 0;
                    for (autor a : lista) {
                        agregarFila(i++, String.valueOf(a.aut_id), a.aut_nombre, a.aut_pais);
                    }
                }
            } else if (tablaSeleccionada.equals("Libro")) {
                List<Libro> lista = ApiClient.getLibros();
                agregarFilaHeader("ID", "Título", "Editorial");
                if (lista != null) {
                    int i = 0;
                    for (Libro l : lista) {
                        agregarFila(i++, String.valueOf(l.lib_id), l.lib_titulo, l.lib_editorial);
                    }
                }
            } else {
                List<autor_libro> lista = ApiClient.getAutoresLibros();
                agregarFilaHeader("Libro", "Autor");
                if (lista != null) {
                    int i = 0;
                    for (autor_libro al : lista) {
                        // Priorizar los nombres que vienen del JOIN. 
                        // Si son nulos (porque el API cambió o no se mapearon), mostrar el ID.
                        String libro = (al.lib_titulo != null && !al.lib_titulo.isEmpty()) ? al.lib_titulo : "ID: " + al.lib_id;
                        String autor = (al.aut_nombre != null && !al.aut_nombre.isEmpty()) ? al.aut_nombre : "ID: " + al.aut_id;
                        agregarFila(i++, libro, autor);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al listar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void agregarFilaHeader(String... columns) {
        TableRow row = new TableRow(this);
        row.setBackgroundColor(Color.parseColor("#3F51B5")); // Primary Color
        for (String text : columns) {
            TextView tv = new TextView(this);
            tv.setText(text);
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setPadding(20, 20, 20, 20);
            tv.setGravity(Gravity.CENTER);
            row.addView(tv);
        }
        tlDatos.addView(row);
    }

    private void agregarFila(int index, String... columns) {
        TableRow row = new TableRow(this);
        // Color alternado
        if (index % 2 == 0) {
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            row.setBackgroundColor(Color.parseColor("#F2F2F2"));
        }
        
        for (String text : columns) {
            TextView tv = new TextView(this);
            tv.setText(text);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(20, 20, 20, 20);
            tv.setBackgroundResource(R.drawable.border);
            row.addView(tv);
        }
        tlDatos.addView(row);
    }
}
