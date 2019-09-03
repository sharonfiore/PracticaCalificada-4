package com.example.lectorbarras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView informacion;
    Button copiar;
    String Info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configurarLector();

        informacion = (TextView) findViewById(R.id.tvresult);
        copiar =(Button) findViewById(R.id.btn_copiar);

        copiar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Info = informacion.getText().toString();

        String separadores = "[\\ \\, \\.]";
        String[] arreglo = Info.split(separadores);

        if(1==0){
            Uri url = Uri.parse(""+Info+"");
            Intent intent = new Intent(Intent.ACTION_VIEW, url);
            startActivity(intent);

        }
        else{
        ClipData clipp = ClipData.newPlainText("Texto",Info);
        ClipboardManager clipboard = (ClipboardManager)this.getSystemService(CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(clipp);
        }
    }

    private void configurarLector(){

        final ImageButton imageButton = (ImageButton)findViewById(R.id.img_button);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
    }

    private void actualizarUITextViews(String resultadoScaneo, String formatoResultado){

        ((TextView)findViewById(R.id.tvFormat)).setText(formatoResultado);
        ((TextView)findViewById(R.id.tvresult)).setText(resultadoScaneo);
    }

    private void manipularResultado(IntentResult intentResult){
        if(intentResult != null){
            actualizarUITextViews(intentResult.getContents(),intentResult.getFormatName()); /*Tambien se puede escanear Cod. barras*/
        }
        else{
            Toast.makeText(getApplicationContext(),"No se leyó nada",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        final IntentResult intentResult =  IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        manipularResultado(intentResult);
    }


}
