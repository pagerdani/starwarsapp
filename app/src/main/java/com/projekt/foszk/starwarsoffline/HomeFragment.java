package com.projekt.foszk.starwarsoffline;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import java.util.Timer;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    ImageView homeBkg, homeIntroBkg, homeIntroLogo, tieFighter;

    /////////////////////////////////////////////////////////
    // Create a Timer object to play the timed Tie Fighter sound
    final Timer timer = new Timer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeBkg = (ImageView) view.findViewById(R.id.homeBkg);

        ObjectAnimator homeBkgFadeOut = ObjectAnimator.ofFloat(homeBkg, "alpha", 0, 1);
        homeBkgFadeOut.setDuration(5000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(

                homeBkgFadeOut
        );

        animatorSet.start();

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (timer != null) {
            timer.cancel();
        }
    }
}
