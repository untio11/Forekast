package com.example.forekast.Wardrobe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.forekast.EditScreen.EditScreen;
import com.example.forekast.R;
import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.Feet;
import com.example.forekast.Clothing.Legs;
import com.example.forekast.Clothing.Torso;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Wardrobe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Wardrobe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Wardrobe extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WardrobeViewModel vm;

    public Wardrobe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Wardrobe.
     */
    public static Wardrobe newInstance() {
        Wardrobe fragment = new Wardrobe();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Publicly get an array of clothing types of the wanted body type location.
     * Should correspond with the switch statements in Torso, Legs and Feet classes.
     *
     * @param location string that indicates which body type we want clothing types for
     * @return array of strings with clothing types of the wanted body type
     */
    public static String[] getTypes(String location) {
        switch (location) {
            case "Torso":
                return new String[]{"T-Shirt", "Dress", "Jacket", "Shirt", "Sweater", "Tanktop"};
            case "Legs":
                return new String[]{"Jeans", "Shorts", "Skirt", "Trousers"};
            case "Feet":
                return new String[]{"Shoes", "Sandals", "Sneakers", "Formal", "Boots"};
            default:
                return null;
        }
    }

    /**
     * Get lists from viewmodel that belong to the set user in settings
     */
    @Override
    public void onResume() {
        super.onResume();
        vm.getLists(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wardrobe_overview, container, false);

        // Viewmodel stuff
        vm = ViewModelProviders.of(this).get(WardrobeViewModel.class);

        // Set the ImageButtons from layout
        ImageButton addTorso = view.findViewById(R.id.addTorso);
        ImageButton addLegs = view.findViewById(R.id.addBottom);
        ImageButton addFeet = view.findViewById(R.id.addShoes);

        // The add torso button will add a new clothing of type torso
        addTorso.setOnClickListener(v -> {
            Clothing torso = new Torso();
            // Navigate to edit screen
            navigateEditscreen(torso);
        });

        // The add bottom button will add a new clothing of type bottom
        addLegs.setOnClickListener(v -> {
            Clothing legs = new Legs();
            // Navigate to edit screen
            navigateEditscreen(legs);
        });

        // The add shoes button will add a new clothing of type shoes
        addFeet.setOnClickListener(v -> {
            Clothing feet = new Feet();
            // Navigate to edit screen
            navigateEditscreen(feet);
        });

        // Set the checkbox from layout
        CheckBox showWashing = view.findViewById(R.id.showWashing);
        // If availability system is checked on
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("availability_system", true)) {
            // Set the boolean value of checkbox
            showWashing.setChecked(vm.getWashing());
        } else {
            // Else, hide the checkbox if availability system is off
            showWashing.setVisibility(View.GONE);
        }

        // Execute loading of database in background, continue with setLists when done
        Observer<List<Clothing>> torsoObs = torsoList -> setLists(view, torsoList, (CustomGridView) view.findViewById(R.id.Torso));
        Observer<List<Clothing>> legsObs = legsList -> setLists(view, legsList, (CustomGridView) view.findViewById(R.id.Bottom));
        Observer<List<Clothing>> feetObs = feetList -> setLists(view, feetList, (CustomGridView) view.findViewById(R.id.Shoes));

        vm.getTorsoList().observe(this, torsoObs);
        vm.getLegsList().observe(this, legsObs);
        vm.getFeetList().observe(this, feetObs);

        // Initially already get the lists from viewmodel that belong to set user in settings (can be deleted?)
        vm.getLists(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));

        // Check if checkbox for showing items in washingMachine is checked off.
        showWashing.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vm.setWashing(isChecked);
            vm.getLists(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));
        });

        getFragmentManager().addOnBackStackChangedListener(() -> { // Ensure the wardrobe overview is reloaded upon entry by pressing back
            FragmentManager fm = getFragmentManager();

            if (fm == null) return;
            List<Fragment> fragments = fm.getFragments();
            if (fragments.size() > 0 && fragments.get(fragments.size() - 1) instanceof Wardrobe) {
                vm.getLists(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general"));
            }
        });

        // return view
        return view;
    }

    /**
     * Navigate from wardrobe to editscreen, and pass the clothing object we just added
     * @param clothing a Clothing object we just added (either Torso, Legs or Feet)
     */
    private void navigateEditscreen(Clothing clothing) {
        // First get which clothing types can be added (of this body type)
        String[] types = getTypes(clothing.location);
        // Pop up
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("What do you want to add?")
                .setItems(types,
                        (dialog, which) -> {
                            // The 'which' argument contains the index position
                            // of the selected item
                            // Set clothing type in the current clothing object
                            clothing.type = types[which];
                            // Navigate to the editscreen and pass the clothing object
                            Fragment fragment = EditScreen.newInstance(clothing, true);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.wardrobe_container, fragment).addToBackStack("edit").commit();
                        }).show();
    }

    /**
     * Executed after the observer notices change in the wardrobe lists. Puts the clothing objects
     * from a list (either Torso, Legs or Feet) into the corresponding gridView.
     * @param view the view wardrobe_overview
     * @param list the List of Clothing objects we want to put into a gridView
     * @param listView the customGridView (customized gridView) we want to put the clothing in
     */
    private void setLists(View view, List<Clothing> list, CustomGridView listView) {
        // First sort the list such that all clothing types are grouped together
        list.sort((c1, c2) -> c1.type.compareTo(c2.type));
        // Create a new WardrobeAdapter, put the clothing objects into the gridView
        WardrobeAdapter adapter = new WardrobeAdapter(view.getContext(), R.layout.wardrobe_entry, list);
        listView.setAdapter(adapter);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NotNull Context context) {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
