package uk.ac.abertay.cmp309.morse2flash;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper { //CLASS TAKEN AND SLIGHTLY MODIFIED FROM LAB 6 CMP309//
    static String DEBUG_TAG = "Morse2FlashBug";

    private static FileHelper instance = null;

    Context parentContext;

    public static FileHelper getInstance(Context context){
        if(instance == null){
            instance = new FileHelper();
        }
        instance.parentContext = context;
        return instance;
    }

    public void saveToInternalStorage(String filename, String data){
        try {
            // Open file output with the file name and the operating mode.
            FileOutputStream fos = parentContext.openFileOutput(filename, Context.MODE_PRIVATE);
            // Write data to file.
            fos.write(data.getBytes());
            // Close output stream.
            fos.close();
            Log.d("DEBUG", "file saved!");
        } catch (IOException e) {
            // Handle error messages here!
            e.printStackTrace();
            Toast.makeText(parentContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    public String loadFromInternalStorage(String filename) {
        String result = "";
        FileInputStream fis = null;
        try {
            // Open file input stream with file name.
            fis = parentContext.openFileInput(filename);
            // Initialise the reader.
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            // Read file line-by-line.
            StringBuilder builder = new StringBuilder();
            String line = null;
            line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n"); // append line data + new line symbol
                line = reader.readLine();
            }
            // this will put all lines in a single string with a line break after each row
            result = builder.toString();
            // Close the stream and the reader.
            reader.close();
            fis.close();
            Log.d(DEBUG_TAG, "file loaded!");
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(parentContext, "File not found!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle other error messages here!
            e.printStackTrace();
            Toast.makeText(parentContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // make sure to handle it properly!
        return null;
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    public void saveToExternalStorage(String filename, String text) {
        Log.w(DEBUG_TAG, "SAVING!!");
        // Create file object specifying a path and a file name.
        File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + filename);
        // Check if file already exists
        if (!myFile.exists()){
            // Create if it doesn't
            try {
                myFile.createNewFile();
                Log.w(DEBUG_TAG, "File created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.w(DEBUG_TAG, "File exists! No need to create!");
        }

        // Create output stream and writer.
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myFile);
            Log.w(DEBUG_TAG, "Stream initialised");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        OutputStreamWriter writer = new OutputStreamWriter(fos);

        try {
            // Write the data
            writer.append(text);
            // Close stream and writer when finished.
            writer.close();
            fos.close();
            Log.d(DEBUG_TAG, "file saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.

    public String loadFromExternalStorage(String filename) {
        String result = "";
        try {
            // Create file object.
            File myFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/" + filename);
            // Check if file exists
            if (!myFile.exists()){
                // handle error if it doesn't
                Toast.makeText(parentContext, "File does not exist!", Toast.LENGTH_SHORT).show();
                Log.e(DEBUG_TAG, "File does not exist!");
                return null; //make sure to handle this
            }
            Log.w(DEBUG_TAG, "File exists, loading...");
            // Create input stream and reader.
            FileInputStream fis = new FileInputStream(myFile);
            Log.w(DEBUG_TAG, "Stream initialised");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            Log.w(DEBUG_TAG, "Reader initialised");
            // Read file line-by-line.
            StringBuilder builder = new StringBuilder();
            Log.w(DEBUG_TAG, "Builder initialised");
            String line = reader.readLine();
            while (line != null) {
                Log.w(DEBUG_TAG, "Reading line...");
                builder.append(line).append("\n"); // append line data + new line symbol
                line = reader.readLine();
            }
            result = builder.toString();

            // Close the stream and reader.
            reader.close();
            fis.close();
            Log.d(DEBUG_TAG, "file loaded!");
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(parentContext, "File not found!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // Handle other error messages here!
            e.printStackTrace();
            Toast.makeText(parentContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    } //Boiko, A 2022, Week 6 Local storage, Abertay University, viewed 07 May, 2022.
}
