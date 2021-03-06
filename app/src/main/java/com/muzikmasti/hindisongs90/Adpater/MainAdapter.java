
package com.muzikmasti.hindisongs90.Adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muzikmasti.hindisongs90.Activities.AllActivity;
import com.muzikmasti.hindisongs90.GeneralClasses.Global;
import com.muzikmasti.hindisongs90.GeneralClasses.PreferencesHandler;
import com.muzikmasti.hindisongs90.Model.PlayList;
import com.muzikmasti.hindisongs90.Model.SongsMaster;
import com.muzikmasti.hindisongs90.R;

import java.util.ArrayList;
import java.util.Random;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private RecyclerView.RecycledViewPool recycledViewPool;
    ArrayList<SongsMaster> playList;
    Context context;
    PreferencesHandler preferencesHandler;

    public MainAdapter() {
        playList = new ArrayList<>();
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    public void addMain(SongsMaster songsMaster) {
        playList.add(songsMaster);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        preferencesHandler = new PreferencesHandler(context);
        View v = LayoutInflater.from(context).inflate(R.layout.main_playlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.rvOuter.setRecycledViewPool(recycledViewPool);
        return viewHolder;
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.playlist_title.setText(playList.get(position).getPlayList());
//        holder.rvOuter.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//        //  playListAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return playList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playlist_title, view_all;
        RecyclerView rvOuter;
        PlayListAdapter playListAdapter;
        RelativeLayout main_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlist_title = itemView.findViewById(R.id.playlist_title);
            view_all = itemView.findViewById(R.id.viewAll);
            main_parent = itemView.findViewById(R.id.main_parent);


//            view_all.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("ViewAllChecker", "" + playlist_title.getText().toString());
//                    Intent intent = new Intent(context, AllActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(new Intent(context, AllActivity.class));
//                }
//            });
            rvOuter = itemView.findViewById(R.id.mainPlaylist_recycler);
            setupRv();
        }

        private void setupRv() {
            rvOuter.setHasFixedSize(true);
            rvOuter.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            playListAdapter = new PlayListAdapter();
            rvOuter.setAdapter(playListAdapter);
        }

        public void bind(int position) {
            SongsMaster outer = playList.get(position);
            Random rand = new Random(); //instance of random class
            int upperbound = 25;
            //generate random values from 0-24
            int int_random = rand.nextInt(upperbound);
            ;
            if (outer != null) {
                playlist_title.setText(outer.getPlayList());
                ArrayList<PlayList.Songs> myitems = outer.getMysongs();
                for (int i = 0; i < Global.myRandoms.size(); i++) {
                    if (i == position) {
                        if (myitems.size() > Global.myRandoms.get(i)) {
                            myitems.get(Global.myRandoms.get(i)).setAlbumsort("ads");
                        }
                    }
                }
                playListAdapter.addInner(myitems);

                if (outer.getMysongs().size() < 6) {
                    view_all.setVisibility(View.GONE);
                }
                if (playlist_title.getText().toString().contains("Apps")) {
                    playlist_title.setTextColor(Color.BLACK);
                    main_parent.setBackgroundColor(Color.parseColor("#4DFFA000"));
                } else {
                    main_parent.setBackgroundColor(Color.parseColor("#ffffff"));
                    playlist_title.setTextColor(Color.BLACK);
                }
                view_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Global.playListSelected = playlist_title.getText().toString();
                        Log.d("ViewAllChecker", "" + playlist_title.getText().toString());
                        Intent intent = new Intent(context, AllActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(new Intent(context, AllActivity.class));
                    }
                });
            }
        }

    }
}

