package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.dell.androidlib.JokeDisplay;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeListener{

    Button button;
    ProgressBar mSpinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        button = (Button)root.findViewById(R.id.joke_button);
        mSpinner = (ProgressBar)root.findViewById(R.id.progressbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJokeActivity();
            }
        });

        return root;
    }

    @Override
    public void onReceived(String joke) {
        mSpinner.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getActivity(), JokeDisplay.class);
        intent.putExtra("joke", joke);
        this.startActivity(intent);
    }

    public void startJokeActivity(){
        mSpinner.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask(this).execute();
    }
}
