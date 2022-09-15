package com.syahkhay.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView tvLast, tvGuess, tvHint, tv3, tv5;
    private Button btnConfirm;
    private EditText editGuess;

    boolean twoDigits, threeDigits, fourDigits;
    Random r = new Random();
    int random;
    int remaining = 10;

    ArrayList<Integer> guessesList=new ArrayList<>();
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvLast=findViewById(R.id.tvLast);
        tvGuess=findViewById(R.id.tvGuess);
        tvHint=findViewById(R.id.tvHint);
        btnConfirm=findViewById(R.id.btnConfirm);
        editGuess=findViewById(R.id.editGuess);
        tv3=findViewById(R.id.textView3);
        tv5=findViewById(R.id.textView5);

        twoDigits=getIntent().getBooleanExtra("two",false);
        threeDigits=getIntent().getBooleanExtra("three",false);
        fourDigits=getIntent().getBooleanExtra("four",false);

        // Generate secret number based on digits choosen
        if(twoDigits){
            random=r.nextInt(89)+10;
        }else if(threeDigits){
            random=r.nextInt(899)+100;
        }else if(fourDigits){
            random=r.nextInt(8999)+1000;
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guess = editGuess.getText().toString();
                if(guess.equals("")){
                    Toast.makeText(GameActivity.this, getString(R.string.toast), Toast.LENGTH_LONG).show();
                }else{
                    tvLast.setVisibility(View.VISIBLE);
                    tvGuess.setVisibility(View.VISIBLE);
                    tvHint.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.VISIBLE);
                    tv5.setVisibility(View.VISIBLE);

                    count++;
                    remaining --;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);
                    tvLast.setText(userGuess+"");
                    tvGuess.setText(remaining+"");

                    // Kalau Betul
                    if(random==userGuess){

                        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle(getString(R.string.alert_title));
                        builder.setCancelable(false); // So bila click luar dialog, takleh close
                        builder.setMessage(getString(R.string.congrats1)+ random + " \n\n " +getString(R.string.congrats2) + " " + count + getString(R.string.congrats3)
                                            + "\n\n" + getString(R.string.congrats4) + guessesList + "\n\n " + getString(R.string.congrats5));
                        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TERMINATE THE APPLICATION
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();

                    // Kalau guess lagi besar
                    }else if(random<userGuess){
                        tvHint.setText(getString(R.string.hintGreat));

                    //Kalau guess lagi rendah
                    }else if(random>userGuess){
                        tvHint.setText(getString(R.string.hintLower));
                    }

                    // Kalau nyawa dah habis
                    if(remaining==0){

                        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
                        builder.setTitle(getString(R.string.alert_title));
                        builder.setCancelable(false); // So bila click luar dialog, takleh close
                        builder.setMessage(getString(R.string.lose1)+ random + " \n\n " +getString(R.string.lose2) + " " + count + getString(R.string.congrats3)
                                + "\n\n" + getString(R.string.congrats4) + guessesList + "\n\n " + getString(R.string.congrats5));
                        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(GameActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TERMINATE THE APPLICATION
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();

                    }

                    editGuess.setText("");

                }
            }
        });


    }
}