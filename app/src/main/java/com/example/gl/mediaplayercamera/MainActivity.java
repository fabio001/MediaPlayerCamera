package com.example.gl.mediaplayercamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private HashMap<String,String> dict;

    private static int REQ_CAM_ID = 5555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void radioButtonHandler(View view) {
        //you can make an optimized code by defining as a member....
        MediaPlayer mp = null;

        if(view.getId() == R.id.rd1){
            mp = MediaPlayer.create(this,R.raw.button1);
            mp.start();
        }
        else if (view.getId() == R.id.rd2){
            mp = MediaPlayer.create(this,R.raw.button2);
            mp.start();
        }
        else{
            Intent pict = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(pict,REQ_CAM_ID);

        }
    }

    public void lookupDict(View view) {

        EditText userInput = (EditText) findViewById(R.id.userWord);
        String res = lookup(userInput.getText().toString());
        TextView output = (TextView)findViewById(R.id.dictRes);

        if(res == null){
            output.setText("Not Found!!!");

        }
        else{
            output.setText(res);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CAM_ID && resultCode == RESULT_OK){
            Bitmap bmp = (Bitmap)data.getExtras().get("data");
            ImageView img = (ImageView) findViewById(R.id.photo);
            img.setImageBitmap(bmp);
        }
    }

    private String lookup(String word ){
        if(dict == null)
        {
            dict = new HashMap<>();
        }
        else{
            return dict.get(word);
        }

        Scanner fileRead = new Scanner(getResources().openRawResource(R.raw.mydict));

        while(fileRead.hasNext()){
            String line = fileRead.nextLine();
            String[] words = line.split("\t");
            dict.put(words[0], words[1]);
        }
        return dict.get(word);
    }
}
