package edu.auburn.eng.csse.comp3710.jsv0004.midterm.jsv004scramble;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class History extends Fragment {

    public History() {
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
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        ArrayList<ArrayList<String>> history = MainActivity.history;
        int size = history.size();
        ArrayList<String> leftColumn = new ArrayList<>();
        ArrayList<String> rightColumn = new ArrayList<>();

        if (size % 2 == 0) {
            for (int i = 0; i < size/2; i++) {
                leftColumn.add(history.get(i).get(0));
            }
            for (int i = size/2; i < size; i++) {
                rightColumn.add(history.get(i).get(0));
            }
        } else {
            for (int i = 0; i < (size + 1)/2; i++) {
                leftColumn.add(history.get(i).get(0));
            }
            for (int i = (size + 1)/2; i < size; i++) {
                rightColumn.add(history.get(i).get(0));
            }
        }

        TextView leftTextView = (TextView) getView().findViewById(R.id.textViewLeft);
        TextView rightTextView = (TextView) getView().findViewById(R.id.textViewRight);

        String leftText = "";
        String rightText = "";

        for (String s : leftColumn) {
            leftText += s + "\n";
        }

        for (String s : rightColumn) {
            rightText += s + "\n";
        }

        leftTextView.setText(leftText);
        rightTextView.setText(rightText);
    }
}
