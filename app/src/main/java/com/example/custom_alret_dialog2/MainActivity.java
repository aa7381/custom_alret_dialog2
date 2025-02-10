package com.example.custom_alret_dialog2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] arr = new String[20];
    AlertDialog.Builder adb;
    ArrayAdapter<String> adapter;

    double kefel=0;
    double x12=0;
    int num = 1 ;

    double sum= 0 ;
    int position =0;
    Button btn_enter, ad_han, ad_hes;
    boolean sedra = false;
    String error = "Invalid input please enter again number";


    String str = "";
    String str2 = "";
    ListView data_list;
    LinearLayout dialog_custom;

    EditText ab_a1, ab_kef;
    TextView x1, d, n, Sn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_enter = findViewById(R.id.btn_enter);
        data_list = findViewById(R.id.data_list);
        x1 = findViewById(R.id.x1);
        d = findViewById(R.id.d);
        n = findViewById(R.id.n);
        Sn = findViewById(R.id.Sn);

        data_list.setOnItemClickListener(this);

        adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arr);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void handsit(View view)
    {
        sedra = false;
    }
    public void hesbonit(View view )
    {
        sedra = true ;
    }

    public void enter_alert(View view) {
        dialog_custom = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_alret,null );
        ad_han = (Button) dialog_custom.findViewById(R.id.ad_han);
        ad_hes = (Button) dialog_custom.findViewById(R.id.ad_hes);
        ab_a1 = (EditText) dialog_custom.findViewById(R.id.ab_a1);
        ab_kef = (EditText) dialog_custom.findViewById(R.id.ab_kef);

        adb = new AlertDialog.Builder(this);
        adb.setView(dialog_custom);

        adb.setPositiveButton("enter ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                str = ab_a1.getText().toString();
                str2 = ab_kef.getText().toString();
                if (str.isEmpty() || str.equals("-") || str.equals("-.") || str.equals("+") || str.equals("+.") || str2.isEmpty() || str2.equals("-") || str2.equals("-.") || str2.equals("+") || str2.equals("+."))
                {
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    kefel= Double.parseDouble(str2);
                    x12 = Double.parseDouble(str);
                    for (int i = 0; i <20; i++) {
                        double term;
                        if (sedra) {
                            term = x12 + i * kefel;
                        } else {
                            term = x12 * Math.pow(kefel, i);
                        }
                        if(term>= 1000000 )
                        {
                            arr[i] = String.valueOf(bigNumSimplifier(term));
                        }
                        else
                        {
                            arr[i] = String.valueOf(term);
                        }
                    }
                    data_list.setAdapter(adapter);

                }
            }
        });
        adb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.setNeutralButton("reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ab_a1.setText("enter the first num ");
                kefel = 0 ;
                ab_kef.setText("enter the kefel");
                x12 = 0 ;
                dialog.cancel();
            }

        });
        adb.setCancelable(false);
        AlertDialog ad = adb.create();
        ad.show();

    }


    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long rowid)
    {
        x1.setText("x1=" + str);
        d.setText("d= "+ str2);
        if(sedra)
        {
            n.setText("n= hesbonit");
        } else {
            n.setText("n=  handsit");
        }
        Sn.setText("Sn= " + arr[pos]);
        }

    public boolean onOptionsItemSelected(MenuItem menu)
    {
        String num1 = menu.getTitle().toString();
        if(num1.equals("home"))
        {
            if(num ==1 )
            {

            }
            else
                finish();
        }
        else
        {
            Intent si = new Intent(this, Page2.class);
            startActivity(si);

        }

        return super.onOptionsItemSelected(menu);

    }

    public  String bigNumSimplifier(double result) {
        String scientificNotation = String.format("%.4e", result);
        String[] parts = scientificNotation.split("e");
        double base = Double.parseDouble(parts[0]) / 10.0;
        int exponent = Integer.parseInt(parts[1]) + 1;
        return String.format("%.4f * 10^%d", base, exponent);
    }

}