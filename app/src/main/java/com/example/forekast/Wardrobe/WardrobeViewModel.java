
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

    private MutableLiveData<List<Clothing>> torsoList = new MutableLiveData<>();
    private MutableLiveData<List<Clothing>> legsList = new MutableLiveData<>();
    private MutableLiveData<List<Clothing>> feetList = new MutableLiveData<>();

    public void getLists() {
        new AgentAsyncTask("Torso").execute(torsoList);
        new AgentAsyncTask("Legs").execute(legsList);
        new AgentAsyncTask("Feet").execute(feetList);
    }

    public LiveData<List<Clothing>> getTorsoList() {
        return torsoList;
    }

    public LiveData<List<Clothing>> getLegsList() {
        return legsList;
    }

    public LiveData<List<Clothing>> getFeetList() {
        return feetList;
    }

    private static class AgentAsyncTask extends AsyncTask<MutableLiveData<List<Clothing>>, Void, Void> {
        private String location;
        private MutableLiveData<List<Clothing>> clothingList;

        AgentAsyncTask(String location) {
            this.location = location;
        }

        @Override
        protected Void doInBackground(MutableLiveData<List<Clothing>> ... lists) {
            ClothingCriteria criteria = new ClothingCriteria();
            criteria.owner = "General";

            clothingList = lists[0];
            clothingList.postValue(Repository.getClothing(location, criteria));
            return null;
        }
    }
}
