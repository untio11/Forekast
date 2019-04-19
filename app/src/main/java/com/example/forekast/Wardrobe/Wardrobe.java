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
    // TODO: Rename and change types and number of parameters
    public static Wardrobe newInstance() {
        Wardrobe fragment = new Wardrobe();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

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

        ImageButton addTorso = view.findViewById(R.id.addTorso);
        ImageButton addLegs = view.findViewById(R.id.addBottom);
        ImageButton addFeet = view.findViewById(R.id.addShoes);

        CheckBox showWashing = view.findViewById(R.id.showWashing);
        showWashing.setChecked(vm.getWashing());

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

        // Execute loading of database in background
        Observer<List<Clothing>> torsoObs = torsoList -> setLists(view, torsoList, (CustomGridView) view.findViewById(R.id.Torso));
        Observer<List<Clothing>> legsObs = legsList -> setLists(view, legsList, (CustomGridView) view.findViewById(R.id.Bottom));
        Observer<List<Clothing>> feetObs = feetList -> setLists(view, feetList, (CustomGridView) view.findViewById(R.id.Shoes));

        vm.getTorsoList().observe(this, torsoObs);
        vm.getLegsList().observe(this, legsObs);
        vm.getFeetList().observe(this, feetObs);

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

    private void navigateEditscreen(Clothing clothing) {
        String[] types = getTypes(clothing.location);
        // Pop up
        new android.app.AlertDialog.Builder(getContext())
                .setTitle("What do you want to add?")
                .setItems(types,
                        (dialog, which) -> {
                            // The 'which' argument contains the index position
                            // of the selected item
                            clothing.type = types[which];
                            // Navigate to the editscreen and pass the clothing objects
                            Fragment fragment = EditScreen.newInstance(clothing, true);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.wardrobe_container, fragment).addToBackStack("edit").commit();
                        }).show();
    }

    private void setLists(View view, List<Clothing> list, CustomGridView listView) {
        list.sort((c1, c2) -> c1.type.compareTo(c2.type));
        WardrobeAdapter adapter = new WardrobeAdapter(view.getContext(), R.layout.wardrobe_entry, list);
        listView.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
