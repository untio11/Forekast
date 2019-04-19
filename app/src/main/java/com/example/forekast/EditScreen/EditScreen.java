package com.example.forekast.EditScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.forekast.R;
import com.example.forekast.Wardrobe.Wardrobe;
import com.example.forekast.Clothing.Clothing;
import com.example.forekast.ExternalData.Repository;

import java.io.ByteArrayOutputStream;
import java.util.Objects;


public class EditScreen extends Fragment implements AdapterView.OnItemSelectedListener {

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
        items = Wardrobe.getTypes(editClothing.location);
        bitmap = null;
        return new EditScreen();
    }

    private void setWashingTime() {
        if (editClothing.washing_machine && !preWashingState) {
            // If not in washingmachine before, but it is now: set time to current time
            editClothing.washing_time = System.currentTimeMillis() / 1000; // seconds,
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

        ImageButton returnbutton = view.findViewById(R.id.returnbutton);
        ImageButton delete = view.findViewById(R.id.delete);
        ImageButton savebutton = view.findViewById(R.id.save);

        //create the camera button, and take into consideration the clothing image
        Button redoImage = view.findViewById(R.id.redoImage);
        imageView = view.findViewById(R.id.imageView10);

        //create clothing type spinner, along with the choice of type, and the adapter it needs with it
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        view.findViewById(R.id.drop_down_butt).setOnClickListener(v -> spinner.performClick());
        ArrayAdapter adapter = new ArrayAdapter(Objects.requireNonNull(getActivity()), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Get the different seek bars to regulate warmth, comfort, formality and preference
        SeekBar seekWarmth = view.findViewById(R.id.seekWarmth);
        SeekBar seekComfort = view.findViewById(R.id.seekComfort);
        SeekBar seekFormality = view.findViewById(R.id.seekFormality);
        SeekBar seekPreference = view.findViewById(R.id.seekPreference);

        // Get the checkbox
        CheckBox checkBox = view.findViewById(R.id.checkBox);

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

        delete.setOnClickListener(v -> {
            // Give a prompt to make sure deletion is intended
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this item?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", (dialog, whichButton) -> {
                        // Show some text that deletion was successful
                        Toast.makeText(getContext(), "Item was deleted", Toast.LENGTH_SHORT).show();
                        // Perform the actual deleting in the database
                        new AgentAsyncTaskDelete().execute();
                        // Navigate to the wardrobe
                        navigateWardrobe();
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });

        savebutton.setOnClickListener(v -> {
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
            editClothing.owner = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("user_list", "general");
            editClothing.washing_machine = checkBox.isChecked();
            setWashingTime();
            // save the picture if there is one
            if (bitmap != null) {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 1, bos);
                editClothing.picture = bos.toByteArray();
            }
            // Perform the saving to the database
            new AgentAsyncTask().execute();

            // Navigate to the wardrobe
            navigateWardrobe();

        });

        returnbutton.setOnClickListener(v -> {
            // Navigate to the wardrobe
            navigateWardrobe();
        });

        redoImage.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            } else {
                Toast.makeText(getActivity(), "Please give camera permission in your phone's settings.", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void selectPicture() {
        // Let the user decide what to access: Gallery or camera?
        // Open camera
// Open gallery
        new AlertDialog.Builder(getContext())
                .setTitle("Add a picture")
                .setMessage("Do you want to access the gallery or make a new picture?")
                .setIcon(android.R.drawable.ic_menu_camera)
                .setNeutralButton(android.R.string.no, null)
                .setNegativeButton("Gallery", (dialog, whichButton) -> {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    } else {
                        Toast.makeText(getActivity(), "Please give gallery permission in your phone's settings.", Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Camera", (dialog, whichButton) -> {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(getActivity(), "Please give camera permission in your phone's settings.", Toast.LENGTH_LONG).show();
                    }
                }).show();
    }

    private void navigateWardrobe() {
        getActivity().onBackPressed();
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

        AgentAsyncTask() {
        }

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

        AgentAsyncTaskDelete() {
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            Repository.removeClothing(editClothing);
            return null;
        }
    }
}
