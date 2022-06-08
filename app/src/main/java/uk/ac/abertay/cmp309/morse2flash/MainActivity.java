package uk.ac.abertay.cmp309.morse2flash;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{ //Main Morse2Flash class

    Button toggle;
    TextView text;
    boolean flashCheck = false; //setting phone flashlight availability as false
    boolean flashOn = false;
    private FileHelper fileHelper; //defining FileHelper class
    private SQLiteTextHelper sqLiteTextHelper; //defining SQLiteTextHelper class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteTextHelper = SQLiteTextHelper.getInstance(getApplicationContext());
        fileHelper = FileHelper.getInstance(getApplicationContext());

        //CHECK AND ASK FOR PERMISSIONS//

        int check1 = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE); //for  permission to store user message in Internal Storage (Main phone storage)
        int check2 = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE); //for permission to store user message in External Storage (SD card)
        int check3 = checkSelfPermission(Manifest.permission.CAMERA); //for permission to use phone flashlight

        boolean needToAsk = !((check1 == PackageManager.PERMISSION_GRANTED) & (check2 == PackageManager.PERMISSION_GRANTED) & (check3 == PackageManager.PERMISSION_GRANTED));

        if(needToAsk){ //One permission has not been granted - ask.
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, //request permission for Morse2Flash to access internal storage
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, //request permission for Morse2Flash to access external storage
                    Manifest.permission.CAMERA //request permission for flashlight
            }, 0);
        }

        //Info Button - if user wants more information about Morse2Flash

        Button info = findViewById(R.id.infoButton); //getting "info" button id at top right of screen.

        info.setOnClickListener(new View.OnClickListener() { //listener waiting for user to click info button.
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, info.class)); //Start info activity setting new view.
            }
        });

        flashCheck = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH); //Checking if the phone has a flashlight.

        toggle = findViewById(R.id.flash); //ID of "Flash" button on main page
        text = findViewById(R.id.input); //ID of entered message by user to flash in Morse.

        //FLASH IN MORSE CODE//
        toggle.setOnClickListener(new View.OnClickListener() { //listener for user to flash their message
            @Override
            public void onClick(View view) {
                if(flashCheck) //The phone has a flashlight
                {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                    {
                        char[] toFlash = convert(text); //convert message to char array
                        int time = calcFlashtime(toFlash); //how long the message will take to flash in seconds.
    //

                        if(time >=60) //convert estimated time to minutes
                        {
                            float minute = time /60 ; //converting message into minutes
                            Toast.makeText(MainActivity.this, "Estimated time to flash is: " + minute + "minute(s)", Toast.LENGTH_SHORT).show(); //Simple toast to let the user know how long it will take to flash their message
                        }
                        else if(toFlash.length>0) //making sure user has inputted a message before giving time estimation.
                        {
                            Toast.makeText(MainActivity.this, "Estimated time to flash is: " + time + "second(s)", Toast.LENGTH_SHORT).show(); //Simple toast to let the user know how long it will take to flash their message
                        }

                        for(char e: toFlash) //Go through entire message letter by letter
                        {
                            if (e == ' ') //If there is a space in the message
                            {
                                try {
                                    flashOff(); //waiting 7 seconds
                                    e++;
                                } catch (CameraAccessException | InterruptedException o) {
                                    o.printStackTrace();
                                }
                            }
                            switch (e) { //statement to flash the flashlight in corresponding character
                                case 'A':
                                    try {
                                        dot(); // call dot function
                                        dash(); // call dash function
                                    } catch (CameraAccessException | InterruptedException f) { //catch if error is thrown
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'B':
                                    try {
                                        dash();
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'C':
                                    try {
                                        dash();
                                        dot();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'D':
                                    try {
                                        dash();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'E':
                                    try {
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'F':
                                    try {
                                        dot();
                                        dot();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'G':
                                    try {
                                        dash();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'H':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'I':
                                    try {
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'J':
                                    try {
                                        dot();
                                        dash();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'K':
                                    try {
                                        dash();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'L':
                                    try {
                                        dot();
                                        dash();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'M':
                                    try {
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'N':
                                    try {
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'O':
                                    try {
                                        dash();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'P':
                                    try {
                                        dot();
                                        dash();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'Q':
                                    try {
                                        dash();
                                        dash();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'R':
                                    try {
                                        dot();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'S':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'T':
                                    try {
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'U':
                                    try {
                                        dot();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'V':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'W':
                                    try {
                                        dot();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'X':
                                    try {
                                        dash();
                                        dot();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'Y':
                                    try {
                                        dash();
                                        dot();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case 'Z':
                                    try {
                                        dash();
                                        dash();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '0':
                                    try {
                                        dash();
                                        dash();
                                        dash();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '1':
                                    try {
                                        dot();
                                        dash();
                                        dash();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '2':
                                    try {
                                        dot();
                                        dot();
                                        dash();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '3':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                        dash();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '4':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                        dot();
                                        dash();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '5':
                                    try {
                                        dot();
                                        dot();
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '6':
                                    try {
                                        dash();
                                        dot();
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '7':
                                    try {
                                        dash();
                                        dash();
                                        dot();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '8':
                                    try {
                                        dash();
                                        dash();
                                        dash();
                                        dot();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                                case '9':
                                    try {
                                        dash();
                                        dash();
                                        dash();
                                        dash();
                                        dot();
                                    } catch (CameraAccessException | InterruptedException f) {
                                        f.printStackTrace();
                                    }
                                    break;
                            }
                            try {
                                betweenLetter(); //waiting 3 seconds between each letter in message
                            } catch (CameraAccessException | InterruptedException o) {
                                o.printStackTrace();
                            }
                        }

                    }



                }
                else //The phone does not have a flashlight
                {
                    Toast.makeText(MainActivity.this, "Flash isn't available for this device", Toast.LENGTH_LONG).show(); //Toast message to notify user their phone does not have a flashlight and is incompatible
                }
            }
        });
    }

    @Override
    public void onBackPressed() { //Pop-up dialog box to get permission to close the app.

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //create pop up in Main Page of Morse2FLash
        builder.setCancelable(false); //user cannot exit pop up by tapping outside of it
        builder.setTitle("Wait!"); //title for pop up
        builder.setMessage("Are you sure you want to exit?"); //Pop up message
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { //If the user wants to exit the app, close it
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() { ////If the user wants to stay in the app, cancel pop up
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create(); //create alert
        alertDialog.show(); //show alert
    }

    private void dot() throws CameraAccessException, InterruptedException { //dot in Morse code 1 second as 1 time unit
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String camId = cameraManager.getCameraIdList()[0]; //getting camera to turn on the flashlight
        cameraManager.setTorchMode(camId, true); //turning on flashlight
//        Toast.makeText(this, "Dot", Toast.LENGTH_SHORT).show();
        Thread.sleep(1000); //one unit of time
        cameraManager.setTorchMode(camId, false); //turning off flashlight
    } //Penguin Coders 2020, How to Create Flash Light App in Android | Android Studio | Android Beginner Series | Penguin Coders, viewed 05 May, <https://www.youtube.com/watch?v=sRL-DbKuMk0>.

    private void dash() throws CameraAccessException, InterruptedException { //dash in Morse code - 3 seconds
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String camId = cameraManager.getCameraIdList()[0];  //getting camera to turn on the flashlight
        cameraManager.setTorchMode(camId, true); //turning on flashlight
//        Toast.makeText(this, "Dash", Toast.LENGTH_SHORT).show();
        Thread.sleep(3000); //three units
        cameraManager.setTorchMode(camId, false); //turning off flashlight
    } //Penguin Coders 2020, How to Create Flash Light App in Android | Android Studio | Android Beginner Series | Penguin Coders, viewed 05 May, <https://www.youtube.com/watch?v=sRL-DbKuMk0>.

    private void betweenLetter() throws CameraAccessException, InterruptedException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String camId = cameraManager.getCameraIdList()[0];  //getting camera to turn on the flashlight
        cameraManager.setTorchMode(camId, false); //turn off flashlight
//        Toast.makeText(this, "Between Letter", Toast.LENGTH_SHORT).show();
        Thread.sleep(3000); //turn off for 3 seconds as between a letter in message
    }

    public void processClicks(View view){ //function to handle storage options
        text = findViewById(R.id.input);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED & checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            switch (view.getId()) {
                case R.id.btnSave:
                    saveText(text.getText().toString()); //save user entered message
                    break;
                case R.id.btnLoad:
                    text.setText(loadText()); //load user saved message
                    break;
                case R.id.btnClear:
                    text.setText(""); //remove text from TextView
                    break;
            }
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private void saveText(String Name) { //function to save message with selected storage option
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED & checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            switch (((RadioGroup) findViewById(R.id.rbgGroup)).getCheckedRadioButtonId()) {
                case R.id.rbInternal:
                    fileHelper.saveToInternalStorage("internal.txt", Name);
                    break; //save message to internal storage
                case R.id.rbExternal:
                    fileHelper.saveToExternalStorage("external.txt", Name);
                    break; //save message to external storage
                case R.id.rbSQLite:
                    saveToSQLite(Name);
                    break; //save message to SQLite database
            }
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private String loadText() { //load message stored in selected storage method
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED & checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            switch (((RadioGroup) findViewById(R.id.rbgGroup)).getCheckedRadioButtonId()) {
                case R.id.rbInternal:
                    return fileHelper.loadFromInternalStorage("internal.txt"); //load message from internal storage
                case R.id.rbExternal:
                    return fileHelper.loadFromExternalStorage("external.txt"); //load message from external storage
                case R.id.rbSQLite:
                    return loadFromSQLite(); //load message from SQLite database
                default:
                    return "NOTHING LOADED!"; //nothing stored, return error message
            }
        }
        else{
            return null;
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private void saveToSQLite(String message){
        sqLiteTextHelper.saveText(message); //send message to SQlite database
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private String loadFromSQLite(){
        return sqLiteTextHelper.loadText(); //load message from SQLite database
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    private void flashOn() throws CameraAccessException
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        String camId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(camId, true);
//        Toast.makeText(this, "Flashlight is on", Toast.LENGTH_SHORT).show();
    }

    private void flashOff() throws CameraAccessException, InterruptedException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE); //get camera service on phone

        String camId = cameraManager.getCameraIdList()[0]; //get camera to turn off the flashlight
        cameraManager.setTorchMode(camId, false); //turn off the flashlight
//        Toast.makeText(this, "Flashlight is on", Toast.LENGTH_SHORT).show();
        Thread.sleep(7000); //wait 7 seconds as between words in message.
    }

    //Converting and timing message//

    private char[] convert(TextView view){ //convert message to char array
        String input = view.getText().toString().toUpperCase();
        input.split("\\s"); //splitting the message where there is a space. If the character contains a space then 7 seconds needed.
        StringBuilder s = new StringBuilder(input);
        s.append('#'); //putting random character to register as a space between words in the message
        char[] array = input.toCharArray(); //convert to character array to make switch statement easier
        return array; //returning character array
    }

    private int calcFlashtime(char[] e) //function to get time to flash message in seconds
    {
        int time = 0; //convert milliseconds into minutes!!!
        int dot = 1; //1 second
        int dash = 3; //3 seconds
        for(char r: e) //go through entire character array
        {
            switch (r){ //add time in seconds depending on current character in char array.
                case ' ':
                    time += 3; //add 3 seconds
                    break;
                case 'A':
                    time += 4; //add 4 seconds
                    break;
                case 'B':
                    time += 6; //...
                    break;
                case 'C':
                    time += 8;
                    break;
                case 'D':
                    time += 5;
                    break;
                case 'E':
                    time += 1;
                    break;
                case 'F':
                    time += 6;
                    break;
                case 'G':
                    time += 7;
                    break;
                case 'H':
                    time += 4;
                    break;
                case 'I':
                    time += 2;
                    break;
                case 'J':
                    time += 10;
                    break;
                case 'K':
                    time += 7;
                    break;
                case 'L':
                    time += 8;
                    break;
                case 'M':
                    time += 6;
                    break;
                case 'N':
                    time += 4;
                    break;
                case 'O':
                    time += 9;
                    break;
                case 'P':
                    time += 8;
                    break;
                case 'Q':
                    time += 10;
                    break;
                case 'R':
                    time += 5;
                    break;
                case 'S':
                    time += 3;
                    break;
                case 'T':
                    time += 3;
                    break;
                case 'U':
                    time += 5;
                    break;
                case 'V':
                    time += 6;
                    break;
                case 'W':
                    time += 7;
                    break;
                case 'X':
                    time += 8;
                    break;
                case 'Y':
                    time += 10;
                    break;
                case 'Z':
                    time += 8;
                    break;
                case '0':
                    time += 5;
                    break;
                case '1':
                    time += 13;
                    break;
                case '2':
                    time += 11;
                    break;
                case '3':
                    time += 9;
                    break;
                case '4':
                    time += 7;
                    break;
                case '5':
                    time += 5;
                    break;
                case '6':
                    time += 7;
                    break;
                case '7':
                    time += 9;
                    break;
                case '8':
                    time += 11;
                    break;
                case '9':
                    time += 13;
                    break;
            }
            time+=3; //add three seconds to signify the pause between different letters
        }
        time+= -3; //take off time between letter from last letter in word
        return time; //return how long it will take to flash the message
    }
}
