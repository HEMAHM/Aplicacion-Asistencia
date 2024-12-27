package com.example.asistencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText usuario, contrasena;
    Button ingresar, registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.txtA1usuario);
        contrasena = findViewById(R.id.txtA1contrasena);
        ingresar = findViewById(R.id.btnA1ingresar);
        registrar = findViewById(R.id.btnA1registrar);

        // Acción del botón Registrar
        registrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, registro.class);
            startActivity(intent);
        });

        // Acción del botón Ingresar
        ingresar.setOnClickListener(v -> {
            String usu = usuario.getText().toString().trim();
            String con = contrasena.getText().toString().trim();

            if (usu.isEmpty() || con.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                validarUsuario("http://192.168.1.90/asistencia/login.php", usu, con);
            }
        });
    }

    private void validarUsuario(String URL, String user, String pass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        // Intenta parsear el JSON
                        JSONObject jsonObject = new JSONObject(response);

                        // Verifica si la respuesta contiene un error
                        if (jsonObject.has("error")) {
                            String errorMessage = jsonObject.getString("error");
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            // Si no hay error, guarda los datos del usuario en SharedPreferences
                            String nombre = jsonObject.getString("nombre");        // Suponiendo que el JSON tiene este campo
                            String apellidop = jsonObject.getString("apellidop");
                            String apellidom = jsonObject.getString("apellidom");
                            String usuario = jsonObject.getString("usuario");

                            // Guardar los datos en SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UsuarioSesion", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("nombre", nombre);
                            editor.putString("apellidop", apellidop);
                            editor.putString("apellidom", apellidom);
                            editor.putString("usuario", usuario);
                            editor.apply(); // Aplicar los cambios

                            // Redirigir a la pantalla de inicio
                            Intent intent = new Intent(getApplicationContext(), inicio.class);
                            startActivity(intent);
                            finish(); // Finaliza esta actividad para que el usuario no regrese al login
                        }
                    } catch (JSONException e) {
                        // En caso de que haya un error al procesar el JSON
                        Toast.makeText(MainActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", e.toString());
                    }
                },
                error -> {
                    // Error en la conexión o en la petición
                    Toast.makeText(MainActivity.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Volley", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", user);
                params.put("contrasena", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
