package com.example.landmarksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static Landmark CURRENT_LANDMARK = null;
    private int CURRENT_LANDMARK_INDEX = 0;
    private static ArrayList<Landmark> LANDMARKS = null;
    private enum PlayMode {
        LANDMARK_NAME,
        LANDMARK_LOCATION
    }
    private static PlayMode MODE;
    private final ArrayList<Integer> CHOICE_BUTTONS = new ArrayList<Integer>() {{
        add(R.id.choice_button_0);
        add(R.id.choice_button_1);
        add(R.id.choice_button_2);
        add(R.id.choice_button_3);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LANDMARKS = Landmark.setUpLandmarks(this);
        Collections.shuffle(LANDMARKS);

        changeLandmark();
    }

    public void skipLandmark(View view) {
        changeLandmark();
    }

    public void choiceButtonClick(View view) {
        if (MODE == PlayMode.LANDMARK_NAME) {
            if (checkLandmarkNameChoice(view)) {
                setLandmarkLocationOptions();
                clearError();
                setHeader("Where is this located?");
                setSubheader(CURRENT_LANDMARK.get_name());
                MODE = PlayMode.LANDMARK_LOCATION;
            } else {
                setError();
            }
        } else {
            if (checkLandmarkLocationChoice(view)) {
                changeLandmark();
            } else {
                setError();
            }
        }
    }

    private boolean checkLandmarkNameChoice(View view) {
        Button choiceButton = (Button) findViewById(view.getId());
        return choiceButton.getText().equals(CURRENT_LANDMARK.get_name());
    }

    private boolean checkLandmarkLocationChoice(View view) {
        Button choiceButton = (Button) findViewById(view.getId());
        return choiceButton.getText().equals(CURRENT_LANDMARK.get_location());
    }

    private void changeLandmark() {
        setNewLandmark();
        setLandmarkImage();
        setLandmarkNameOptions();
        clearError();
        clearSubheader();
        setHeader("What is this landmark called?");
        MODE = PlayMode.LANDMARK_NAME;
    }

    private void setNewLandmark() {
        if (CURRENT_LANDMARK_INDEX == LANDMARKS.size() - 1) {
            Collections.shuffle(LANDMARKS);
            CURRENT_LANDMARK_INDEX = 0;

        } else {
            CURRENT_LANDMARK_INDEX += 1;
        }
        CURRENT_LANDMARK = LANDMARKS.get(CURRENT_LANDMARK_INDEX);
    }

    private void setLandmarkNameOptions() {
        ArrayList<String> options = new ArrayList<>();
        int correctAnswerIndex = (int) (Math.random() * 4);
        options.add(CURRENT_LANDMARK.get_name());
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerIndex) {
                setLandmarkNameButton(i, CURRENT_LANDMARK);
            } else {
                int landmarkIndex = (int) (Math.random() * LANDMARKS.size());
                Landmark landmarkChoice = LANDMARKS.get(landmarkIndex);
                if (options.contains(landmarkChoice.get_name())) {
                    i--;
                    continue;
                }
                options.add(landmarkChoice.get_name());
                setLandmarkNameButton(i,landmarkChoice);
            }

        }
    }

    private void setLandmarkLocationOptions() {
        ArrayList<String> options = new ArrayList<>();
        int correctAnswerIndex = (int) (Math.random() * 4);
        options.add(CURRENT_LANDMARK.get_location());
        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerIndex) {
                setLandmarkLocationButton(i, CURRENT_LANDMARK);
            } else {
                int landmarkIndex = (int) (Math.random() * LANDMARKS.size());
                Landmark landmarkChoice = LANDMARKS.get(landmarkIndex);
                if (options.contains(landmarkChoice.get_location())) {
                    i--;
                    continue;
                }
                options.add(landmarkChoice.get_location());
                setLandmarkLocationButton(i,landmarkChoice);
            }

        }
    }

    private void setLandmarkImage() {
        ImageView img = (ImageView) findViewById(R.id.landmarkPicture);
        img.setImageResource(CURRENT_LANDMARK.get_image());
    }

    private void setLandmarkNameButton(int index, Landmark landmark) {
        Button choiceButton = (Button) findViewById(CHOICE_BUTTONS.get(index));
        choiceButton.setText(landmark.get_name());
    }

    private void setLandmarkLocationButton(int index, Landmark landmark) {
        Button choiceButton = (Button) findViewById(CHOICE_BUTTONS.get(index));
        choiceButton.setText(landmark.get_location());
    }

    private void setHeader(String text) {
        TextView header = (TextView) findViewById(R.id.header);
        header.setText(text);
    }

    private void setSubheader(String text) {
        TextView header = (TextView) findViewById(R.id.subheader);
        header.setText(text);;
    }

    private void clearSubheader() {
        TextView header = (TextView) findViewById(R.id.subheader);
        header.setText("");
    }

    private void setError() {
        TextView header = (TextView) findViewById(R.id.try_again);
        header.setText("Try Again!");
        header.setTextColor(Color.RED);
    }

    private void clearError() {
        TextView header = (TextView) findViewById(R.id.try_again);
        header.setText("");
    }
}
