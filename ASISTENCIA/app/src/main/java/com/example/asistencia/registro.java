package com.example.asistencia;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    EditText nombre, apellidop, apellidom, usuario, contraseña;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.txtA2nombre);
        apellidop = findViewById(R.id.txtA2app);
        apellidom = findViewById(R.id.txtA2apm);
        usuario = findViewById(R.id.txtA2usuario);
        contraseña = findViewById(R.id.txtA2contrasena);

        registrar = findViewById(R.id.btnA2registrar);

        registrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String URL = "http://192.168.1.90/asistencia/registro.php"; // Cambia la URL al endpoint de tu servidor

        // Obtener los datos del formulario
        String nombreText = nombre.getText().toString().trim();
        String apellidopText = apellidop.getText().toString().trim();
        String apellidomText = apellidom.getText().toString().trim();
        String usuarioText = usuario.getText().toString().trim();
        String contraseñaText = contraseña.getText().toString().trim();

        // Validar que no haya campos vacíos
        if (nombreText.isEmpty() || apellidopText.isEmpty() || apellidomText.isEmpty() || usuarioText.isEmpty() || contraseñaText.isEmpty()) {
            Toast.makeText(registro.this, "Lene todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear la solicitud POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Verificar la respuesta del servidor
                        if (response.contains("Error")) {
                            Toast.makeText(registro.this, "Hubo un error al registrar", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(registro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                            finish(); // Cerrar la actividad de registro y regresar a la anterior
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(registro.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Enviar los datos del formulario al servidor
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombreText);
                params.put("apellidop", apellidopText);
                params.put("apellidom", apellidomText);
                params.put("usuario", usuarioText);
                params.put("contrasena", contraseñaText);
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
