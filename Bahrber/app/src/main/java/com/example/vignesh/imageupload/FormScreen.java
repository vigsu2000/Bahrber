package com.example.vignesh.imageupload;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class FormScreen extends AppCompatActivity {
    private static final String TAG = "Bahrber:Form";

    TextView formTitle, length;
    Button buttonShort, buttonMediumLength, buttonLong;
    Button buttonFine, buttonMediumThickness, buttonCoarse;
    Button button1a, button1b, button1c, button2a, button2b, button2c, button3a, button3b, button3c, button4a, button4b, button4c;
    Button submit, help;
    LinearLayout lengthLayout, thicknessLayout, hairType1Layout, hairType2Layout, hairType3Layout, hairType4Layout;
    ConstraintLayout constraintLayoutLength, constraintLayoutThickness, constraingLayoutHairType;
    HashMap<Button, Boolean> lengthButtons, thicknessButtons, hairTypeButtons;
    ArrayList<Integer> opaqueColors, fadedColors;
    ArrayList<HashMap<Button, Boolean>> allGroups;
    HashMap<HashMap<Button, Boolean>, String> groups;

    final int ANIMATION_DURATION = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_screen);

        formTitle = (TextView)findViewById(R.id.formTitle);
        length = (TextView)findViewById(R.id.thickness);
        buttonShort = (Button)findViewById(R.id.buttonShort);
        buttonMediumLength = (Button)findViewById(R.id.buttonMediumLength);
        buttonLong = (Button)findViewById(R.id.buttonLong);
        buttonFine = (Button)findViewById(R.id.buttonFine);
        buttonMediumThickness = (Button)findViewById(R.id.buttonMediumThickness);
        buttonCoarse = (Button)findViewById(R.id.buttonCoarse);
        button1a = (Button)findViewById(R.id.button1a);
        button1b = (Button)findViewById(R.id.button1b);
        button1c = (Button)findViewById(R.id.button1c);
        button2a = (Button)findViewById(R.id.button2a);
        button2b = (Button)findViewById(R.id.button2b);
        button2c = (Button)findViewById(R.id.button2c);
        button3a = (Button)findViewById(R.id.button3a);
        button3b = (Button)findViewById(R.id.button3b);
        button3c = (Button)findViewById(R.id.button3c);
        button4a = (Button)findViewById(R.id.button4a);
        button4b = (Button)findViewById(R.id.button4b);
        button4c = (Button)findViewById(R.id.button4c);
        lengthLayout = (LinearLayout)findViewById(R.id.lengthLayout);
        thicknessLayout = (LinearLayout)findViewById(R.id.thicknessLayout);
        hairType1Layout = (LinearLayout)findViewById(R.id.hairType1Layout);
        hairType2Layout = (LinearLayout)findViewById(R.id.hairType2Layout);
        hairType3Layout = (LinearLayout)findViewById(R.id.hairType3Layout);
        hairType4Layout = (LinearLayout)findViewById(R.id.hairType4Layout);
        constraintLayoutLength = (ConstraintLayout)findViewById(R.id.constraintLayoutLength);
        constraintLayoutThickness = (ConstraintLayout)findViewById(R.id.constraintLayoutThickness);
        constraingLayoutHairType = (ConstraintLayout)findViewById(R.id.constraintLayoutHairType);
        help = (Button)findViewById(R.id.help);
        submit = (Button)findViewById(R.id.submit);

        //Creates map of length buttons
        lengthButtons = new HashMap<>();
        lengthButtons.put(buttonShort, Boolean.TRUE);
        lengthButtons.put(buttonMediumLength, Boolean.TRUE);
        lengthButtons.put(buttonLong, Boolean.TRUE);

        //Creates map of thickness buttons
        thicknessButtons = new HashMap<>();
        thicknessButtons.put(buttonFine, Boolean.TRUE);
        thicknessButtons.put(buttonMediumThickness, Boolean.TRUE);
        thicknessButtons.put(buttonCoarse, Boolean.TRUE);

        //Button Colors
        opaqueColors = new ArrayList<>();
        fadedColors = new ArrayList<>();
        opaqueColors.add(getResources().getColor(R.color.colorPrimary));
        opaqueColors.add(getResources().getColor(R.color.colorPrimaryDark));
        opaqueColors.add(getResources().getColor(R.color.colorAccent));
        fadedColors.add(getResources().getColor(R.color.colorPrimaryFaded));
        fadedColors.add(getResources().getColor(R.color.colorPrimaryDarkFaded));
        fadedColors.add(getResources().getColor(R.color.colorAccentFaded));

        //Creates map of hair type buttons
        hairTypeButtons = new HashMap<>();
        hairTypeButtons.put(button1a, Boolean.TRUE);
        hairTypeButtons.put(button1b, Boolean.TRUE);
        hairTypeButtons.put(button1c, Boolean.TRUE);
        hairTypeButtons.put(button2a, Boolean.TRUE);
        hairTypeButtons.put(button2b, Boolean.TRUE);
        hairTypeButtons.put(button2c, Boolean.TRUE);
        hairTypeButtons.put(button3a, Boolean.TRUE);
        hairTypeButtons.put(button3b, Boolean.TRUE);
        hairTypeButtons.put(button3c, Boolean.TRUE);
        hairTypeButtons.put(button4a, Boolean.TRUE);
        hairTypeButtons.put(button4b, Boolean.TRUE);
        hairTypeButtons.put(button4c, Boolean.TRUE);

        //Creates arraylist of all groups
        allGroups = new ArrayList<>();
        allGroups.add(lengthButtons);
        allGroups.add(thicknessButtons);
        allGroups.add(hairTypeButtons);

        //Creates map for groups
        groups = new HashMap<>();
        groups.put(lengthButtons, "length");
        groups.put(thicknessButtons, "thickness");
        groups.put(hairTypeButtons, "hair type");

        //Allow the buttons to be square
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        lengthLayout.getLayoutParams().height = screenWidth / lengthButtons.size();
        thicknessLayout.getLayoutParams().height = screenWidth / thicknessButtons.size();
        hairType1Layout.getLayoutParams().height = screenWidth / (hairTypeButtons.size() / 4);
        hairType2Layout.getLayoutParams().height = screenWidth / (hairTypeButtons.size() / 4);
        hairType3Layout.getLayoutParams().height = screenWidth / (hairTypeButtons.size() / 4);
        hairType4Layout.getLayoutParams().height = screenWidth / (hairTypeButtons.size() / 4);
        constraintLayoutLength.getLayoutParams().height = screenWidth / 2;
        constraintLayoutThickness.getLayoutParams().height = screenWidth / 2;
        constraingLayoutHairType.getLayoutParams().height = screenWidth * 3 / 2;


        for (final HashMap<Button, Boolean> group : allGroups) {
            for (final Button button : group.keySet()) {
                button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.out.println();
                        if (group.get(button)) {
                            if (numberOfSelectedButtons(group) > 1) {
                                selectButtons(group, button);
                            } else if (numberOfSelectedButtons(group) == 1) {
                                resetButtons(group);
                            }
                        } else {
                            selectButtons(group, button);
                        }

                        drawButtons();
                        }
                });
            }
        }

        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Help button clicked");
                Intent intent = new Intent(FormScreen.this, HelpScreen.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Submit button clicked");
                Intent intent = new Intent(FormScreen.this, Result.class);

//                for (HashMap<Button, Boolean> group : allGroups) {
//                    for (Button button : group.keySet()) {
//                        if (group.get(button)) {
//                            intent.putExtra(groups.get(group), button.getText().toString());
//                        }
//                    }
//                }

                for (Button button : lengthButtons.keySet()) {
                    if (lengthButtons.get(button)) {
                        intent.putExtra("length", button.getText().toString());
                    }
                }
                for (Button button : hairTypeButtons.keySet()) {
                    if (hairTypeButtons.get(button)) {
                        intent.putExtra("type", button.getText().toString().substring(0, 1));
                    }
                }

                startActivity(intent);
            }
        });

        drawButtons();
    }

    void selectButtons(HashMap<Button, Boolean> group, Button target) {
        System.out.println(groups.get(group));
        for (Button button : group.keySet()) {
            System.out.println(button.getId());
            if (button != target) {
                if (group.get(button)) {
                    group.put(button, Boolean.FALSE);
                }
            } else {
                if (!group.get(button)) {
                    AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
                    alphaAnim.setDuration (ANIMATION_DURATION);
                    button.startAnimation(alphaAnim);
                    group.put(button, Boolean.TRUE);
                }
            }
        }
    }

    void resetButtons(HashMap<Button, Boolean> group) {
        for (Button button : group.keySet()) {
            System.out.println(button.getId());
            if (!group.get(button)) {
                AlphaAnimation alphaAnim = new AlphaAnimation(0.5f, 1.0f);
                alphaAnim.setDuration (ANIMATION_DURATION);
                button.startAnimation(alphaAnim);
                group.put(button, Boolean.TRUE);
            }
        }
    }

    int numberOfSelectedButtons(HashMap<Button, Boolean> group) {
        int numSelectedButtons = 0;

        for (Button button : group.keySet()) {
            if (group.get(button)) {
                ++numSelectedButtons;
            }
        }

        return numSelectedButtons;
    }


    void drawButtons() {
        for (HashMap<Button, Boolean> group: allGroups) {
            for (Button button : group.keySet()) {
                ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
                int colorId = buttonColor.getColor();

                if (group.get(button)) {
                    for (int color : opaqueColors) {
                        if (Color.red(colorId) == Color.red(color) &&
                                Color.green(colorId) == Color.green(color) &&
                                Color.blue(colorId) == Color.blue(color)) {
                            button.setBackgroundColor(color);
                        }
                    }
                } else {
                    for (int color : fadedColors) {
                        if (Color.red(colorId) == Color.red(color) &&
                                Color.green(colorId) == Color.green(color) &&
                                Color.blue(colorId) == Color.blue(color)) {
                            button.setBackgroundColor(color);
                        }
                    }
                }
            }
        }

        if (numberOfSelectedButtons(lengthButtons) == 1 &&
                numberOfSelectedButtons(thicknessButtons) == 1 &&
                numberOfSelectedButtons(hairTypeButtons) == 1) {
            submit.setEnabled(true);
        } else {
            submit.setEnabled(false);
        }
    }
}
