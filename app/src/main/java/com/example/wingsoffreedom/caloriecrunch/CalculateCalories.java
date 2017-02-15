package com.example.wingsoffreedom.caloriecrunch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateCalories extends Fragment {
    private boolean profileEntered = false;
    private int userWeight;

    private Map<String, float[]> exerciseMap = new HashMap<String, float[]>();
    private List<float[]> forHundred = new ArrayList<float[]>(Arrays.asList(new float[] {350, (float) 0.0042},
            new float[] {200, (float) 0.0075}, new float[] {225, (float) 0.0065}, new float[] {25, (float) 3.5},
            new float[] {25, (float) 3.5}, new float[] {10, (float) 8.0}, new float[] {100, (float) 0.015},
            new float[] {12, (float) 4.0}, new float[] {20, (float) 3.3}, new float[] {12, (float) 7.0},
            new float[] {13, (float) 8.0}, new float[] {15, (float) 7.0}));

    private List<String> allExercises =  new ArrayList<>(Arrays.asList("Pushups", "Situps", "Squats", "Leg-Lifts",
            "Plank", "Jumping Jacks", "Pullups", "Cycling", "Walking", "Jogging", "Swimming", "Stair-Climbing"));

    private EditText weightInput;
    private EditText amountInput;

    private Spinner exerciseDrop;

    private Button convertButton;
    private Button compareButton;

    private TextView convertOutput;
    private TextView compareLine = null;
    private List<TextView> compareCalories = null;
    private LinearLayout displayLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_calories, container, false);

        for(int i = 0; i < allExercises.size(); i++) {
            exerciseMap.put(allExercises.get(i), forHundred.get(i));
        }

        weightInput = (EditText) view.findViewById(R.id.weight_input);

        compareCalories = new ArrayList<TextView>();

        exerciseDrop = (Spinner) view.findViewById(R.id.dropdown_exercise);
        amountInput = (EditText) view.findViewById(R.id.amount_input);

        convertButton = (Button) view.findViewById(R.id.calConvert_btn);
        compareButton = (Button) view.findViewById(R.id.calCompare_btn);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercise = exerciseDrop.getSelectedItem().toString();
                int exerAmount = (Integer) Integer.parseInt((String) amountInput.getText().toString());
                String weightString = (String) weightInput.getText().toString();
                if (!weightString.isEmpty()) {
                    userWeight = Integer.parseInt(weightString);
                    profileEntered = true;
                }

                float caloriesBurned;

                if (profileEntered) {
                    if (exercise.equals("Pushups") || exercise.equals("Situps") || exercise.equals("Squats") || exercise.equals("Pullups")) {
                        caloriesBurned = (float) ((float)userWeight/2.2)*((float)exerAmount*exerciseMap.get(exercise)[1]);
                    } else {
                        caloriesBurned = (float) ((float)userWeight/2.2)*(exerciseMap.get(exercise)[1])*(float)((float)exerAmount/60.0);
                    }
                } else {
                    caloriesBurned = (float) (100.0/(float)exerciseMap.get(exercise)[0])*(float)exerAmount;
                }

                displayLayout = (LinearLayout) view.findViewById(R.id.display_layout);
                if (compareCalories != null) {
                    for (TextView view : compareCalories) {
                        displayLayout.removeView(view);
                    }
                    compareCalories = null;
                }
                if (compareLine != null) {
                    displayLayout.removeView(compareLine);
                }
                convertOutput = (TextView) view.findViewById(R.id.btn_output);
                displayLayout.removeView(convertOutput);

                convertOutput.setText(String.valueOf((int) caloriesBurned) + " Calories Burned!");
                convertOutput.setTextSize(35);

                displayLayout.addView(convertOutput);

            }
        });

        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exercise = exerciseDrop.getSelectedItem().toString();
                int exerAmount = (Integer) Integer.parseInt((String) amountInput.getText().toString());

                String weightString = (String) weightInput.getText().toString();
                if (!weightString.isEmpty()) {
                    userWeight = Integer.parseInt(weightString);
                    profileEntered = true;
                }

                float caloriesBurned;

                if (profileEntered) {
                    if (exercise.equals("Pushups") || exercise.equals("Situps") || exercise.equals("Squats") || exercise.equals("Pullups")) {
                        caloriesBurned = (float) ((float)userWeight/2.2)*((float)exerAmount*exerciseMap.get(exercise)[1]);
                    } else {
                        caloriesBurned = (float) ((float)userWeight/2.2)*(exerciseMap.get(exercise)[1])*(float)((float)exerAmount/60.0);
                    }
                } else {
                    caloriesBurned = (float) (100.0/(float)exerciseMap.get(exercise)[0])*(float)exerAmount;
                }

                displayLayout = (LinearLayout) view.findViewById(R.id.display_layout);

                if (compareCalories != null) {
                    for (TextView view : compareCalories) {
                        displayLayout.removeView(view);
                    }
                }
                compareCalories = new ArrayList<TextView>();

                if (compareLine != null) {
                    displayLayout.removeView(compareLine);
                } else {
                    compareLine = new TextView(view.getContext());
                }

                convertOutput = (TextView) view.findViewById(R.id.btn_output);
                displayLayout.removeView(convertOutput);

                convertOutput.setText(String.valueOf((int) caloriesBurned) + " Calories Burned!");

                compareLine.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                compareLine.setText("To burn the same amount of calories:");
                compareLine.setTextSize(20);

                displayLayout.addView(convertOutput);
                displayLayout.addView(compareLine);

                for (String ex : exerciseMap.keySet()) {
                    float tempBurned;
                    if (profileEntered) {
                        if (ex.equals("Pushups") || ex.equals("Situps") || ex.equals("Squats") || ex.equals("Pullups")) {
                            tempBurned = (float) ((float) caloriesBurned/((float) ((float)userWeight/2.2)*((float)exerciseMap.get(ex)[1])));
                        } else {
                            tempBurned = (float) (((float) caloriesBurned/((float) ((float)userWeight/2.2)*((float)exerciseMap.get(ex)[1])))*60.0);
                        }
                    } else {
                        tempBurned = (float) ((float)exerciseMap.get(ex)[0]/100)*(float)caloriesBurned;
                    }

                    TextView tempView = new TextView(view.getContext());
                    tempView.setTextSize(16);
                    tempView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    if (ex.equals("Pushups") || ex.equals("Situps") || ex.equals("Squats") || ex.equals("Pullups")) {
                        tempView.setText(ex + "         " + String.valueOf((int)tempBurned) + " reps");
                    } else {
                        tempView.setText(ex + "         " + String.valueOf((int)tempBurned) + " mins");
                    }

                    compareCalories.add(tempView);
                    displayLayout.addView(tempView);
                }
            }
        });

        return view;
    }

}
