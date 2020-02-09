package com.ist.pingpong;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.ping2pong.R;

public class AddPlayer extends Fragment {
    private static final String PLAYER1 = "param1";
    private static final String PLAYER2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button save;
    private EditText player1;
    private EditText player2;

    private OnFragmentInteractionListener mListener;

    public AddPlayer() {
        // Required empty public constructor
    }

    public static AddPlayer newInstance(String param1, String param2) {
        AddPlayer fragment = new AddPlayer();
        Bundle args = new Bundle();
        args.putString(PLAYER1, param1);
        args.putString(PLAYER2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(PLAYER1);
            mParam2 = getArguments().getString(PLAYER2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_player, container, false);
        player1 = view.findViewById(R.id.player1);
        player1.requestFocus();
        player2 = view.findViewById(R.id.player2);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(player1.getText().toString(), player2.getText().toString());

            }
        });
        return view;
    }

    public void onButtonPressed(String s1, String s2) {
        if (mListener != null) {
            mListener.onFragmentInteraction(s1, s2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String s1, String s2);
    }
}
