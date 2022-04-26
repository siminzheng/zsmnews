package pager.realpager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zsmnews.R;

public class wodexinxi extends AppCompatActivity {
    Button xinxi_fanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wodexinxi);

        xinxi_fanhui=findViewById(R.id.xinxi_fanhui);

        xinxi_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}