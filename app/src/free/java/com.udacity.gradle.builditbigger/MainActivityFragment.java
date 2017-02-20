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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeListener{

    Button button;
    InterstitialAd mInterstitialAd;
    ProgressBar mSpinner;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        button = (Button)root.findViewById(R.id.joke_button);
        mSpinner = (ProgressBar)root.findViewById(R.id.progressbar);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startJokeActivity();
            }
        });

        requestNewInterstitial();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpinner.setVisibility(View.INVISIBLE);
                if (mInterstitialAd.isLoaded()){
                    mInterstitialAd.show();
                }
                else {
                    startJokeActivity();
                }
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);
        return root;
    }

    @Override
    public void onReceived(String joke) {
        Intent intent = new Intent(getActivity(), JokeDisplay.class);
        intent.putExtra("joke", joke);
        this.startActivity(intent);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void startJokeActivity(){
        requestNewInterstitial();
        mSpinner.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask(this).execute();
    }
}
