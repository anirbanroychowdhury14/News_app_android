package com.haerul.popularnews;

import android.app.Application;
import io.realm.Realm;

public class MynewsRealmApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
