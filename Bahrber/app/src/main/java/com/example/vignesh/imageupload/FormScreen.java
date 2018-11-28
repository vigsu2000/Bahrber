package com.example.vignesh.imageupload;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FormScreen extends AppCompatActivity {

    TextView formTitle, length;
    Button buttonShort, buttonMediumLength, buttonLong;
    Button buttonFine, buttonMediumThickness, buttonCoarse;
    Button button1a, button1b, button1c, button2a, button2b, button2c, button3a, button3b, button3c, button4a, button4b, button4c;
    LinearLayout lengthLayout, thicknessLayout, hairType1Layout, hairType2Layout, hairType3Layout, hairType4Layout;
    ConstraintLayout constraintLayoutLength, constraintLayoutThickness, constraingLayoutHairType;
    Switch terms;
    ArrayList<Button> fadedButtons, opaqueButtons;
    HashMap<Button, Boolean> lengthButtons, thicknessButtons, hairTypeButtons;
    ArrayList<HashMap<Button, Boolean>> allGroups;
    HashMap<HashMap<Button, Boolean>, String> groups;

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

        //Creates arraylist of length buttons
        lengthButtons = new HashMap<>();
        lengthButtons.put(buttonShort, Boolean.TRUE);
        lengthButtons.put(buttonMediumLength, Boolean.TRUE);
        lengthButtons.put(buttonLong, Boolean.TRUE);

        //Creates arraylist of thickness buttons
        thicknessButtons = new HashMap<>();
        thicknessButtons.put(buttonFine, Boolean.TRUE);
        thicknessButtons.put(buttonMediumThickness, Boolean.TRUE);
        thicknessButtons.put(buttonCoarse, Boolean.TRUE);

        //Creates arraylist of hair type buttons
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

        lengthLayout.getLayoutParams().height = screenWidth / 3;
        thicknessLayout.getLayoutParams().height = screenWidth / 3;
        hairType1Layout.getLayoutParams().height = screenWidth / 3;
        hairType2Layout.getLayoutParams().height = screenWidth / 3;
        hairType3Layout.getLayoutParams().height = screenWidth / 3;
        hairType4Layout.getLayoutParams().height = screenWidth / 3;
        constraintLayoutLength.getLayoutParams().height = screenWidth / 2;
        constraintLayoutThickness.getLayoutParams().height = screenWidth / 2;
        constraingLayoutHairType.getLayoutParams().height = screenWidth * 3 / 2;


        for (final HashMap<Button, Boolean> group : allGroups) {
            for (final Button button : group.keySet()) {
                opaqueButtons.add(button);

                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
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
                        drawButtons(fadedButtons, opaqueButtons, group);
                        printButtonStates();

//                        System.out.println(button.getId());
                    }
                });
            }
        }

        printButtonStates();
        drawButtons(fadedButtons, opaqueButtons, null);
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
                    alphaAnim.setDuration (400);
                    button.startAnimation(alphaAnim);
                    group.remove(button);
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
                alphaAnim.setDuration (400);
                button.startAnimation(alphaAnim);
                button.getBackground().setAlpha(0xff);
                group.put(button, Boolean.TRUE);
            }
        }
    }

    void printButtonStates() {
        for (HashMap<Button, Boolean> group: allGroups) {
            for (Button button : group.keySet()) {
                int color = Color.TRANSPARENT;
                Drawable background = button.getBackground();
                if (background instanceof ColorDrawable) {
                    color = ((ColorDrawable) background).getColor();
                }
                System.out.println(button.getId() + " " + color + ": " + (group.get(button) ? "opaque" : "faded"));
            }
            System.out.println();
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

    void drawButtons(ArrayList<Button> faded, ArrayList<Button> opaque, HashMap<Button, Boolean> group) {
        for (Button button : faded) {
            button.getBackground().setAlpha(0x80);
        }

        for (Button button : opaque) {
            button.getBackground().setAlpha(0xff);
        }
    }
}
