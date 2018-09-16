package e.valka.dibujar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
    private static int activado = 1;
    private final int colorCodigo = 1001;
    private float x1, y1, x2, y2, a1, b1, a2, b2;
    private float m1,m2,grados;
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
        Matrix inverse = new Matrix();
        imagen.setOnTouchListener((view,motionEvent)->{
            String message = "";

            switch (motionEvent.getActionMasked ()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX ();
                    y1 = motionEvent.getY ();
                    break;
                case MotionEvent.ACTION_UP:
                    a1 = motionEvent.getX (0);
                    b1 = motionEvent.getY (0);
                    m2 = (a2-a1)/(b2-b1);
                    grados = (float)Math.toDegrees((float)Math.atan((m2-m1)/(1+(m1*m2))));
                    if(activado == 0){
                        imagen.setRotation(imagen.getRotation() + grados);
                        imagen.invalidate();
                        Toast.makeText (this, "Grados: "+grados, Toast.LENGTH_SHORT).show ();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(activado == 1){
                        Bitmap bitmap = ((BitmapDrawable)imagen.getDrawable()).getBitmap().copy(Bitmap.Config.ARGB_8888,true);
                        Canvas canvas = new Canvas(bitmap);
                        Paint color = new Paint();
                        color.setColor(rgb(rgb[0],rgb[1],rgb[2]));
                        imagen.setImageBitmap(bitmap);
                        int diferencia = (view.getHeight()-bitmap.getHeight())/4;
                        canvas.drawCircle(((motionEvent.getX()*100)/bitmap.getWidth()),((((motionEvent.getY()-diferencia)*100)/bitmap.getHeight())),5,color);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    x2 = motionEvent.getX (motionEvent.getActionIndex ());
                    y2 = motionEvent.getY (motionEvent.getActionIndex ());
                    m1 = (y2-y1)/(x2-x1);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    a2 = motionEvent.getX (motionEvent.getActionIndex ());
                    b2 = motionEvent.getY (motionEvent.getActionIndex ());
                    break;
            }

            return true;
        });
    }
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
            case R.id.mnu_item_two:
                if(activado == 1){
                    Toast.makeText (this, "Colorear desactivado.", Toast.LENGTH_SHORT).show ();
                    activado = 0;
                }else{
                    Toast.makeText (this, "Colorear activado.", Toast.LENGTH_SHORT).show ();
                    activado = 1;
                }
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
