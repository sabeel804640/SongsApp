package app.sabeeldev.mysongs.Adpater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.sabeeldev.mysongs.Activities.MainActivity;
import app.sabeeldev.mysongs.Activities.Player;
import app.sabeeldev.mysongs.GeneralClasses.Global;
import app.sabeeldev.mysongs.Model.PlayList;
import app.sabeeldev.mysongs.R;
import app.sabeeldev.mysongs.RetrofitUtils.PostWebAPIData;
import app.sabeeldev.mysongs.RoomDatabase.Favourite;
import app.sabeeldev.mysongs.RoomDatabase.Recent;

public class AllPlayListAdapter extends RecyclerView.Adapter<AllPlayListAdapter.ViewHolder> {
    Context context;
    List<PlayList.Songs> newsongsPlayList;
    PostWebAPIData postWebAPIData;

    public AllPlayListAdapter() {
        postWebAPIData = new PostWebAPIData();
        newsongsPlayList = new ArrayList<>();
    }

    public void addAllItems(List<PlayList.Songs> inners) {
        newsongsPlayList.clear();
        newsongsPlayList = inners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllPlayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.all_playlist_items, parent, false);
        return new AllPlayListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllPlayListAdapter.ViewHolder holder, int position) {
        Picasso.get().load(newsongsPlayList.get(position).getImage()).into(holder.song_img);
        holder.song_title.setText(newsongsPlayList.get(position).getTitle());
        holder.song_album.setText(newsongsPlayList.get(position).getAlbumName());
        holder.fav.setImageResource(R.drawable.ic_fav_unselect);

        for (int i = 0; i < Global.favList.size(); i++) {
            if (newsongsPlayList.get(position).getTitle().equals(Global.favList.get(i).getTitle())) {
                Log.d("MyfavouriteItem", "" + holder.song_title.getText().toString());
                holder.fav.setImageResource(R.drawable.ic_fav_selected);
            }
        }

        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favourite addfav = new Favourite("" + newsongsPlayList.get(position).getAlbumID(), "" + newsongsPlayList.get(position).getAlbumName(), "" + newsongsPlayList.get(position).getAlbumsort(),
                        "" + newsongsPlayList.get(position).getSongID(), "" + newsongsPlayList.get(position).getTitle(), "" + newsongsPlayList.get(position).getWebview(),
                        "" + newsongsPlayList.get(position).getIsRedirection(), "" + newsongsPlayList.get(position).getRedirectionApp(), "" + newsongsPlayList.get(position).getImage(),
                        "" + newsongsPlayList.get(position).getYoutubecode(), "" + newsongsPlayList.get(position).getSongSortorder());

                boolean check = false;
                int index = 0;
                for (int i = 0; i < Global.favList.size(); i++) {
                    if (Global.favList.get(i).getTitle().equals(addfav.getTitle())) {
                        check = true;
                        index = i;
                        break;
                    }
                }
                if (!check) {
                    holder.fav.setImageResource(R.drawable.ic_fav_selected);
                    MainActivity.viewModel.insertFav(addfav);
                    Global.favList.add(addfav);
                } else {
                    MainActivity.viewModel.deleteSingleFav(newsongsPlayList.get(position).getTitle());
                    holder.fav.setImageResource(R.drawable.ic_fav_unselect);
                    Global.favList.remove(index);
                }
            }
        });

        holder.playlist_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.imgURL = newsongsPlayList.get(position).getImage();
                Global.playListSelected = holder.song_album.getText().toString();
                Global.videoTitle = "";
                Global.duration="";
                Recent recent = new Recent("" + newsongsPlayList.get(position).getAlbumID(), "" + newsongsPlayList.get(position).getAlbumName(), "" + newsongsPlayList.get(position).getAlbumsort(),
                        "" + newsongsPlayList.get(position).getSongID(), "" + newsongsPlayList.get(position).getTitle(), "" + newsongsPlayList.get(position).getWebview(),
                        "" + newsongsPlayList.get(position).getIsRedirection(), "" + newsongsPlayList.get(position).getRedirectionApp(), "" + newsongsPlayList.get(position).getImage(),
                        "" + newsongsPlayList.get(position).getYoutubecode(), "" + newsongsPlayList.get(position).getSongSortorder());
                boolean check = false;
                int index = 0;
                for (int i = 0; i < Global.recentList.size(); i++) {
                    if (Global.recentList.get(i).getTitle().equals(recent.getTitle())) {
                        check = true;
                        index = i;
                        break;
                    }
                }
                if (!check) {
                    MainActivity.viewModel.insertRecent(recent);
                    Global.recentList.add(recent);
                }

                postWebAPIData.GetVideoData(newsongsPlayList.get(position).getYoutubecode());
                Global.changeActivity(context, new Player());
            }
        });
    }


    @Override
    public int getItemCount() {
        return newsongsPlayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView song_img, fav;
        TextView song_title, song_album;
        RelativeLayout playlist_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            song_img = itemView.findViewById(R.id.playlistSong_img);
            song_title = itemView.findViewById(R.id.playlistSong_title);
            song_album = itemView.findViewById(R.id.playlistSong_album);
            playlist_card = itemView.findViewById(R.id.playlist_card);
            fav = itemView.findViewById(R.id.playlistSong_fav);
        }
    }
}

