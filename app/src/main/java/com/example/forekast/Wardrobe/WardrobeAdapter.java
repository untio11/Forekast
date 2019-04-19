package com.example.forekast.Wardrobe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.forekast.EditScreen.EditScreen;
import com.example.forekast.R;
import com.example.forekast.Clothing.Clothing;
import com.example.forekast.ExternalData.Repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class WardrobeAdapter extends ArrayAdapter<Clothing> {

    private final Context context;
    private final List<Clothing> myList;
    private final FragmentManager fragmentManager;

    public WardrobeAdapter(Context context, int resource, List<Clothing> objects) {
        super(context, resource, objects);

        this.context = context;
        this.myList = objects;
        fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        if (myList != null)
            return myList.size();
        return 0;
    }

    @Override
    public Clothing getItem(int position) {
        if (myList != null)
            return myList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (myList != null)
            return myList.get(position).hashCode();
        return 0;

    }


    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {

        final Holder holder;
        Bitmap bitmap;

        //If the listview does not have an xml layout ready set the layout
        if (convertView == null) {

            //we need a new holder to hold the structure of the cell
            holder = new Holder();

            //get the XML inflation service
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate our xml cell to the convertView
            convertView = inflater.inflate(R.layout.wardrobe_entry, null);

            //Get xml components into our holder class
            holder.imageView = convertView.findViewById(R.id.clothingimage);

            //Attach our holder class to this particular cell
            convertView.setTag(holder);

        } else {

            //The listview cell is not empty and contains already components loaded, get the tagged holder
            holder = (Holder) convertView.getTag();

        }

        //Fill our cell with data

        //get our clothing object from the list we passed to the adapter
        Clothing clothing = getItem(position);

        //Fill our view components with data
        if (clothing != null && clothing.picture != null) {
            bitmap = BitmapFactory.decodeByteArray(clothing.picture, 0, clothing.picture.length);
            holder.imageView.setImageBitmap(bitmap);
        }

        // Check if the time in washingmachine has passed for this item
        if (clothing != null && clothing.washing_machine) {
            if (clothing.washing_time != 0) {
                if ((System.currentTimeMillis() / 1000) - clothing.washing_time > 10) {
                    // change the above to / (1000*60*60*24) for the amount of days
                    // 10 seconds have passed since this item was in the washing machine,
                    // make it available again
                    clothing.washing_machine = false;
                    clothing.washing_time = 0;
                    // update to the database
                    new AgentAsyncTask(clothing).execute();
                }
            } else {
                throw new IllegalArgumentException("Something is wrong with a clothing's washing time");
            }
        }

        //Clothing is in washing machine, so set faded out picture
        if (clothing != null && clothing.washing_machine) {
            holder.imageView.setAlpha(0.5f);
        }

        holder.imageView.setOnClickListener(v -> {
            // Clicking on item will navigate to editscreen with the current clothing item
            Fragment fragment = EditScreen.newInstance(clothing, false);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.wardrobe_container, fragment).addToBackStack("edit").commit();
        });

        return convertView;
    }

    private static class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {
        final Clothing clothing;

        AgentAsyncTask(Clothing clothing) {
            // Get the clothing object
            this.clothing = clothing;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            // Update clothing to the database
            Repository.updateClothing(clothing);
            return null;
        }
    }

    /**
     * This holder must replicate the components in the wardrobe_entry
     * We have an imageview for the picture and a progressBar to show progress
     */
    private class Holder {
        ImageView imageView;
    }
}
