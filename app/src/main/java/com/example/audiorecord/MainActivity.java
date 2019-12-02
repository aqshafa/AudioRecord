package com.example.audiorecord;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button record, stop, play;
    private MediaRecorder audioRecorder;
    private String outputFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        record = findViewById(R.id.btn1);
        stop = findViewById(R.id.btn2);
        play = findViewById(R.id.btn3);

        stop.setEnabled(false);
        play.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecorder.setOutputFile(outputFile);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //audioRecorder = new MediaRecorder();
                    audioRecorder.prepare();
                    audioRecorder.start();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording Started", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    audioRecorder.stop();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                audioRecorder.release();
                //audioRecorder=null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Audio Recorded Succesfully", Toast.LENGTH_SHORT).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(outputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                record.setEnabled(true);

                mediaPlayer.start();
                Toast.makeText(getApplicationContext(), "Memutar Audio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
