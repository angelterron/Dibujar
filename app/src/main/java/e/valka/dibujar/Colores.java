package e.valka.dibujar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Colores extends AppCompatActivity {
    @BindView(R.id.rojo) TextView rojo;
    @BindView(R.id.sRojo) SeekBar sRojo;
    @BindView(R.id.verde) TextView verde;
    @BindView(R.id.sVerde) SeekBar sVerde;
    @BindView(R.id.azul) TextView azul;
    @BindView(R.id.sAzul) SeekBar sAzul;
    @BindView(R.id.color) ImageView color;
    @BindView(R.id.boton) Button boton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colores);
        ButterKnife.bind(this);
        color.setBackgroundColor(Color.rgb(sRojo.getProgress(),sVerde.getProgress(),sAzul.getProgress()));
        SeekBar.OnSeekBarChangeListener escucha = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (seekBar.getId()){
                    case R.id.sRojo:
                        rojo.setText(""+seekBar.getProgress());
                        break;
                    case R.id.sVerde:
                        verde.setText(""+seekBar.getProgress());
                        break;
                    case R.id.sAzul:
                        azul.setText(""+seekBar.getProgress());
                        break;
                }
                color.setBackgroundColor(Color.rgb(sRojo.getProgress(),sVerde.getProgress(),sAzul.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        sRojo.setOnSeekBarChangeListener(escucha);
        sVerde.setOnSeekBarChangeListener(escucha);
        sAzul.setOnSeekBarChangeListener(escucha);
        boton.setOnClickListener((view)->{
            int[] rgb = new int[3];
            rgb[0] = sRojo.getProgress();
            rgb[1] = sVerde.getProgress();
            rgb[2] = sAzul.getProgress();
            Intent inten = new Intent();
            inten.putExtra(MainActivity.colorDatos,rgb);
            setResult(Activity.RESULT_OK,inten);
            finish();
        });


    }

}
