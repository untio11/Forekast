package com.example.forekast;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.forekast.clothing.Clothing;

import java.util.ArrayList;
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
        ImageButton addBottom = (ImageButton) view.findViewById(R.id.addBottom);
        ImageButton addShoes = (ImageButton) view.findViewById(R.id.addShoes);

        addTorso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clothing torso = new Clothing("torso"); // CHANGE TO TORSO
                Fragment fragment = EditScreen.newInstance(torso);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        addBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clothing bottom = new Clothing("bottom"); // CHANGE TO BOTTOM
                Fragment fragment = EditScreen.newInstance(bottom);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        addShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clothing shoes = new Clothing("shoes"); // CHANGE TO SHOES (SUPER)
                Fragment fragment = EditScreen.newInstance(shoes);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        // Torso list
        CustomGridView torsoListView = (CustomGridView)view.findViewById(R.id.Torso);
        List<Clothing> torsoList = new ArrayList<>();

        Clothing torso1 = new Clothing();
        Clothing torso2 = new Clothing();

        //clothing.setImageUrl("IMG_20190323_174603.jpg");
        //clothing2.setImageUrl("IMG_20190215_084658.jpg");
        torso1.setImageUrl("https://www.hekonvalentijn.nl/Portals/0/Entity/109/Images/Wine.jpg");
        torso2.setImageUrl("https://www.tipdebruin.nl/media/catalog/product/cache/3eefb03207b57b648a3f7359289ae856/s/t/stone-island-sweater-701562751-wit-00043036-1.jpg.jpg");
        torsoList.add(torso1);
        torsoList.add(torso2);
        WardrobeAdapter torsoAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, torsoList);

        torsoListView.setAdapter(torsoAdapter);

        // Bottom list
        CustomGridView bottomListView = (CustomGridView)view.findViewById(R.id.Bottom);
        List<Clothing> bottomList = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Clothing bottom = new Clothing();

            bottom.setImageUrl("https://purepng.com/public/uploads/large/purepng.com-ladies-jeansgarmentlower-bodydenimjeansladies-1421526363383tdgn0.png");

            bottomList.add(bottom);
        }
        WardrobeAdapter bottomAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, bottomList);

        bottomListView.setAdapter(bottomAdapter);

        // Shoe list
        CustomGridView shoeListView = (CustomGridView)view.findViewById(R.id.Shoes);
        List<Clothing> shoeList = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            Clothing shoe = new Clothing();

            shoe.setImageUrl("https://images.vans.com/is/image/Vans/D3HBKA-HERO?$583x583$");

            shoeList.add(shoe);
        }

        WardrobeAdapter shoeAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, shoeList);

        shoeListView.setAdapter(shoeAdapter);

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
}
