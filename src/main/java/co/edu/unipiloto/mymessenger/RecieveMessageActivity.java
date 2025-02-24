package co.edu.unipiloto.mymessenger;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecieveMessageActivity extends Activity {
    public static final String EXTRA_MESSAGE = "chat_message";
    private TextView textViewChat;
    private EditText escribirRespuesta;
    private List<String> chat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_message);

        textViewChat = findViewById(R.id.textViewChat);
        escribirRespuesta = findViewById(R.id.escribirRespuesta);

        String mensajeRecibido = getIntent().getStringExtra(EXTRA_MESSAGE);
        if (mensajeRecibido != null) {
            textViewChat.setText("Propietario: " + mensajeRecibido);
        }

        cargarConversacion();
    }

    public void responderMensaje(View view){
        String respuesta = escribirRespuesta.getText().toString();
        if(!respuesta.isEmpty()){
            //cargarConversacion();
            chat.add("Cuidador: " + respuesta);
            guardarMensajes();
            finish();
        }
    }

    private void guardarMensajes(){
        SharedPreferences prefs = getSharedPreferences("ChatsPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String historial = prefs.getString("Historial_Chat", "");
        if (!chat.isEmpty()) {
            historial += ";" + chat.get(chat.size() - 1);
        }

        editor.putString("Historial_Chat", historial);
        editor.apply();
    }

    private void cargarConversacion(){
        SharedPreferences prefs = getSharedPreferences("ChatsPrefs", MODE_PRIVATE);
        String historial = prefs.getString("Historial_Chat", "");
        if (!historial.isEmpty()) {
            chat.clear();
            chat.addAll(Arrays.asList(historial.split(";")));

            StringBuilder historialTexto = new StringBuilder();
            for (String mensaje : chat) {
                historialTexto.append(mensaje).append("\n");
            }
            textViewChat.setText(historialTexto.toString());
        }

    }
}