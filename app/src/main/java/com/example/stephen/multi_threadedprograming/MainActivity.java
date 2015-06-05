package com.example.stephen.multi_threadedprograming;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.progressBar);
        final ProgressBar progress = (ProgressBar) view;

        final String fileName = "numbers.txt";

        //Set up the list view
        final List<Integer> listOfNumbers = new ArrayList<>();
        final ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>
                (this, android.R.layout.simple_list_item_1, listOfNumbers);
        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);

        View.OnClickListener createListener = new View.OnClickListener() {

            /********************************************************************
             * On Click method for Create button.
             * Create a file and write the numbers 1-10 to it
             * @param v
             ********************************************************************/
            @Override
            public void onClick(View v) {

                Runnable runnable = new Runnable() {

                    public void run() {
                        //Reset progress bar
                        progress.setProgress(0);
                        BufferedWriter writer = null;
                        try {
                            //Create the file
                            File file = new File(fileName);

                            //Open the file and create a writer
                            writer = new BufferedWriter(new FileWriter(file));


                            //Write numbers 1-10 to the file
                            for (int i = 1; i < 11; i++) {

                                String string = Integer.toString(i);
                                writer.write(string);

                                //Simulate a difficult task
                                Thread.sleep(250);

                                //Update progress bar
                                progress.setProgress(i * 10);
                            }

                            //Close the file
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread create = new Thread(runnable);
                create.start();
            }
        };

        View.OnClickListener loadListener = new View.OnClickListener() {

            /*************************************************
             * On click method for Load button
             * Populate the adapter and notify of changes
             * @param v
             *************************************************/
            @Override
            public void onClick(View v) {
                //What to do when load button is clicked
                Runnable runnable = new Runnable() {
                    public void run() {
                        try {
                            //Create a buffer
                            FileReader fileReader = new FileReader(fileName);
                            BufferedReader bufferedReader = new BufferedReader(fileReader);

                            //Populate our adapter
                            for (int i = 1; i < 11; i++) {
                                //Read a file line into the adapter
                                adapter.add(Integer.parseInt(bufferedReader.readLine()));

                                //Notify of changes
                                adapter.notifyDataSetChanged();

                                //Simulate something difficult
                                Thread.sleep(250);

                                //Update progress
                                progress.setProgress(i * 10);
                            }

                            //Close file
                            fileReader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread load = new Thread(runnable);
                load.start();
            }
        };

        View.OnClickListener clearListener = new View.OnClickListener() {

            /*************************************************************
             * On click method for Clear button
             * Reset progress bar and clear the adapter. Notify of changes
             * @param v
             ************************************************************/
            @Override
            public void onClick(View v) {
                //What to do when clear button is clicked
                progress.setProgress(0);
                adapter.clear();
                adapter.notifyDataSetChanged();
            }
        };

        //Create button press event handler
        Button createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener(createListener);

        //Load button press event handler
        Button loadButton = (Button)findViewById(R.id.loadButton);
        loadButton.setOnClickListener(loadListener);

        //Clear button press event handler
        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(clearListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
