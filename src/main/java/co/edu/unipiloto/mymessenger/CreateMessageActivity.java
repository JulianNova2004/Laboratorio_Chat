package co.edu.unipiloto.mymessenger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class CreateMessageActivity extends Activity {

    public static final String EXTRA_MESSAGE = "chat_message";
    private LinearLayout chatLayout;
    private EditText escribirMensaje;
    private List<String> chat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        chatLayout = findViewById(R.id.chatLayout);
        escribirMensaje = findViewById(R.id.escribirMensaje);

        cargarConversacion();
    }

    public void onSendMessage(View view) {
        String messageText = escribirMensaje.getText().toString();
        if(!messageText.isEmpty()){
            chat.add("Propietario: " + messageText);
            agregarMensajes("Propietario: " + messageText);
            guardarMensajes();

            Intent intent = new Intent(this, RecieveMessageActivity.class);

            intent.putExtra(EXTRA_MESSAGE, messageText);

            startActivity(intent);
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        cargarConversacion();

        chatLayout.removeAllViews();
        for (String mensaje : chat) {
            agregarMensajes(mensaje);
        }
    }

    private void agregarMensajes(String mensaje){
        TextView textView = new TextView(this);
        textView.setText(mensaje);
        textView.setPadding(10, 5, 10, 5);
        chatLayout.addView(textView);
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
        chatLayout.removeAllViews();

        if(!historial.isEmpty()){
            chat.clear();
            chat.addAll(Arrays.asList(historial.split(";")));

            for(String mensaje: chat){
                agregarMensajes(mensaje);
            }
        }
    }


}