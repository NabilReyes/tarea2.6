package com.example.tarea26;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etUsuario, etContrasena;
    Button btnLogin;
    TextView tvResultado;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        tvResultado = findViewById(R.id.tvResultado);

        btnLogin.setOnClickListener(view -> login());
    }

    private void login() {
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            tvResultado.setText("Por favor completa todos los campos");
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/miapi/login.php"; // usa esta IP para emulador

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject json = new JSONObject(response);
                        if (json.has("token")) {
                            token = json.getString("token");
                            obtenerProductos();
                        } else {
                            tvResultado.setText("Credenciales inválidas");
                        }
                    } catch (JSONException e) {
                        tvResultado.setText("Error al procesar la respuesta");
                        e.printStackTrace();
                    }
                },
                error -> {
                    tvResultado.setText("Error de red: " + error.toString());
                    error.printStackTrace();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("contrasena", contrasena);
                return params;
            }
        };

        queue.add(postRequest);
    }

    private void obtenerProductos() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2/miapi/productos.php";

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                response -> tvResultado.setText("Productos:\n" + response),
                error -> {
                    tvResultado.setText("Error al obtener productos");
                    error.printStackTrace();
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(getRequest);
    }
}
