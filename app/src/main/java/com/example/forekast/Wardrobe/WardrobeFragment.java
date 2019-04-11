
package com.example.forekast.Wardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.example.forekast.EditScreen.EditScreen;
import com.example.forekast.R;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.clothing.Feet;
import com.example.forekast.clothing.Legs;
import com.example.forekast.clothing.Torso;
import com.example.forekast.external_data.Repository;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WardrobeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WardrobeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WardrobeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WardrobeViewModel vm;

    public WardrobeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WardrobeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WardrobeFragment newInstance() {
        WardrobeFragment fragment = new WardrobeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    public static final String[] getTypes(String location) {
        switch (location) {
            case "Torso":
                return new String[]{"T-Shirt", "Sweater", "Jacket", "Shirt", "Dress", "Tanktop"};
            case "Legs":
                return new String[]{"Jeans", "Shorts", "Skirt", "Trousers", "Sweatpants"};
            case "Feet":
                return new String[]{"Shoes", "Sandals", "Sneakers", "Formal", "Boots"};
            default:
                return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wardrobe_list, container, false);

        // Viewmodel stuff
        vm = ViewModelProviders.of(this).get(WardrobeViewModel.class);

        ImageButton addTorso = (ImageButton) view.findViewById(R.id.addTorso);
        ImageButton addLegs = (ImageButton)  view.findViewById(R.id.addBottom);
        ImageButton addFeet = (ImageButton)  view.findViewById(R.id.addShoes);

        CheckBox showWashing = (CheckBox) view.findViewById(R.id.showWashing);
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

        vm.getLists(showWashing.isChecked());

        // Check if checkbox for showing items in washingMachine is checked off.
        showWashing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vm.getLists(isChecked);
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
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                                clothing.type = types[which];
                                // Navigate to the editscreen and pass the clothing objects
                                Fragment fragment = EditScreen.newInstance(clothing, true);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.wardrobefragment, fragment).commit();
                            }
                        }).show();
    }

    public void setLists(View view, List<Clothing> list, CustomGridView listView) {
        list.sort((c1, c2) -> c1.type.compareTo(c2.type));
        WardrobeAdapter adapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, list);
        listView.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
