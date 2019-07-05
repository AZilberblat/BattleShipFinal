package com.avrahamzilberblat.battleshipfinal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static com.avrahamzilberblat.battleshipfinal.Constants.DELAY_MILLS;
import static com.avrahamzilberblat.battleshipfinal.Constants.HIT;
import static com.avrahamzilberblat.battleshipfinal.Constants.MISSED;

public class gameActivity extends AppCompatActivity implements SensorEventListener {
    private gameBoard opponent = null;
    private gameBoard player = null;
    private GridView opponentBoard;
    private GridView playerBoard;
    private GridViewCustomAdapterCom adapter1;
    public static boolean playerTurn;
    static int counterPlayer, counterOpponent;
    private Handler handler = new Handler();
    private ProgressBar prog;
    private TextView txt;
    private int numberOfShips;
    private double numOfHits = 0;
    private double numOfMisses = 1;
    private double ratio;
    private String levelDifficulty;
    private SensorManager sensorManager;
    private Sensor gyro;
    private boolean flag = false;
    private boolean first = true;
    private Float startXValue;

    public gameActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(gameActivity.this, gyro, 1000);

        playerTurn = true;
        counterPlayer = 0;
        counterOpponent = 0;


        prog = findViewById(R.id.progress);
        prog.setVisibility(View.INVISIBLE);

        txt = findViewById(R.id.txt);
        txt.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();

        int numberOfButtons = extras.getInt("numOfButtons", 0);
        numberOfShips = extras.getInt("numOfShips", 0);
        //After Reading Bundle Documentation
        if (extras != null) {
            levelDifficulty = extras.getString("levelDifficulty", "myDefaultString"); // This line modified
        }
        final int shipSizePerLevel = extras.getInt("shipCounter", 0);

        opponentBoard = findViewById(R.id.opponent);
        playerBoard = findViewById(R.id.player);

        opponent = new gameBoard(numberOfButtons, numberOfShips);
        player = new gameBoard(numberOfButtons, numberOfShips);

        opponentBoard.setNumColumns(numberOfButtons);
        playerBoard.setNumColumns(numberOfButtons);

        BaseAdapter adapter = new GridViewCustomAdapter(this, opponent, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playerTurn == true) {
                    Button b = (Button) view;
                    String text = b.getText().toString();
                    int column = Character.getNumericValue(text.charAt(2));
                    int row = Character.getNumericValue(text.charAt(0));
                    if (opponent.getBoardStatus()[row][column] == HIT) {
                        b.setBackgroundResource(R.drawable.explosion);
                        AnimationDrawable anim1 = (AnimationDrawable) b.getBackground();
                        anim1.start();
                        b.setClickable(false);
                        playerTurn = false;
                        counterPlayer++;
                        numOfHits++;
                        if (counterPlayer == shipSizePerLevel) {
                            ratio = numOfHits / numOfMisses;
                            Intent intentGame = new Intent(getApplicationContext(), GameOver.class);
                            intentGame.putExtra("levelDifficulty", levelDifficulty);
                            intentGame.putExtra("text", "You Won");
                            intentGame.putExtra("isPlayerWon", true);
                            intentGame.putExtra("ratioW_L", ratio);
                            intentGame.putExtra("numOfButtons", opponent.getRows());
                            intentGame.putExtra("numOfShips", numberOfShips);
                            intentGame.putExtra("shipCounter", counterPlayer);
                            intentGame.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intentGame);

                        } else {
                            opponent.getBoardStatus()[row][column] = MISSED;
                            prog.setVisibility(View.VISIBLE);
                            txt.setVisibility(View.VISIBLE);

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    adapter1.doRandom();
                                    prog.setVisibility(View.INVISIBLE);
                                    txt.setVisibility(View.INVISIBLE);
                                }
                            }, DELAY_MILLS);
                        }

                    } else {
                        numOfMisses++;
                        b.setBackgroundResource(R.drawable.movie);
                        AnimationDrawable anim = (AnimationDrawable) b.getBackground();
                        anim.start();
                        b.setClickable(false);
                        playerTurn = false;
                        opponent.getBoardStatus()[row][column] = 2;
                        prog.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.VISIBLE);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter1.doRandom();
                                prog.setVisibility(View.INVISIBLE);
                                txt.setVisibility(View.INVISIBLE);
                            }
                        }, DELAY_MILLS);
                    }

                }


            }

        });

        opponentBoard.setAdapter(adapter);
        adapter1 = new GridViewCustomAdapterCom(this, player, shipSizePerLevel, this);
        playerBoard.setAdapter(adapter1);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
     /*   if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(event.values[0]>2.00 && flag==0) {
                flag=1;


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        adapter1.SensorKillShip();
                        flag=0;


                    }
                }, 2000);

            }




        }*/
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (first) {
                startXValue = event.values[0];
                first = false;
            } else {
                if (Math.abs(startXValue - event.values[0]) > 0.3 && !flag) {
                    flag = true;
                    adapter1.SensorKillShip();
                    adapter1.MoveShip();



                }

            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
