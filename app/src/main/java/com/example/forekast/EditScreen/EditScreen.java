package com.example.forekast.EditScreen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import androidx.appcompat.app.AlertDialog;
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
    private static String[] items;
    private static boolean preWashingState;
    private static String preType;

    public static EditScreen newInstance(Clothing clothing, Boolean add) {
        addBool = add;
        if (addBool) {
            clothing.preSet();
        }
        preType = clothing.type;
        preWashingState = clothing.washing_machine;
        editClothing = clothing;
        items = WardrobeFragment.getTypes(editClothing.location);
        bitmap = null;
        return new EditScreen();
    }

    private void setWashingTime() {
        if (editClothing.washing_machine && !preWashingState) {
            // If not in washingmachine before, but it is now: set time to current time
            editClothing.washing_time = System.currentTimeMillis()/1000; // seconds,
            // change the above to / (1000*60*60*24) for the amount of days
        } else if (!editClothing.washing_machine && preWashingState) {
            // If in washingmachine before, but not anymore: set time to 0
            editClothing.washing_time = 0;
        }
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

        // Get the different seek bars to regulate warmth, comfort, formality and preference
        SeekBar seekWarmth = (SeekBar) view.findViewById(R.id.seekWarmth);
        SeekBar seekComfort = (SeekBar) view.findViewById(R.id.seekComfort);
        SeekBar seekFormality = (SeekBar) view.findViewById(R.id.seekFormality);
        SeekBar seekPreference = (SeekBar) view.findViewById(R.id.seekPreference);

        // Get the checkbox
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        if (addBool && savedInstanceState == null) {
            selectPicture();
        } else if (editClothing.picture != null) {
            bitmap = BitmapFactory.decodeByteArray(editClothing.picture, 0, editClothing.picture.length);
            imageView.setImageBitmap(bitmap);
        }

        seekWarmth.setMax(10); // Ensure this is 10
        seekWarmth.setProgress(editClothing.warmth);

        seekComfort.setMax(10); // Ensure this is 10
        seekComfort.setProgress(editClothing.comfort);

        seekFormality.setMax(10); // Ensure this is 10
        seekFormality.setProgress(editClothing.formality);

        seekPreference.setMax(10); // Ensure this is 10
        seekPreference.setProgress(editClothing.preference);

        checkBox.setChecked(editClothing.washing_machine);

        spinner.setSelection(adapter.getPosition(editClothing.type));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Give a prompt to make sure deletion is intended
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Show some text that deletion was successful
                                Toast.makeText(getContext(), "Item was deleted", Toast.LENGTH_SHORT).show();
                                // Perform the actual deleting in the database
                                new AgentAsyncTaskDelete().execute();
                                // Navigate to the wardrobe
                                navigateWardrobe();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save attributes to the clothing object
                editClothing.warmth = seekWarmth.getProgress();
                editClothing.formality = seekFormality.getProgress();
                editClothing.comfort = seekComfort.getProgress();
                editClothing.preference = seekPreference.getProgress();
                editClothing.type = (String) spinner.getSelectedItem();
                // If the clothing type for Torso was changed, change the wearable attributes:
                if (editClothing.location.equals("Torso") && !editClothing.type.equals(preType)) {
                    editClothing.setWearable();
                }
                editClothing.owner = "General"; // GET THIS VARIABLE FROM SETTINGS!
                editClothing.washing_machine = checkBox.isChecked();
                setWashingTime();
                // save the picture if there is one
                if (bitmap != null) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 1, bos);
                    byte[] img = bos.toByteArray();
                    editClothing.picture = img;
                }
                // Perform the saving to the database
                new AgentAsyncTask().execute();

                // Navigate to the wardrobe
                navigateWardrobe();

            }
        });

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the wardrobe
                navigateWardrobe();
            }
        });

        redoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        return view;
    }

    private void selectPicture() {
        // Let the user decide what to access: Gallery or camera?
        new AlertDialog.Builder(getContext())
                .setTitle("Add a picture")
                .setMessage("Do you want to access the gallery or make a new picture?")
                .setIcon(android.R.drawable.ic_menu_camera)
                .setNeutralButton(android.R.string.no, null)
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    // Open gallery
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(getActivity(),"Please give gallery permission in your phone's settings.",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    // Open camera
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 0);
                        } else {
                            Toast.makeText(getActivity(),"Please give camera permission in your phone's settings.",Toast.LENGTH_LONG).show();
                        }
                    }
                }).show();
    }

    private void navigateWardrobe() {
        // Navigate to the wardrobe
        Fragment fragment = WardrobeFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.wardrobefragment, fragment).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        //Toast.makeText(getActivity(), items[position], Toast.LENGTH_LONG).show();
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
        if (requestCode == 0) {
            if (data.getExtras() != null) {
                // Read image from camera into bitmap
                bitmap = (Bitmap) data.getExtras().get("data");
                // Set image
                imageView.setImageBitmap(bitmap);
                mViewModel.setBitmap(bitmap);
            }
        } else if (requestCode == 1) {
            if (data != null) {
                // Query image from images stored on phone
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                // Decode into bitmap and set image
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                bitmap = BitmapFactory.decodeFile(picturePath, options);
                imageView.setImageBitmap(bitmap);
                mViewModel.setBitmap(bitmap);
            }
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
