package com.example.sharedbilltipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Resultat extends AppCompatActivity {

    TextView tip;
    TextView totalAmount;
    TextView tip_per_person;
    TextView total_per_person;
    Button clear;
    Button shareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        clear=findViewById(R.id.clear);
        shareButton=findViewById(R.id.shareButton);
        tip=findViewById(R.id.tipvalue);
        totalAmount=findViewById(R.id.totalvalue);
        tip_per_person=findViewById(R.id.tipPersonvalue);
        total_per_person=findViewById(R.id.totalPersonvalue);
        Intent intent=getIntent();
        final Double tipValue=intent.getDoubleExtra("tip",0);
        final Double totalAmoutValue=intent.getDoubleExtra("total",0);
        final Double PersonTipValue=intent.getDoubleExtra("tipPerson",0);
        final Double PersonTotalValue=intent.getDoubleExtra("totalPerson",0);
        tip.setText(tipValue.toString()+" DH");
        totalAmount.setText(totalAmoutValue.toString()+" DH");
        tip_per_person.setText(PersonTipValue.toString()+" DH");
        total_per_person.setText(PersonTotalValue.toString()+" DH");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = "Tip: " + tip.getText().toString() + "\n" +
                        "Total Amount: " + totalAmount.getText().toString() + "\n" +
                        "Tip per Person: " + tip_per_person.getText().toString() + "\n" +
                        "Total per Person: " + total_per_person.getText().toString();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                shareIntent.setType("text/plain");
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }else {
                    System.out.println("Hello");
                }
            }
        });
    }
}