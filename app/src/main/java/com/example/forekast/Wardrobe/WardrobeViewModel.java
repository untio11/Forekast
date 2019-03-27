
package com.example.forekast.Wardrobe;

import android.os.AsyncTask;

import com.example.forekast.clothing.Clothing;
import com.example.forekast.clothing.ClothingCriteria;
import com.example.forekast.external_data.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WardrobeViewModel extends ViewModel {

    public MutableLiveData<List<Clothing>> torsoList;
    public MutableLiveData<List<Clothing>> legsList;
    public MutableLiveData<List<Clothing>> feetList;

    public void getLists() {
        //new AgentAsyncTask("Torso").execute(torsoList);
        //new AgentAsyncTask("Legs").execute(legsList);
        //new AgentAsyncTask("Feet").execute(feetList);
    }

    public LiveData<List<Clothing>> getTorsoList() {
        new AgentAsyncTask("Torso").execute(torsoList);
        return torsoList;
    }

    public LiveData<List<Clothing>> getLegsList() {
        new AgentAsyncTask("Legs").execute(legsList);
        return legsList;
    }

    public LiveData<List<Clothing>> getFeetList() {
        new AgentAsyncTask("Feet").execute(feetList);
        return feetList;
    }

    private static class AgentAsyncTask extends AsyncTask<MutableLiveData<List<Clothing>>, Void, Void> {
        private String location;

        AgentAsyncTask(String location) {
            this.location = location;
        }

        @Override
        protected Void doInBackground(MutableLiveData<List<Clothing>> ... lists) {
            ClothingCriteria criteria = new ClothingCriteria();
            criteria.owner = "General";

            MutableLiveData<List<Clothing>> clothingList = lists[0];
            clothingList.postValue(Repository.getClothing(location, criteria));
            return null;
        }
    }
}
