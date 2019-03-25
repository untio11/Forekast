package com.example.forekast;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class WardrobeAdapter extends ArrayAdapter<Clothing> {

    Context context;
    List<Clothing> myList;
    FragmentManager fragmentManager;

    public WardrobeAdapter(Context context, int resource, List<Clothing> objects) {
        super(context, resource, objects);

        this.context = context;
        this.myList = objects;
        fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
    }


    @Override
    public int getCount() {
        if(myList != null)
            return myList.size();
        return 0;
    }

    @Override
    public Clothing getItem(int position) {
        if(myList != null)
            return myList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(myList != null)
            return myList.get(position).hashCode();
        return 0;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder;

        //If the listview does not have an xml layout ready set the layout
        if (convertView == null) {

            //we need a new holder to hold the structure of the cell
            holder = new Holder();

            //get the XML inflation service
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Inflate our xml cell to the convertView
            convertView = inflater.inflate(R.layout.fragment_wardrobe, null);

            //Get xml components into our holder class
            holder.imageView = (ImageView) convertView.findViewById(R.id.clothingimage);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

            //Attach our holder class to this particular cell
            convertView.setTag(holder);

        } else {

            //The listview cell is not empty and contains already components loaded, get the tagged holder
            holder = (Holder) convertView.getTag();

        }

        //Fill our cell with data

        //get our clothing object from the list we passed to the adapter
        final Clothing clothing = getItem(position);

        //Fill our view components with data
        if (clothing.picture != null) {
            final String url = clothing.picture;
            //final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), url);
            //if (file != null) {
                Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // When image is loaded, no progressbar
                        if (holder.progressBar != null) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {
                        // When an error has occured, print which one
                        System.out.println("Wardrobe: Error displaying picture: " + url);
                    }
                });
            //} else {
            //    System.out.println("Wardrobe: Error loading file: " + url);
            //}
        } else if (holder.progressBar != null) {
            // If no image was added to a clothing item, no progressbar (show default picture from XML)
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clicking on item will navigate to editscreen with the current clothing item
                Fragment fragment = EditScreen.newInstance(clothing);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.wardrobefragment, fragment).commit();
            }
        });

        return convertView;
    }

    /**
     * This holder must replicate the components in the fragment_wardrobe.xml
     * We have an imageview for the picture and a progressBar to show progress
     */
    private class Holder {
        ProgressBar progressBar;
        ImageView imageView;
    }
}
