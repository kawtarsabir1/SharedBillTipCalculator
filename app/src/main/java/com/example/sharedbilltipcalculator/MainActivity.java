package com.example.sharedbilltipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText price;
    Spinner spinner;
    EditText nb_people;
    EditText tip;
    TextView msgError;

    Button calculer;
    Double tipvalue;
    String spinnerType;
    Double total_Amount;
    Double tip_person;
    Double total_person;

    Double priceValue;
    Double number_person;

    Button logout;
    Switch darkModeSwitch ;
    ImageView menu;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menu
        linear = findViewById(R.id.myLinearLayout);

        // dark mode
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        Configuration configuration = getResources().getConfiguration();
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        if (isChecked) {
                            setNightMode(Configuration.UI_MODE_NIGHT_YES);
                        }
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        if (!isChecked) {
                            setNightMode(Configuration.UI_MODE_NIGHT_NO);
                        }
                        break;
                }
            }
        });






        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

        price= findViewById(R.id.price);
        spinner=findViewById(R.id.spinner1);
        nb_people=findViewById(R.id.nbpeople);
        tip=findViewById(R.id.tip1);
        calculer=findViewById(R.id.calcul);
        msgError=findViewById(R.id.error);

        String[] items = new String[]{"DH", "%"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                if(Objects.equals(selectedItem, "DH")){
                    spinnerType="DH";
                }
                else{
                    spinnerType="%";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerType="DH";
            }

        });

        calculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!price.getText().toString().isEmpty() && !nb_people.getText().toString().isEmpty() && !tip.getText().toString().isEmpty()){
                    priceValue = Double.valueOf(price.getText().toString());
                    number_person= Double.valueOf(nb_people.getText().toString());
                    if(Objects.equals(spinnerType, "DH")){
                        tipvalue = Double.valueOf(tip.getText().toString());

                    }
                    else{
                        Double porcentageValue= Double.valueOf(tip.getText().toString());
                        tipvalue=(priceValue*(porcentageValue))/100;
                        tipvalue = Double.valueOf(String.format("%.2f", tipvalue));
                    }

                    total_Amount=tipvalue+priceValue;
                    tip_person=tipvalue/number_person;
                    tip_person = Double.valueOf(String.format("%.2f", tip_person));
                    total_person=total_Amount/number_person;
                    total_person = Double.valueOf(String.format("%.2f", total_person));

                    Intent i= new Intent(MainActivity.this,Resultat.class);
                    i.putExtra("total",total_Amount);
                    i.putExtra("tip",tipvalue);
                    i.putExtra("tipPerson",tip_person);
                    i.putExtra("totalPerson",total_person);

                    price.setText("");
                    tip.setText("");
                    nb_people.setText("");
                    msgError.setText("");


                    startActivityForResult(i,1);
                }
                else {
                    //Toast.makeText(MainActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                    msgError.setText("Please fill all fields.");
                }
            }

        });


    }
    private void setNightMode(int nightMode) {
        switch (nightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                // Handle other cases, or do nothing for unknown values
                break;
        }

        // Recreate the activity only if necessary
        if (nightMode != getResources().getConfiguration().uiMode) {
            recreate();
        }
    }
    public void onMenuIconClick(View view) {
        // Toggle the visibility of the LinearLayout
        if (linear.getVisibility() == View.VISIBLE) {
            linear.setVisibility(View.GONE); // If visible, hide it
        } else {
            linear.setVisibility(View.VISIBLE); // If hidden, show it
        }
    }




}
