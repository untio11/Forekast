package com.example.forekast.EditScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.example.forekast.external_data.Repository;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


public class EditScreen extends Fragment implements AdapterView.OnItemSelectedListener{

    private static Clothing editClothing;

    private static EditScreenViewModel mViewModel = new EditScreenViewModel();

    private static Bitmap bitmap;
    private static ImageView imageView;
    private static boolean addBool;
    private static String items[];
    private static boolean preWashingState;

    public static EditScreen newInstance(Clothing clothing, Boolean add) {
        editClothing = clothing;
        addBool = add;
        switch (clothing.location) {
            case "Torso":
                items = new String[]{"T-Shirt", "Dress", "Jacket", "Shirt", "Sweater", "Tanktop"};
                break;
            case "Legs":
                items = new String[]{"Jeans", "Shorts", "Skirt", "Trousers"};
                break;
            case "Feet":
                items = new String[]{"Shoes", "Sandals", "Sneakers"};
                break;
            default:
                break;
        }
        return new EditScreen();
    }

    private boolean decideWashingState() {
        return false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_screen_fragment, container, false);
        ImageButton returnbutton = (ImageButton) view.findViewById(R.id.returnbutton);
        ImageButton delete = (ImageButton) view.findViewById(R.id.delete);
        ImageButton savebutton = (ImageButton) view.findViewById(R.id.save);

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
            imageView.setImageBitmap(bitmap);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AgentAsyncTaskDelete().execute();
                Fragment fragment = WardrobeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editClothing.warmth = seekWarmth.getProgress();
                editClothing.formality = seekFormality.getProgress();
                editClothing.comfort = seekComfort.getProgress();
                editClothing.type = (String) spinner.getSelectedItem();
                editClothing.owner = "General";
                editClothing.washing_machine = checkBox.isChecked();
                if (bitmap != null) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 1, bos);
                    byte[] img = bos.toByteArray();
                    editClothing.picture = img;
                }


                new AgentAsyncTask().execute();

                Fragment fragment = WardrobeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();

            }
        });

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = WardrobeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        redoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                } else {
                    Toast.makeText(getActivity(),"Please give permission for the camera.",Toast.LENGTH_LONG).show();
                }
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
        if (savedInstanceState != null && mViewModel.getBitmap() != null) {
            bitmap = mViewModel.getBitmap();
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getExtras() != null) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            mViewModel.setBitmap(bitmap);
        }
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

    private static class AgentAsyncTaskDelete extends AsyncTask<Void, Void, Integer> {

        public AgentAsyncTaskDelete() {}

        @Override
        protected Integer doInBackground(Void... voids) {
            Repository.removeClothing(editClothing);
            return null;
        }
    }
}
