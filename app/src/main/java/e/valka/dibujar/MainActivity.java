package e.valka.dibujar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyTouchEvent";
    public static final String colorDatos = "colores";
    private final int colorCodigo = 1001;
    private int[] rgb = {0,0,0};
    @BindView(R.id.imagen)ImageView imagen;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById (R.id.toolbar);
        toolbar.setTitle ("Colorear");
        setSupportActionBar (toolbar);
        imagen.setOnTouchListener((view,motionEvent)->{
            String message = "";

            switch (motionEvent.getAction ()) {
                case MotionEvent.ACTION_MOVE:
                    Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap().copy(Bitmap.Config.ARGB_8888,true);
                    Canvas canvas = new Canvas(bitmap);
                    Paint color = new Paint();
                    color.setColor(rgb(rgb[0],rgb[1],rgb[2]));
                    imagen.setImageBitmap(bitmap);
                    int diferencia = (view.getHeight()-bitmap.getHeight())/4;
                        canvas.drawCircle(((motionEvent.getX()*100)/bitmap.getWidth()),((((motionEvent.getY()-diferencia)*100)/bitmap.getHeight())),5,color);
                    break;
            }

            return super.onTouchEvent (motionEvent);
        });
    }
    /*case MotionEvent.ACTION_MOVE:
                      Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap();
                      Canvas canvas = new Canvas(bitmap);
                      Paint color = new Paint();
                      color.setColor(rgb(rgb[0],rgb[1],rgb[2]));
                      canvas.drawCircle(motion.getX(),motion.getY(),100,color);
                      imagen.setImageBitmap(bitmap);
                        message = String.format (Locale.US, "MOVING on (%.2f, %.2f)", motion.getX (), motion.getY ());
                        Log.i ("HOLA", message);
                    break;*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.mnu_item_one:
                Intent colores = new Intent(this,Colores.class);
                startActivityForResult(colores,colorCodigo);
                break;
        }
        return super.onOptionsItemSelected (item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case colorCodigo:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    rgb = data.getIntArrayExtra(colorDatos);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
