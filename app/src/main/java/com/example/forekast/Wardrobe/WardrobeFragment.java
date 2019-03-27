
package com.example.forekast.Wardrobe;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;
    //private WardrobeViewModel viewModel;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wardrobe_list, container, false);

        ImageButton addTorso = (ImageButton) view.findViewById(R.id.addTorso);
        ImageButton addLegs = (ImageButton)  view.findViewById(R.id.addBottom);
        ImageButton addFeet = (ImageButton)  view.findViewById(R.id.addShoes);

        // The add torso button will add a new clothing of type torso
        addTorso.setOnClickListener(v -> {
            Clothing torso = new Torso(); // CHANGE TO TORSO
            // Navigate to edit screen
            Fragment fragment = EditScreen.newInstance(torso);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.wardrobefragment, fragment).commit();
        });

        // The add bottom button will add a new clothing of type bottom
        addLegs.setOnClickListener(v -> {
            Clothing legs = new Legs(); // CHANGE TO BOTTOM
            // Navigate to edit screen
            Fragment fragment = EditScreen.newInstance(legs);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.wardrobefragment, fragment).commit();
        });

        // The add shoes button will add a new clothing of type shoes
        addFeet.setOnClickListener(v -> {
            Clothing feet = new Feet(); // CHANGE TO FEET
            // Navigate to edit screen
            Fragment fragment = EditScreen.newInstance(feet);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.wardrobefragment, fragment).commit();
        });

        // Execute loading of database in background
        new AgentAsyncTask(view).execute();

        // return view
        return view;
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

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {
        View view;

        public AgentAsyncTask(View view) {
            this.view = view;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ClothingCriteria criteria = new ClothingCriteria();
            criteria.owner = "General";


            // Load Torso items from database into wardrobe list
            CustomGridView torsoListView = (CustomGridView) view.findViewById(R.id.Torso);
            List<Clothing> torsoList = Repository.getClothing("Torso", criteria);

            // JUST SOME TESTING... DELETE THIS LATER!!!
            /*Clothing torso1 = new Clothing();
            Clothing torso2 = new Clothing();

            //clothing.setImageUrl("IMG_20190323_174603.jpg");
            //clothing2.setImageUrl("IMG_20190215_084658.jpg");
            torso1.setImageUrl("https://www.hekonvalentijn.nl/Portals/0/Entity/109/Images/Wine.jpg");
            torso2.setImageUrl("https://www.tipdebruin.nl/media/catalog/product/cache/3eefb03207b57b648a3f7359289ae856/s/t/stone-island-sweater-701562751-wit-00043036-1.jpg.jpg");
            torsoList.add(torso1);
            torsoList.add(torso2);
            */



            WardrobeAdapter torsoAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, torsoList);
            torsoListView.setAdapter(torsoAdapter);

            // Load Legs items from database into wardrobe list
            CustomGridView legsListView = (CustomGridView) view.findViewById(R.id.Bottom);
            List<Clothing> legsList = Repository.getClothing("Legs", criteria);
            WardrobeAdapter legsAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, legsList);
            legsListView.setAdapter(legsAdapter);

            // Load Feet items from database into wardrobe list
            CustomGridView feetListView = (CustomGridView) view.findViewById(R.id.Shoes);
            List<Clothing> feetList = Repository.getClothing("Feet", criteria);
            WardrobeAdapter feetAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, feetList);
            feetListView.setAdapter(feetAdapter);

            return null;
        }
    }
}
