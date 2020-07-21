package com.example.project2.ui.present.presentlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;

import org.w3c.dom.Text;

import java.util.List;

public class PresentMessageDialogFragment extends DialogFragment {
    private PresentViewModel presentViewModel;
    private PresentAdapter friendAdapter;
    private Present myPresent;

    public interface PresentMessageDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    PresentMessageDialogListener listener;

    public PresentMessageDialogFragment(Present present) {
        this.myPresent = present;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            listener = (PresentMessageDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Fragment must implement PresentAddDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View root = inflater.inflate(R.layout.dialog_present_message, null);

        final EditText writeMessage = root.findViewById(R.id.write_message);
        writeMessage.setHint("친구를 위한 메시지");
        final String[] message = new String[1];

        builder.setView(root).setPositiveButton(R.string.set_message,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message[0] = writeMessage.getText().toString();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PresentMessageDialogFragment.this.getDialog().cancel();
            }
        });

        presentViewModel =
                ViewModelProviders.of(this).get(PresentViewModel.class);

        List<String> currMessages = myPresent.getMessages();
        currMessages.add(message[0]);
        myPresent.setMessages(currMessages);
        presentViewModel.update(myPresent);

        return builder.create();
    }
}

