package com.example.greyvibrant.front.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.greyvibrant.R;

public class UserDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private UserDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view)
                .setTitle("Forgot Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString().trim();
                        String email = editTextEmail.getText().toString().trim();
                        String password = editTextPassword.getText().toString().trim();
                        listener.applyTexts(username, email, password);
                    }
                });
        editTextUsername = view.findViewById(R.id.verify_username);
        editTextEmail = view.findViewById(R.id.verify_email);
        editTextPassword = view.findViewById(R.id.new_password);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

            listener = (UserDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement UserDialogListener");
        }
    }

    public interface UserDialogListener {
        void applyTexts(String username, String email, String password);
    }
}
