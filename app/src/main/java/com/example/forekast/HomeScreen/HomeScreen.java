package com.example.forekast.HomeScreen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.forekast.R;
import com.example.forekast.Suggestion.Outfit;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.ExternalData.Weather;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This class takes care of handling the interactions with the home screen of our app.
 */
public class HomeScreen extends Fragment {
    private static HomeScreenViewModel vm;
    private View view;

    /**
     * Inflate the layout and start the initialization.
     * @param inflater see super.
     * @param container see super
     * @param savedInstanceState Stores saved state variables (slider values, etc).
     * @return The view that was created.
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_screen, container, false);
        vm = ViewModelProviders.of(Objects.requireNonNull(this.getActivity())).get(HomeScreenViewModel.class);

        init(savedInstanceState);

        return view;
    }

    /**
     * Refresh the outfit that is currently being suggested.
     */
    public static void newOutfit() {
        vm.newOutfit();
    }

    /**
     * Initialize the homes screen by adding listeners and setting default values and getting a
     * reference to the view model.
     * @param savedInstance Possibly has small state variables stored.
     */
    private void init(Bundle savedInstance) {
        vm = ViewModelProviders.of(getActivity()).get(HomeScreenViewModel.class);

        // Observe the LiveData for the outfit, passing in this activity as the LifecycleOwner and the observer.
        vm.getLiveOutfit().observe(
                getActivity(), this::updateOutfit);


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
                new ClothingCriteria.MutablePair<>(vm.getWarmth(), vm.getWarmth()),
                new ClothingCriteria.MutablePair<>(vm.getFormality(), vm.getFormality()),
                new ClothingCriteria.MutablePair<>(vm.getComfort(), vm.getComfort()),
                new ClothingCriteria.MutablePair<>(10, 10),
                PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));

        // Ensure that the view model is initially updated after startup.
        // Cannot be a lambda because it needs to reference itself.
        vm.getLiveWeather().observe(getActivity(), (new Observer<Weather>() {
            @Override
            public void onChanged(Weather weather) {
                if (weather == null) return;
                vm.sugg.setCurrentCriteria(vm.getClothingCriteria(), weather);
                vm.getLiveWeather().removeObserver(this); // Remove after first update
            }
        }));
    }

    /**
     * Outfit change listener. Whenever the outfit in the view model changes, this will update the
     * ui accordingly.
     * @param newOutfit The new outfit that is in use.
     */
    private void updateOutfit(Outfit newOutfit) {
        Log.d("ClothingUpdate", (newOutfit != null ? newOutfit.toString() : "No clothes"));


        if (newOutfit != null && newOutfit.torso != null) {
            if (newOutfit.torso.one && !newOutfit.torso.two) {
                System.out.println(newOutfit.torso.torso);
            } else {
                System.out.println(newOutfit.torso.inner);
                System.out.println(newOutfit.torso.outer);
            }

            System.out.println(newOutfit.pants);
            System.out.println(newOutfit.shoes);

            setOutfitImages();
            accessories(); //New accessories every time we have a clothing update,
        }
    }

    /**
     * Set the image views in the ui to the images of the current outfit
     */
    private void setOutfitImages() {
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

        if (outfit != null && outfit.torso != null) {
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
            } else {
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

        if (outfit != null && outfit.pants != null) {
            if (outfit.pants.picture != null) {
                bitmapP = BitmapFactory.decodeByteArray(outfit.pants.picture, 0, outfit.pants.picture.length);
                bottoms.setImageBitmap(bitmapP);
            } else {
                bottoms.setImageResource(R.drawable.pants);
            }

            if (outfit.torso.torso != null) {
                if (outfit.torso.torso.type.equals("Dress")) {
                    bottomsLayout.setVisibility(View.GONE);
                }
            } else if (outfit.torso.inner != null) {
                if (outfit.torso.inner.type.equals("Dress")) {
                    bottomsLayout.setVisibility(View.GONE);
                }
            }
        }

        if (outfit != null && outfit.shoes != null) {
            if (outfit.shoes.picture != null) {
                bitmapS = BitmapFactory.decodeByteArray(outfit.shoes.picture, 0, outfit.shoes.picture.length);
                shoes.setImageBitmap(bitmapS);
            } else {
                shoes.setImageResource(R.drawable.shoe);
            }
        }
    }

    /**
     * Colour the accessory images based on whether they are suggested or not.
     */
    private void accessories() {
        vm.sugg.setAccessories();
        boolean[] accessories = vm.getAccessories();
        ImageView[] accessory_views = new ImageView[accessories.length];

        accessory_views[0] = view.findViewById(R.id.noti_sunglasses);
        accessory_views[1] = view.findViewById(R.id.noti_coat);
        accessory_views[2] = view.findViewById(R.id.noti_gloves);
        accessory_views[3] = view.findViewById(R.id.noti_umbrella);
        accessory_views[4] = view.findViewById(R.id.noti_leggings);

        for (int i = 0; i < accessories.length; i++) {
            accessory_views[i].setColorFilter(accessories[i] ? Color.BLACK : Color.LTGRAY);
        }
    }

    /**
     * Listener for the 'reset' button in the ui. Refreshes the entire outfit.
     * @param v
     */
    private void refreshClothing(View v) {
        vm.refreshClothing();
        Log.d("Refresh", v.getTag().toString());
    }

    /**
     * Listener for the 'next' button in the ui. Cycles to the next piece of clothing of its type
     * in the current suggestions.
     * @param v The view of the button. Each button that uses this listener has a tag containing its location.
     */
    private void nextClothing(View v) {
        vm.nextClothing(v.getTag().toString());
        Log.d("Next", v.getTag().toString());
    }

    /**
     * Listener for the 'previous' button in the ui. Cycles to the previous piece of clothing of its type
     * in the current suggestions.
     * @param v The view of the button. Each button that uses this listener has a tag containing its location.
     */
    private void prevClothing(View v) {
        vm.previousClothing(v.getTag().toString());
        Log.d("Prev", v.getTag().toString());
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

        /**
         * Update the current criteria in the view model and the suggestion module.
         * @param seekBar The seekbar that was interacted with
         * @param progress The new value of that seekbar
         * @param fromUser Whether it was changed by the user or programatically.
         */
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