package com.example.forekast.EditScreen;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.forekast.R;
import com.example.forekast.Wardrobe.WardrobeFragment;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.clothing.Jeans;
import com.example.forekast.external_data.Repository;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import static androidx.room.RoomMasterTable.TABLE_NAME;

public class EditScreen extends Fragment implements AdapterView.OnItemSelectedListener{

    /*
    static String owner;
    static int warmth;
    static int formality;
    static int comfort;
    static int preference;
    static int[] color;
    static boolean washing_machine;
    static int washing_time;
    static String picture;
    */
    static Clothing editClothing;

    private EditScreenViewModel mViewModel;

    Bitmap bitmap;
    static Boolean addBool;

    public static EditScreen newInstance(Clothing clothing, Boolean add) {
        /*
        owner = clothing.owner;
        warmth = clothing.warmth;
        formality = clothing.formality;
        comfort = clothing.comfort;
        preference = clothing.preference;
        color = clothing.color;
        washing_machine = clothing.washing_machine;
        washing_time = clothing.washing_time;
        picture = clothing.picture;
        */
        editClothing = clothing;
        addBool = add;
        return new EditScreen();
    }

    String items[] = {"Dress", "Jacket", "Jeans", "Sandals", "Shirt", "Shoes",
            "Shorts", "Skirt", "Sneakers", "Sweater", "Tanktop", "Trousers", "T-shirt"};
    public static EditScreen newInstance() {
        return new EditScreen();
    }
    ImageView imageView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_screen_fragment, container, false);
        ImageButton returnbutton = (ImageButton) view.findViewById(R.id.returnbutton);
        ImageButton delete = (ImageButton) view.findViewById(R.id.delete);

        //create the camera button, and take into consideration the clothing image
        Button redoImage = (Button) view.findViewById(R.id.redoImage);
        imageView = (ImageView) view.findViewById(R.id.imageView10);

        //create clothing type spinner, along with the choice of type, and the adapter it needs with it
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //implement the different seek bars to regulate warmth, comfort and formality
        SeekBar seekWarmth = (SeekBar) view.findViewById(R.id.seekWarmth);
        SeekBar seekComfort = (SeekBar) view.findViewById(R.id.seekComfort);
        SeekBar seekFormality = (SeekBar) view.findViewById(R.id.seekFormality);
        //seekFormality: listen to what changes
        //seekComfort: listen to what changes
        //seekWarmth: listen to what changes

        // Implement the checkbox
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);


        seekWarmth.setMax(10);
        seekWarmth.setProgress(editClothing.warmth);

        seekComfort.setMax(10);
        seekComfort.setProgress(editClothing.comfort);

        seekFormality.setMax(10);
        seekFormality.setProgress(editClothing.formality);

        checkBox.setChecked(editClothing.washing_machine);

        spinner.setSelection(adapter.getPosition(editClothing.type));

        if (editClothing.picture != null) {
            bitmap = BitmapFactory.decodeByteArray(editClothing.picture, 0, editClothing.picture.length);
            System.out.println(bitmap);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.removeClothing(editClothing);
                Fragment fragment = WardrobeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editClothing.warmth = seekWarmth.getProgress();
                editClothing.formality = seekFormality.getProgress();
                editClothing.comfort = seekComfort.getProgress();
                editClothing.type = (String) spinner.getSelectedItem();
                editClothing.owner = "General";
                editClothing.washing_machine = checkBox.isSelected();
                //editClothing.picture = bitmap;
                //System.out.println(bitmap);
                //ByteArrayOutputStream blob = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
                //byte[] bitmapdata = blob.toByteArray();
                //editClothing.picture = bitmapdata;


                new AgentAsyncTask().execute();

                Fragment fragment = WardrobeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();

            }
        });

        redoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getActivity(), items[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditScreenViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {

        public AgentAsyncTask() {}

        @Override
        protected Integer doInBackground(Void... voids) {
            if (addBool) {
                Repository.addClothing(editClothing);
            } else {
                Repository.updateClothing(editClothing);
            }
            return null;
        }
    }
}
