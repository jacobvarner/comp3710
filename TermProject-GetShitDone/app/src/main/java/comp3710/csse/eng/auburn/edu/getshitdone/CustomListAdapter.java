package comp3710.csse.eng.auburn.edu.getshitdone;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter {

    private ArrayList<String[]> tasks;
    private Context mContext;
    int viewId;

    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<String[]> list) {
        super(context, textViewResourceId, list);
        mContext = context;
        viewId = textViewResourceId;
        tasks = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View mView = view;
        if (mView == null) {
            LayoutInflater v = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = v.inflate(viewId, null);
        }

        TextView text = (TextView) mView.findViewById(R.id.textView);

        if (tasks.get(position)[1] != null) {
            if (text != null) {
                text.setTextColor(Color.BLACK);
                text.setText(tasks.get(position)[1]);
                if (tasks.get(position)[3].equals("true")) {
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        }

        return mView;
    }

}