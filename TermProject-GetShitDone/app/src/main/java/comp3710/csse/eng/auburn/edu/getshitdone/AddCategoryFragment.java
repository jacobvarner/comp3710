package comp3710.csse.eng.auburn.edu.getshitdone;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;


public class AddCategoryFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Task Category")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something with the category
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}