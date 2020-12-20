package com.example.war.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.war.R;

public class MyDialog extends AppCompatDialogFragment {
    private EditText dialog_LBL_ply1;
    private EditText dialog_LBL_ply2;
    private MyDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_mydialog, null);

        builder.setView(view)
                .setTitle("Enter player names")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String player1 = dialog_LBL_ply1.getText().toString();
                        String player2 = dialog_LBL_ply2.getText().toString();
                        listener.applyTexts(player1, player2);
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.applyTexts("Player1", "Player2");
                    }
                });
        dialog_LBL_ply1 = view.findViewById(R.id.dialog_LBL_ply1);
        dialog_LBL_ply2 = view.findViewById(R.id.dialog_LBL_ply2);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface MyDialogListener {
        void applyTexts(String name1, String name2);
    }

}
