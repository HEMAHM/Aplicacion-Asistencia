package com.example.asistencia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

public class inicio extends AppCompatActivity {
    private Executor executor;
    private androidx.biometric.BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    Button cerrar;

    TextView nombre_completo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        cerrar = findViewById(R.id.btnA3Cerrar);
        nombre_completo = findViewById(R.id.tvA3nombrecom);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inicio.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG |
                BiometricManager.Authenticators.DEVICE_CREDENTIAL)){
            case BiometricManager.BIOMETRIC_SUCCESS:
                Toast.makeText(this, "SE PUEDE USAR EL BIOMETRICO", Toast.LENGTH_LONG).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "NO HAY DISPOSITIVO BIOMETRICO", Toast.LENGTH_LONG).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "DISPOSITIVO BIOMETRICO NO DISPONIBLE", Toast.LENGTH_LONG).show();
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "CREE CREDENCIALES NECESARIAS", Toast.LENGTH_LONG).show();
                break;
        }
        lanzar_Biometrico();
    }
    private void lanzar_Biometrico() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new androidx.biometric.BiometricPrompt(this, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(inicio.this, "ERROR EN EL EXECUTOR BIOMETRICO",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull androidx.biometric.BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                registrarAsistencia();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(inicio.this, "ERROR EN LA AUTENTICACION",Toast.LENGTH_LONG).show();
            }
        });
        promptInfo = new androidx.biometric.BiometricPrompt.PromptInfo.Builder()
                .setTitle("CONFIRMA TU ASISTENCIA")
                .setSubtitle("Presiona el sensor de huella para confirmar su asistencia")
                .setNegativeButtonText("USE PASSWORD PARA VALIDAR")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

    private void registrarAsistencia() {
        // Obtener los datos del usuario desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UsuarioSesion", MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", null);
        String apellidop = sharedPreferences.getString("apellidop", null);
        String apellidom = sharedPreferences.getString("apellidom", null);

        String nombreCompleto = nombre + " " + apellidop + " " + apellidom;

        nombre_completo.setText(nombreCompleto);

        // Obtener la fecha y hora actuales
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String fechaHora = sdf.format(new Date());

        // URL del archivo PHP que procesará la solicitud
        String URL = "http://192.168.1.90/asistencia/reg_asistencia.php"; // Asegúrate de usar la IP correcta y el archivo PHP correcto

        // Realizar la solicitud de inserción usando Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {
                        // Procesar la respuesta (puedes agregar verificaciones según lo que devuelva el servidor)
                        JSONObject jsonObject = new JSONObject(response);
                        String resultado = jsonObject.getString("resultado");
                        if ("exito".equals(resultado)) {
                            Toast.makeText(getApplicationContext(), "ASISTENCIA REGISTRADA", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al registrar la asistencia", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // Manejo de errores
                        Toast.makeText(getApplicationContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", e.toString());
                    }
                },
                error -> {
                    // Manejo de errores en la conexión
                    Toast.makeText(getApplicationContext(), "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Volley", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Enviar los parámetros al servidor
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("apellidop", apellidop);
                params.put("apellidom", apellidom);
                params.put("fecha", fechaHora);  // Fecha y hora actual
                params.put("hora", fechaHora);   // Fecha y hora actual (se puede separar si es necesario)
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}