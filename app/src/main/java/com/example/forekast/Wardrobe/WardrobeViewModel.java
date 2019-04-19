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
    // Has to be true in initialization, since it will only create more problems in acceptance test
    // if it is false: Items in washing machine will not be opened and there will not be checked
    // if the washing-time has been passed.

    void getLists(String owner) {
        new AgentAsyncTask("Torso", owner).execute(torsoList);
        new AgentAsyncTask("Legs", owner).execute(legsList);
        new AgentAsyncTask("Feet", owner).execute(feetList);
    }

    boolean getWashing() {
        return washingState;
    }

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
