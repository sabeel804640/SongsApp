package app.sabeeldev.mysongs.GeneralClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import app.sabeeldev.mysongs.Model.PlayList;
import app.sabeeldev.mysongs.Model.SongsMaster;
import app.sabeeldev.mysongs.Model.Video;
import app.sabeeldev.mysongs.R;
import app.sabeeldev.mysongs.RoomDatabase.DbViewmModel;
import app.sabeeldev.mysongs.RoomDatabase.Favourite;
import app.sabeeldev.mysongs.RoomDatabase.Recent;

public class Global {
    public static boolean back_status = false;
    public static ArrayList<PlayList.Songs> mySongslists = new ArrayList<>();
    public static ArrayList<String> playList = new ArrayList<>();
    public static ArrayList<SongsMaster> sortedList = new ArrayList<>();
    public static List<Favourite> favList = new ArrayList<>();
    public static List<Recent> recentList = new ArrayList<>();
    public static ArrayList<Video.AllFormats> videosFormats = new ArrayList<>();
    public static String TEST_URL_MP4 = "";
    public static String TEST_URL_MP3 = "";
    public static String duration = "";
    public static String videoTitle = "";
    public static String playListSelected = "";
    public static String imgURL = "";
    public static KProgressHUD mKProgressHUD;
    public static void changeFragmentMain(Context context, Fragment fragment, String device_back_tag, boolean status) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fadein,
                R.anim.fadeout);
        transaction.replace(R.id.main_container, fragment);
        if (status) {
            transaction.addToBackStack(device_back_tag);
        }
        back_status = status;
        transaction.commit();
    }

    public static void changeActivity(Context context, Activity activity) {
        Intent in = new Intent();
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.setClass(context, activity.getClass());
        context.startActivity(in);
    }
}
