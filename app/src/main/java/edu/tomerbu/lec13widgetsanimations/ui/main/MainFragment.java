package edu.tomerbu.lec13widgetsanimations.ui.main;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import edu.tomerbu.lec13widgetsanimations.R;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private Button btn;
    private FancyButton fancy;
    private ConstraintLayout layout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    private boolean isRotated = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.message);
        fancy = view.findViewById(R.id.button);
        layout = view.findViewById(R.id.main);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator colorAnimator = ValueAnimator.ofArgb(0xffffffff, 0xff00ddff);
            colorAnimator.setDuration(1000);



            colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int)animation.getAnimatedValue();
                    layout.setBackgroundColor(value);
                }
            });

            //start the animation in onClick
            layout.setOnClickListener(v->{
                colorAnimator.start();
            });
        }


        btn.setOnClickListener(v -> {
            float angle = 0;
            float scale = 0;
            float translationY = 0;
            if (isRotated) {
                angle = 0;
                scale = 1;
                translationY = 0;
            } else {
                angle = 360;
                scale = 2;
                translationY = -200;
            }

            isRotated = !isRotated;

            fancy.animate().setDuration(1000).rotation(angle).scaleX(scale).scaleY(scale).translationY(translationY);
        });


        btn.setAlpha(0);
        fancy.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Fancy", Toast.LENGTH_SHORT).show();

            //show animation:
            //alpha from 0 to 1
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //manually change the values:

                    //1) get the current value:
                    float currentAlpha = (float) animation.getAnimatedValue();
                    //2) use it for alpha:
                    btn.setAlpha(currentAlpha);
                }
            });

            animator.start();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

}
