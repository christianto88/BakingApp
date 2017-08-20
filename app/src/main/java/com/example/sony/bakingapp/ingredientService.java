package com.example.sony.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by SONY on 8/20/2017.
 */

public class ingredientService extends IntentService {
    public ingredientService() {
        super("ingredientService");
    }

    public static final String ACTION_UPDATE_WIDGET = "com.example.android.bakingapp.action.update.widget";

//    public static void startBakingService(Context context,ArrayList<Ingredients> ingredientsList) {
//        Intent intent = new Intent(context, ingredientService.class);
//        intent.putParcelableArrayListExtra("ingredientList",ingredientsList);
//        context.startService(intent);
//    }
    public static void startBakingService(Context context,String t) {
        Intent intent = new Intent(context, ingredientService.class);
        intent.putExtra("pesan",t);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
//            ArrayList<Ingredients> myingredientsList = intent.getExtras().getParcelableArrayList("ingredientList");
            String teks=intent.getStringExtra("pesan");
            handleActionUpdateBakingWidgets(teks);

        }
    }



//    private void handleActionUpdateBakingWidgets(ArrayList<Ingredients> ingredientsList) {
//        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
//        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
//        intent.putParcelableArrayListExtra("ingredientList",ingredientsList);
//        sendBroadcast(intent);
//    }
private void handleActionUpdateBakingWidgets(String x) {
    Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
    intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
    intent.putExtra("msg",x);
    sendBroadcast(intent);
}
}
