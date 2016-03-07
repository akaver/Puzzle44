package com.akaver.puzzle44;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String STATE_MOVES = "playerMoves";
    private static final String STATE_TIME = "playerTime";
    private static final String STATE_BOARDROW = "boardRow";


    private Puzzle44Engine board = new Puzzle44Engine();

    private TextView textViewMoves;
    private TextView textViewTime;

    private int timeCount;

    private Timer gameTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewMoves = (TextView) findViewById(R.id.textViewMoves);
        textViewTime = (TextView) findViewById(R.id.textViewTime);

        // Do i need to restore UI state
        if (savedInstanceState != null){
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Restoring state");
            }

            int gameBoard[][] = board.getBoard();

            for (int y = 0; y<=3; y++){
                gameBoard[y] = savedInstanceState.getIntArray(STATE_BOARDROW + y);
            }
            board.setBoard(gameBoard);
            board.setMove(savedInstanceState.getInt(STATE_MOVES));

            timeCount = savedInstanceState.getInt(STATE_TIME);
            textViewTime.setText("" + timeCount);

            if (timeCount < 999){
                startGameTimer();
            }
        }

        drawCurrentGameBoard();
    }


    public void drawCurrentGameBoard() {
        int gameBoard[][] = board.getBoard();

        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 3; y++) {
                int item = gameBoard[y][x];
                int buttonId = this.getResources().getIdentifier("button" + y + x, "id", this.getPackageName());
                Button b = (Button) findViewById(buttonId);
                if (item < 16) {
                    b.setText(Integer.toString(item));
                } else {
                    b.setText("");
                }
            }
        }

        textViewMoves.setText("" + board.getMoves());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void startGameTimer(){
        // stop timer
        gameTimer.cancel();

        gameTimer = new Timer();

        gameTimer.scheduleAtFixedRate(
                new TimerTask(){
                    @Override
                public void run(){
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                textViewTime.setText(""+timeCount);
                                if (timeCount>=999){
                                    gameTimer.cancel();
                                }
                                timeCount++;
                            }
                        });
                    }
                },
        1000,1000);
    }

    public void buttonClicked(View view) {
        Button btn = (Button) view;
        String idAsString = btn.getResources().getResourceName(btn.getId());
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "button pressed: " + idAsString);
        }

        int x = Integer.parseInt(idAsString.substring(idAsString.length()-1, idAsString.length()-0));
        int y = Integer.parseInt(idAsString.substring(idAsString.length()-2, idAsString.length()-1));

        if (BuildConfig.DEBUG) {
            Log.d(TAG, "button pressed x: " + x + " y: "+y);
        }

        board.makeMove(x,y);
        drawCurrentGameBoard();

        //TODO: check game state, is it over?
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_newgame) {
            drawCurrentGameBoard();
            return true;
        }
        if (id == R.id.action_exit) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState called");
        }
        savedInstanceState.putInt(STATE_MOVES, board.getMoves());
        savedInstanceState.putInt(STATE_TIME, timeCount);

        int gameBoard[][] = board.getBoard();

        for (int y = 0; y<=3; y++){
            savedInstanceState.putIntArray(STATE_BOARDROW + y,gameBoard[y]);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart called");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume called");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause called");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop called");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDestroy called");
        }

    }


}
