package com.example.flagtestusmanhamid088;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView ivShowImage;
    ImageView ivMedals;
    ArrayList<String> gamenames = new ArrayList<>();
    ArrayList<String> newgamenames = new ArrayList<>();
    HashMap<String, String> map = new HashMap<>();
    int index;
    Random random;
    String[] answers = new String[4];
    Button btn1,btn2,btn3,btn4;
    TextView tvPoints;
    int points = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivShowImage = findViewById(R.id.ivShowImage);
        ivMedals = findViewById(R.id.ivMedals);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        gamenames.add("The Last of Us");
        gamenames.add("COD: Black Ops 1");
        gamenames.add("Metal Gear Solid");
        gamenames.add("The Last of Us Part 2");
        gamenames.add("Loco Roco");
        gamenames.add("Dark Souls");
        gamenames.add("Overwatch");
        gamenames.add("Rayman");
        gamenames.add("BioShock Infinite");
        gamenames.add("Little Big Planet");
        index = 0;
        map.put(gamenames.get(0), "https://cdn.vox-cdn.com/thumbor/0bYnlTopAUJgKb6wedYOSXrDkY0=/0x0:960x540/920x613/filters:focal(404x194:556x346):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/69101183/2013_06_13_LaunchDayPost_960p.0.0.png");
        map.put(gamenames.get(1), "https://m.media-amazon.com/images/M/MV5BMTI5MTY3ODQzMl5BMl5BanBnXkFtZTcwODA2NzQwNA@@._V1_CR0,60,640,360_AL_UX477_CR0,0,477,268_AL_.jpg");
        map.put(gamenames.get(2), "https://cdn.vox-cdn.com/thumbor/Hsjf0LS1fqJNHKSfEMra1eNnacE=/150x0:1770x1080/2050x1367/cdn.vox-cdn.com/uploads/chorus_image/image/30591813/1_snake.0.jpg");
        map.put(gamenames.get(3), "https://i.gadgets360cdn.com/large/last_of_us_2_review_1591869264504.jpeg?downsize=950:*&output-quality=80");
        map.put(gamenames.get(4), "https://i.pinimg.com/originals/0e/41/7b/0e417b4a6d4c71b9a5ae88977e351f9f.gif");
        map.put(gamenames.get(5), "https://www.denofgeek.com/wp-content/uploads/2016/04/dark-souls_1.jpg?resize=670%2C432");
        map.put(gamenames.get(6), "https://cdn.vox-cdn.com/thumbor/sVawaIYZUHzaiAGiWx3Z1bf92Ms=/0x0:1920x1080/1200x800/filters:focal(546x117:852x423)/cdn.vox-cdn.com/uploads/chorus_image/image/67559973/tracer_overwatch.0.0.0.0.jpg");
        map.put(gamenames.get(7), "https://pbs.twimg.com/profile_images/1173874381283581952/6VPV96Oz.png");
        map.put(gamenames.get(8), "https://wallpapercave.com/wp/8XYCmTl.jpg");
        map.put(gamenames.get(9), "https://m.media-amazon.com/images/M/MV5BNDFiOWE0N2EtOWE4Ni00OWFmLTk2Y2UtZWJjY2JiYWNhOTViXkEyXkFqcGdeQXVyNzU1NzE3NTg@._V1_CR0,45,480,270_AL_UX477_CR0,0,477,268_AL_.jpg");
        Collections.shuffle(gamenames);
        random = new Random();

        generateQuestions(index);
    }

    private void generateQuestions(int index) {
        try {
            Bitmap bitmap = new imageDownloader().execute(map.get(gamenames.get(index))).get();
            ivShowImage.setImageBitmap(bitmap);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        newgamenames = (ArrayList<String>) gamenames.clone();
        newgamenames.remove(index);
        Collections.shuffle(newgamenames);
        int correctAnswerPosition = random.nextInt(4);
        for(int i = 0; i<4;i++)
        {
            if (i == correctAnswerPosition)
                answers[i] = gamenames.get(index);
            else
                answers[i] = newgamenames.get(i);
        }
        btn1.setText(answers[0]);
        btn2.setText(answers[1]);
        btn3.setText(answers[2]);
        btn4.setText(answers[3]);
        newgamenames.clear();
    }


    public void answerSelected(View view) {
        String answer = ((Button)view).getText().toString();
        if(answer.equals(gamenames.get(index))){
            points++;
            tvPoints.setText(points + "/10");
        }
        index++;
        if(index >gamenames.size()-1 ){
            ivShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);

            if(points == 10)
            {
                ivMedals.setImageResource(R.drawable.);
            }
            else if(points != 10 && points <=7 )
            {
                ivMedals.setImageResource(R.drawable.);
            }
            else if(points <=5)
            {
                ivMedals.setImageResource(R.drawable.);
            }
        }
        else
            generateQuestions(index);
    }

    private class imageDownloader extends AsyncTask<String, Void, Bitmap>{
        HttpURLConnection httpURLConnection;
        @Override
        protected Bitmap doInBackground(String... strings) {


            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                Bitmap temp = BitmapFactory.decodeStream(inputStream);
                return temp;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                ivShowImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Download Succesful", Toast.LENGTH_SHORT);
            }else{
                Toast.makeText(getApplicationContext(), "Download error", Toast.LENGTH_SHORT);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
