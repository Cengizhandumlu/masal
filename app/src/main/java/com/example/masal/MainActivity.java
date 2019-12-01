package com.example.masal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button changeButton;
    Button selectButton1;
    Button selectButton2;
    TextView score;
    TextView sentence;
    TextView word;
    TextView mean1;
    TextView mean2;
    Random random;
    int puan = 0;
    int right_left = 0;
    int removeId = 0;
    int wordCounter = 24;
    int sentenceCounter = 30;


    ArrayList<Integer> sentenceID = new ArrayList<>();
    ArrayList<String> sentenceList = new ArrayList<>();
    ArrayList<Integer> wordID = new ArrayList<>();
    ArrayList<String> wordList = new ArrayList<>();
    ArrayList<String> currentWordSentence = new ArrayList<>();
    ArrayList<String> wordMeansList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_JSON();

        random = new Random();

        changeButton = findViewById(R.id.changeButtonId);
        selectButton1 = findViewById(R.id.selectButton1Id);
        selectButton2 = findViewById(R.id.selectButton2Id);
        score = findViewById(R.id.scoreId);
        sentence = findViewById(R.id.sentenceId);
        word = findViewById(R.id.wordId);
        mean1 = findViewById(R.id.mean1Id);
        mean2 = findViewById(R.id.mean2Id);

        int randomWordID = wordID.get(random.nextInt(wordCounter));
        removeId = randomWordID;
        word.setText(wordList.get(randomWordID));
        for (int i = 0; i < sentenceList.size(); i++) {
            if (randomWordID == sentenceID.get(i)) {
                currentWordSentence.add(sentenceList.get(i));
            }
        }
        sentence.setText(currentWordSentence.get(0));

        int randomSelectButton = random.nextInt(2);
        right_left = randomSelectButton;

        int randomWordMeanId = random.nextInt(wordCounter);


        if (randomSelectButton == 0) {
            mean1.setText(wordMeansList.get(randomWordID));
            if (randomWordID != randomWordMeanId) {
                mean2.setText(wordMeansList.get(randomWordMeanId));
            } else {
                randomWordMeanId = random.nextInt(wordCounter);
                mean2.setText(wordMeansList.get(randomWordMeanId));
            }


        } else {
            mean2.setText(wordMeansList.get(randomWordID));
            if (randomWordID != randomWordMeanId) {
                mean1.setText(wordMeansList.get(randomWordMeanId));
            } else {
                randomWordMeanId = random.nextInt(wordCounter);
                mean1.setText(wordMeansList.get(randomWordMeanId));
            }
        }


    }


    public void changeButton(View view) {
        changeButton.setOnClickListener(new View.OnClickListener() {
            int size = currentWordSentence.size();


            @Override
            public void onClick(View view) {
                if (size != 0) {
                    sentence.setText(currentWordSentence.get(size - 1));
                    size--;
                }
            }
        });
    }

    public void selectButton1(View view) {
        selectButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (right_left == 0) {
                    puan = puan + 10;
                    wordList.remove(wordCounter);
                    wordMeansList.remove(wordCounter);
                    wordID.remove(wordCounter);
                    wordCounter--;
                    removeId = wordID.get(random.nextInt(wordCounter));
                    word.setText(wordList.get(removeId));
                    score.setText("" + puan);


                } else {
                    puan--;
                    wordList.remove(wordCounter);
                    wordMeansList.remove(wordCounter);
                    wordID.remove(wordCounter);
                    wordCounter--;
                    removeId = wordID.get(random.nextInt(wordCounter));
                    word.setText(wordList.get(removeId));
                    score.setText("" + puan);

                }


            }

        });

    }

    public void selectButton2(View view) {
        selectButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (right_left == 1) {
                    puan = puan + 10;
                    wordList.remove(wordCounter);
                    wordMeansList.remove(wordCounter);
                    wordID.remove(wordCounter);
                    wordCounter--;
                    removeId = wordID.get(random.nextInt(wordCounter));
                    word.setText(wordList.get(removeId));
                    score.setText("" + puan);

                } else {
                    puan--;
                    wordList.remove(wordCounter);
                    wordMeansList.remove(wordCounter);
                    wordID.remove(wordCounter);
                    wordCounter--;
                    removeId = wordID.get(random.nextInt(wordCounter));
                    word.setText(wordList.get(removeId));
                    score.setText("" + puan);

                }
                score.setText("" + puan);
            }
        });
    }


    public void get_JSON() {

        String JSON;
        try {
            InputStream inputStream = getAssets().open("sentence.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                sentenceID.add(jsonObject.getInt("id"));
                sentenceList.add(jsonObject.getString("sentence"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = getAssets().open("word.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            JSON = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(JSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                wordID.add(jsonObject.getInt("id"));
                wordList.add(jsonObject.getString("word"));
                wordMeansList.add(jsonObject.getString("mean"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}
