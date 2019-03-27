/*
package com.example.forekast.Wardrobe;

import android.os.AsyncTask;
import android.view.View;

import com.example.forekast.R;
import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WardrobeViewModel extends ViewModel {

    private MutableLiveData<List<Clothing>> target;


    private static class AgentAsyncTask extends AsyncTask<MutableLiveData<List<Clothing>>, Void, Void> {
        View view;

        public AgentAsyncTask(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(MutableLiveData<List<Clothing>> ... lists) {
            ClothingCriteria criteria = new ClothingCriteria();

            MutableLiveData<List<Clothing>> torsoList = lists[0];

            torsoList.postValue(Repository.getClothing("Torso", criteria));

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

/*
            WardrobeAdapter torsoAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, torsoList);
            torsoListView.setAdapter(torsoAdapter);

            // Load Legs items from database into wardrobe list
            CustomGridView legsListView = (CustomGridView) view.findViewById(R.id.Bottom);
            List<Clothing> legsList = Repository.getClothing("Legs", new ClothingCriteria());
            WardrobeAdapter legsAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, legsList);
            legsListView.setAdapter(legsAdapter);

            // Load Feet items from database into wardrobe list
            CustomGridView feetListView = (CustomGridView) view.findViewById(R.id.Shoes);
            List<Clothing> feetList = Repository.getClothing("Feet", new ClothingCriteria());
            WardrobeAdapter feetAdapter = new WardrobeAdapter(view.getContext(), R.layout.fragment_wardrobe, feetList);
            feetListView.setAdapter(feetAdapter);

            return null;
        }
    }
}
*/