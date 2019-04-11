package com.example.forekast.homescreen;

import android.graphics.Color;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Suggestion.Outfit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HomeScreen extends Fragment {

    private HomeScreenViewModelInterface vm;
    private View view;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_screen, container, false);
        vm = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(HomeScreenViewModel.class);
        init(savedInstanceState);
        return view;
    }

    public void accessories(){
        Boolean sunglasses = false;
        Boolean coat = false;
        Boolean gloves = false;
        Boolean umbrella = false;
        Boolean leggings = false;

        ImageView sunglassesView = view.findViewById(R.id.noti_sunglasses);
        ImageView coatView = view.findViewById(R.id.noti_coat);
        ImageView glovesView = view.findViewById(R.id.noti_gloves);
        ImageView umbrellaView = view.findViewById(R.id.noti_umbrella);
        ImageView leggingsView = view.findViewById(R.id.noti_leggings);

        if (sunglasses) {
            sunglassesView.setColorFilter(Color.GRAY);
        }
        else {
            sunglassesView.setColorFilter(Color.BLACK);
        }
        if (coat) {
            coatView.setColorFilter(Color.GRAY);
        }
        else {
            coatView.setColorFilter(Color.BLACK);
        }
        if (gloves) {
            glovesView.setColorFilter(Color.GRAY);
        }
        else {
            glovesView.setColorFilter(Color.BLACK);
        }
        if (umbrella) {
            umbrellaView.setColorFilter(Color.GRAY);
        }
        else {
            umbrellaView.setColorFilter(Color.BLACK);
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Wardrobe.
     */
    public static HomeScreen newInstance() {
        HomeScreen fragment = new HomeScreen();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

  @Override
    public void onSaveInstanceState(@NotNull Bundle savedInstanceState) {
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

        // Save the user's current game state
        savedInstanceState.putInt("Warmthsl", vm.getWarmth());
        savedInstanceState.putInt("Comfortsl", vm.getComfort());
        savedInstanceState.putInt("Formalsl", vm.getFormality());
    }

    private void init(Bundle savedInstance) {
        if (savedInstance != null) {
            vm.setComfort(savedInstance.getInt("Comfortsl"));
            vm.setFormality(savedInstance.getInt("Formalsl"));
            vm.setWarmth(savedInstance.getInt("Warmthsl"));
        }

        SeekBar comfort_slider = view.findViewById(R.id.slider_comfort);
        comfort_slider.setProgress(vm.getComfort() == Integer.MAX_VALUE ? 3 : vm.getComfort());
        comfort_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());

        SeekBar warmth_slider = view.findViewById(R.id.slider_warmth);
        warmth_slider.setProgress(vm.getWarmth() == Integer.MAX_VALUE ? 3 : vm.getWarmth());
        warmth_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());

        SeekBar formality_slider = view.findViewById(R.id.slider_formality);
        formality_slider.setProgress(vm.getFormality() == Integer.MAX_VALUE ? 3 : vm.getFormality());
        formality_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());



        final Observer<Outfit> clothingObserver = newClothing -> Log.d("ClothingUpdate", (newClothing != null ? newClothing.toString() : "No clothes"));

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        // TODO: For merging, note that it is important to use the this.getActivity() instead of just this.
        vm.getLiveOutfit().observe(Objects.requireNonNull(this.getActivity()), clothingObserver);
        vm.getLiveWeather().observe(
                this.getActivity(),
                newWeather -> Log.d(
                        "WeatherUpdate",
                        (newWeather != null ? newWeather.toString() : "No weather")
                )
        );
    }

    public void refreshClothing(View v) {
        vm.newOutfit();
    }

    public void nextClothing(View v) {
        vm.nextClothing(v.getTag().toString());
        Log.d("Next", v.getTag().toString());
    }

    public void prevClothing(View v) {
        vm.previousClothing(v.getTag().toString());
        Log.d("Prev", v.getTag().toString());
    }


    private class updateCriteriaSeekBar implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            String category = seekBar.getTag().toString();
            Log.d("SeekbarListener", "Updating " + category + " to: " + progress);
            switch (category) {
                case "warmth":
                    vm.setWarmth(progress);
                    break;
                case "formality":
                    vm.setFormality(progress);
                    break;
                case "comfort":
                    vm.setComfort(progress);
                    break;
                default:
                    break;
            }
        }
    }
}
