package com.example.forekast.Wardrobe;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.forekast.Clothing.Clothing;
import com.example.forekast.Clothing.ClothingCriteria;
import com.example.forekast.ExternalData.Repository;

import java.util.List;

class WardrobeViewModel extends ViewModel {

    private static boolean washingState = true;
    private final MutableLiveData<List<Clothing>> torsoList = new MutableLiveData<>();
    private final MutableLiveData<List<Clothing>> legsList = new MutableLiveData<>();
    private final MutableLiveData<List<Clothing>> feetList = new MutableLiveData<>();

    /**
     * Get all lists from repo belonging to the set owner
     * @param owner String, set owner
     */
    void getLists(String owner) {
        new AgentAsyncTask("Torso", owner).execute(torsoList);
        new AgentAsyncTask("Legs", owner).execute(legsList);
        new AgentAsyncTask("Feet", owner).execute(feetList);
    }

    /**
     * get washingState
     * @return boolean of whether or not we wanted to show items in washingmachine
     */
    boolean getWashing() {
        return washingState;
    }

    /**
     * set washingState
     * @param new_washing boolean of whether or not we want to show items in washingmachine
     */
    void setWashing(boolean new_washing) {
        washingState = new_washing;
    }

    LiveData<List<Clothing>> getTorsoList() {
        return torsoList;
    }

    LiveData<List<Clothing>> getLegsList() {
        return legsList;
    }

    LiveData<List<Clothing>> getFeetList() {
        return feetList;
    }

    /**
     * Execute in background: collecting all clothing objects in lists from repository.
     */
    private static class AgentAsyncTask extends AsyncTask<MutableLiveData<List<Clothing>>, Void, Void> {
        private final String location;
        private final String owner;
        private MutableLiveData<List<Clothing>> clothingList;

        AgentAsyncTask(String location, String owner) {
            this.location = location;
            this.owner = owner;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(MutableLiveData<List<Clothing>>... lists) {
            ClothingCriteria criteria = new ClothingCriteria();
            criteria.owner = owner;
            criteria.washingMachine = washingState;

            clothingList = lists[0];
            clothingList.postValue(Repository.getClothing(location, criteria));
            return null;
        }
    }
}
