package edu.auburn.eng.csse.comp3710.jsv0004.midterm.jsv004scramble;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class Scramble extends Fragment {
    ArrayList<String> scrambledWords = new ArrayList<>();

    public int width = 5;
    public int start = 0;
    public int end = start + width;

    public Scramble() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scramble, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        scrambledWords = args.getStringArrayList("scrambledWords");
        int size = scrambledWords.size();
        Log.i("ScrambledWords", "size at onStart(): " + size);

        final ArrayList<Integer> spinnerValues = new ArrayList<>();
        if (scrambledWords.size() > 5) {
            spinnerValues.add(5);
        }
        if (scrambledWords.size() > 10) {
            spinnerValues.add(10);
        }
        if (scrambledWords.size() > 20) {
            spinnerValues.add(20);
        }
        spinnerValues.add(scrambledWords.size());

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, spinnerValues);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        Button previous = (Button) getView().findViewById(R.id.buttonPrevious);
        Button next = (Button) getView().findViewById(R.id.buttonNext);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView words = (TextView) getView().findViewById(R.id.scrambledWords);
                Button previous = (Button) getView().findViewById(R.id.buttonPrevious);
                Button next = (Button) getView().findViewById(R.id.buttonNext);

                String w = "";
                width = spinnerValues.get(position);

                if (width == scrambledWords.size()) {
                    previous.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);
                } else {
                    next.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < width; i++) {
                    w += scrambledWords.get(i) + "\n";
                }
                words.setText(w);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        if (start == 0) {
            previous.setVisibility(Button.GONE);
        } else {
            previous.setVisibility(Button.VISIBLE);
        }

        if (end >= scrambledWords.size()) {
            next.setVisibility(Button.GONE);
        } else {
            next.setVisibility(Button.VISIBLE);
        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start = start - width;
                end = start + width;

                TextView words = (TextView) getView().findViewById(R.id.scrambledWords);
                Button previous = (Button) getView().findViewById(R.id.buttonPrevious);
                Button next = (Button) getView().findViewById(R.id.buttonNext);

                String w = "";
                for (int i = start; i < end; i++) {
                    if (!scrambledWords.get(i).isEmpty()) {
                        w += scrambledWords.get(i) + "\n";
                    }
                }
                words.setText(w);

                if (start == 0) {
                    previous.setVisibility(Button.GONE);
                } else {
                    previous.setVisibility(Button.VISIBLE);
                }

                if (end >= scrambledWords.size()) {
                    next.setVisibility(Button.GONE);
                    end = scrambledWords.size();
                } else {
                    next.setVisibility(Button.VISIBLE);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView words = (TextView) getView().findViewById(R.id.scrambledWords);
                Button previous = (Button) getView().findViewById(R.id.buttonPrevious);
                Button next = (Button) getView().findViewById(R.id.buttonNext);
                start = start + width;
                end = start + width;

                if (start == 0) {
                    previous.setVisibility(Button.GONE);
                } else {
                    previous.setVisibility(Button.VISIBLE);
                }

                if (end >= scrambledWords.size()) {
                    next.setVisibility(Button.GONE);
                    end = scrambledWords.size();
                } else {
                    next.setVisibility(Button.VISIBLE);
                }

                String w = "";
                for (int i = start; i < end; i++) {
                    w += scrambledWords.get(i) + "\n";
                }
                words.setText(w);
            }
        });

        TextView words = (TextView) getView().findViewById(R.id.scrambledWords);
        String w = "";
        int count;
        if (scrambledWords.size() < 5) {
            count = scrambledWords.size();
        } else {
            count = 5;
        }
        for (int i = 0; i < count; i++) {
            w += scrambledWords.get(i) + "\n";
        }
        words.setText(w);

    }
}
