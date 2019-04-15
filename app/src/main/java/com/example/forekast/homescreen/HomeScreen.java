package com.example.forekast.homescreen;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import com.example.forekast.R;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.clothing.ClothingCriteriaInterface.*;
import com.example.forekast.clothing.TorsoClothing;
import com.example.forekast.external_data.Repository;
import com.example.forekast.external_data.Weather;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
public class HomeScreen extends Fragment {
    private static HomeScreenViewModelInterface vm;
    private View view;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_screen, container, false);
        vm = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(HomeScreenViewModel.class);
        init(savedInstanceState);

        return view;
    }

    private void init(Bundle savedInstance) {
        vm = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);
        Repository.initDB(getActivity().getApplicationContext());

        // Observe the LiveData for the outfit, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveOutfit().observe(
                getActivity(), this::initOutfit);


        if (savedInstance != null) {
            vm.setComfort(savedInstance.getInt("Comfortsl"));
            vm.setFormality(savedInstance.getInt("Formalsl"));
            vm.setWarmth(savedInstance.getInt("Warmthsl"));
        } else {
            vm.setComfort(5);
            vm.setFormality(5);
            vm.setWarmth(5);
        }

        SeekBar comfort_slider = view.findViewById(R.id.slider_comfort);
        comfort_slider.setMax(10);
        comfort_slider.setProgress(vm.getComfort());
        comfort_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());

        SeekBar warmth_slider = view.findViewById(R.id.slider_warmth);
        warmth_slider.setMax(10);
        warmth_slider.setProgress(vm.getWarmth());
        warmth_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());

        SeekBar formality_slider = view.findViewById(R.id.slider_formality);
        formality_slider.setMax(10);
        formality_slider.setProgress(vm.getFormality());
        formality_slider.setOnSeekBarChangeListener(new updateCriteriaSeekBar());

        view.findViewById(R.id.refresh).setOnClickListener(this::refreshClothing);
        view.findViewById(R.id.next_feet).setOnClickListener(this::nextClothing);
        view.findViewById(R.id.next_legs).setOnClickListener(this::nextClothing);
        view.findViewById(R.id.next_torso).setOnClickListener(this::nextClothing);
        view.findViewById(R.id.prev_feet).setOnClickListener(this::prevClothing);
        view.findViewById(R.id.prev_legs).setOnClickListener(this::prevClothing);
        view.findViewById(R.id.prev_torso).setOnClickListener(this::prevClothing);

        vm.clothingCriteria = new ClothingCriteria(
                new MutablePair<>(vm.getWarmth(), vm.getWarmth()),
                new MutablePair<>(vm.getFormality(), vm.getFormality()),
                new MutablePair<>(vm.getComfort(), vm.getComfort()),
                new MutablePair<>(10, 10),
                PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));


        vm.getLiveWeather().observe(getActivity(), (new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather == null) return;
                vm.sugg.setCurrentCriteria(vm.getClothingCriteria(), weather);
                vm.getLiveWeather().removeObserver(this); // Remove after first update
            }
        }));
    }



    private void initOutfit(Outfit newOutfit) {
        Log.d("ClothingUpdate", (newOutfit != null ? newOutfit.toString() : "No clothes"));

        if (newOutfit != null) {
            if (newOutfit.torso != null) {
                if (newOutfit.torso.one && !newOutfit.torso.two) {
                    System.out.println(newOutfit.torso.torso);
                } else {
                    System.out.println(newOutfit.torso.inner);
                    System.out.println(newOutfit.torso.outer);
                }
            }
            System.out.println(newOutfit.pants);
            System.out.println(newOutfit.shoes);
            //newOutfit();
            setOutfit();
            accessories(); //New accessories every time we have a clothing update,

        }
    }

    public void setOutfit() {
        Bitmap bitmapIT;
        Bitmap bitmapOT;
        Bitmap bitmapP;
        Bitmap bitmapS;

        ImageView innerTorso = view.findViewById(R.id.innerTorso);
        ImageView outerTorso = view.findViewById(R.id.outerTorso);
        ImageView bottoms = view.findViewById(R.id.bottoms);
        ImageView shoes = view.findViewById(R.id.shoes);
        LinearLayout bottomsLayout = view.findViewById(R.id.bottomsLayout);

        bottomsLayout.setVisibility(View.VISIBLE);
        innerTorso.setVisibility(View.VISIBLE);
        outerTorso.setVisibility(View.VISIBLE);
        Outfit outfit = vm.currentOutfit.getValue();

        if (outfit.torso != null){
            if (outfit.torso.one && !outfit.torso.two) {
                if (outfit.torso.torso != null) {
                    if (outfit.torso.torso.picture != null) {
                        bitmapIT = BitmapFactory.decodeByteArray(outfit.torso.torso.picture, 0, outfit.torso.torso.picture.length);
                        innerTorso.setImageBitmap(bitmapIT);
                    } else {
                        innerTorso.setImageResource(R.drawable.shirt);
                    }
                    outerTorso.setVisibility(View.GONE);
                }
            }
            else {
                if (outfit.torso.inner.picture != null) {
                    bitmapIT = BitmapFactory.decodeByteArray(outfit.torso.inner.picture, 0, outfit.torso.inner.picture.length);
                    innerTorso.setImageBitmap(bitmapIT);
                } else {
                    innerTorso.setImageResource(R.drawable.shirt);
                }
                if (outfit.torso.outer.picture != null) {
                    bitmapOT = BitmapFactory.decodeByteArray(outfit.torso.outer.picture, 0, outfit.torso.outer.picture.length);
                    outerTorso.setImageBitmap(bitmapOT);
                } else {
                    outerTorso.setImageResource(R.drawable.sweater);
                }
            }
        }

        if (outfit.pants != null){
            if (outfit.pants.picture != null) {
                bitmapP = BitmapFactory.decodeByteArray(outfit.pants.picture, 0, outfit.pants.picture.length);
                bottoms.setImageBitmap(bitmapP);
            }
            else {
                bottoms.setImageResource(R.drawable.pants);
            }

            if (outfit.torso.torso != null){
                if(outfit.torso.torso.type.equals("Dress")) {
                    bottomsLayout.setVisibility(View.GONE);
                }
            }
            else if (outfit.torso.inner != null) {
                if (outfit.torso.inner.type.equals("Dress")) {
                    bottomsLayout.setVisibility(View.GONE);
                }
            }
        }

        if (outfit.shoes != null){
            if (outfit.shoes.picture != null) {
                bitmapS = BitmapFactory.decodeByteArray(outfit.shoes.picture, 0, outfit.shoes.picture.length);
                shoes.setImageBitmap(bitmapS);
            } else {
                shoes.setImageResource(R.drawable.shoe);
            }
        }
    }

    private void accessories(){
        vm.sugg.setAccessories();

        ImageView sunglassesView = view.findViewById(R.id.noti_sunglasses);
        ImageView coatView = view.findViewById(R.id.noti_coat);
        ImageView glovesView = view.findViewById(R.id.noti_gloves);
        ImageView umbrellaView = view.findViewById(R.id.noti_umbrella);
        ImageView leggingsView = view.findViewById(R.id.noti_leggings);

        boolean[] accessories = vm.getAccessories();

        if (accessories[0]) { // Sunglasses
            sunglassesView.setColorFilter(Color.BLACK);
        }
        else {
            sunglassesView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[1]) { // Coat
            coatView.setColorFilter(Color.BLACK);
        }
        else {
            coatView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[2]) { // Gloves
            glovesView.setColorFilter(Color.BLACK);
        }
        else {
            glovesView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[3]) { // Umbrella
            umbrellaView.setColorFilter(Color.BLACK);
        }
        else {
            umbrellaView.setColorFilter(Color.LTGRAY);
        }
        if (accessories[4]) { // Leggings
            leggingsView.setColorFilter(Color.BLACK);
        }
        else {
            leggingsView.setColorFilter(Color.LTGRAY);
        }
    }

    public static void newOutfit() {
        vm.newOutfit();
    }

    public void refreshClothing(View v) {
        vm.refreshClothing();
        Log.d("Refresh", v.getTag().toString());
    }

    public void nextClothing(View v) {
        vm.nextClothing(v.getTag().toString());
        Log.d("Next", v.getTag().toString());
    }

    public void prevClothing(View v) {
        vm.previousClothing(v.getTag().toString());
        Log.d("Prev", v.getTag().toString());
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

        savedInstanceState.putInt("Warmthsl", vm.getWarmth());
        savedInstanceState.putInt("Comfortsl", vm.getComfort());
        savedInstanceState.putInt("Formalsl", vm.getFormality());
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
            ClothingCriteria criteria = vm.getClothingCriteria();

            // Set the new clothing criteria from seekbars
            criteria.warmth.first = vm.getWarmth();
            criteria.warmth.second = vm.getWarmth();
            criteria.formality.first = vm.getFormality();
            criteria.formality.second = vm.getFormality();
            criteria.comfort.first = vm.getComfort();
            criteria.comfort.second = vm.getComfort();
            criteria.preference.first = 10;
            criteria.preference.second = 10;

            vm.sugg.setCurrentCriteria(criteria, vm.getWeather());

            System.out.println(criteria);
        }
    }
}
