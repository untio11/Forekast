
package com.example.forekast.Wardrobe;

import android.os.AsyncTask;
import android.preference.PreferenceManager;

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

    private static boolean washingState = true;
    // Has to be true in initialization, since it will only create more problems in acceptance test
    // if it is false: Items in washing machine will not be opened and there will not be checked
    // if the washing-time has been passed.

    public void getLists(boolean washingMachine, String owner) {
        washingState = washingMachine;
        new AgentAsyncTask("Torso", owner).execute(torsoList);
        new AgentAsyncTask("Legs", owner).execute(legsList);
        new AgentAsyncTask("Feet", owner).execute(feetList);
    }

    public boolean getWashing() {
        return washingState;
    }

    public void setWashing(boolean new_washing) {
        washingState = new_washing;
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
        private String owner;
        private MutableLiveData<List<Clothing>> clothingList;

        AgentAsyncTask(String location, String owner) {
            this.location = location;
            this.owner = owner;
        }

        @Override
        protected Void doInBackground(MutableLiveData<List<Clothing>> ... lists) {
            ClothingCriteria criteria = new ClothingCriteria();
            criteria.owner = owner;
            criteria.washingMachine = washingState;

            clothingList = lists[0];
            clothingList.postValue(Repository.getClothing(location, criteria));
            return null;
        }
    }
}
