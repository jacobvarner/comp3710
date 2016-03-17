package edu.auburn.eng.csse.comp3710.jsv0004.midterm.jsv004scramble;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<ArrayList<String>> history = new ArrayList<ArrayList<String>>();
    private static Set<String> words = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scrambleButton = (Button) findViewById(R.id.buttonScramble);
        Button historyButton = (Button) findViewById(R.id.buttonHistory);

        scrambleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText wordEntry = (EditText) findViewById(R.id.wordToScramble);
                String originalWord = wordEntry.getText().toString();
                Log.i("EditText", "The original word is: " + originalWord);

                if (originalWord.equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "No word to scramble", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<String> historyEntry = new ArrayList<>();
                    historyEntry.add(originalWord);
                    ArrayList<String> scrambledWords = new ArrayList<>();

                    if (originalWord.length() == 2) {
                        String newWord = new StringBuilder(originalWord).reverse().toString();
                        historyEntry.add(newWord);
                        scrambledWords.add(newWord);
                    } else {
                        scrambledWords = scramble(originalWord);
                        scrambledWords = sort(scrambledWords);
                        //takes out the original word before adding to the entry to insure that the original word is first in the ArrayList
                        scrambledWords.remove(originalWord);

                        for (String w : scrambledWords) {
                            historyEntry.add(w);
                        }
                    }

                    Log.i("ScrambledWords", "size in MainActivity: "  + scrambledWords.size());

                    if (!history.contains(historyEntry)) {
                        history.add(historyEntry);
                    }

                    // Begin the transaction
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    // Replace the contents of the container with the new fragment
                    Scramble scrambleFragment = new Scramble();
                    Bundle args = new Bundle();
                    args.putStringArrayList("scrambledWords", scrambledWords);
                    scrambleFragment.setArguments(args);
                    ft.replace(R.id.placeholder, scrambleFragment);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (history.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No history", Toast.LENGTH_SHORT).show();
                } else {
                    // Begin the transaction
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    // Replace the contents of the container with the new fragment
                    History historyFragment = new History();
                    ft.replace(R.id.placeholder, historyFragment);
                    // or ft.add(R.id.your_placeholder, new FooFragment());
                    // Complete the changes added above
                    ft.commit();
                }
            }
        });
    }

    private ArrayList<String> scramble(String word) {
        permutation(word);
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(words);
        words.clear();
        return temp;
    }

    private static Set<String> permutation(String str) {
        permutation("", str);
        return words;
    }

    private static void permutation(String prefix, String str) {
        int n = str.length();
        if (n == 0) {
            words.add(prefix);
        }
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
        }
    }

    private static ArrayList<String> sort(ArrayList<String> in) {
        Collections.sort(in);
        return in;
    }
}
