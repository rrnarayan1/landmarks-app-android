package com.example.landmarksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static Landmark CURRENT_LANDMARK = null;
    private static ArrayList<Landmark> LANDMARKS = null;
    private enum PlayMode {
        LANDMARK_NAME,
        LANDMARK_LOCATION
    }
    private static PlayMode MODE;
    private ArrayList<Integer> CHOICE_BUTTONS = new ArrayList<Integer>() {{
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

        changeLandmark();
    }

    public void skipLandmark(View view) {
        changeLandmark();
    }

    public void choiceButtonClick(View view) {
        if (MODE == PlayMode.LANDMARK_NAME) {
            if (checkLandmarkNameChoice(view)) {
                setLandmarkLocationOptions();
                System.out.println("correct");
                MODE = PlayMode.LANDMARK_LOCATION;
            } else {
                System.out.println("wrong");
            }
        } else {
            if (checkLandmarkLocationChoice(view)) {
                changeLandmark();
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
        MODE = PlayMode.LANDMARK_NAME;
    }

    private void setNewLandmark() {
        int index = (int) (Math.random()*LANDMARKS.size());
        Landmark newLandmark = LANDMARKS.get(index);
        if (newLandmark == CURRENT_LANDMARK) {
            setNewLandmark();
        }
        CURRENT_LANDMARK = newLandmark;
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
}
