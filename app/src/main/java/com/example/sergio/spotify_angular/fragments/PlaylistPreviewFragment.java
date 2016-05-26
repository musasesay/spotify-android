package com.example.sergio.spotify_angular.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.spotify_angular.R;
import com.poliveira.parallaxrecyclerview.HeaderLayoutManagerFixed;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.PlaylistSimple;

/**
 * Created by sergio on 21/05/2016.
 */
public class PlaylistPreviewFragment extends Fragment {

    public final static String PLAYLIST_PARAM = "playlist";

    protected RecyclerView recyclerPlaylistTracks;
    protected PlaylistSimple playlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) playlist = arguments.getParcelable(PLAYLIST_PARAM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.playlist_preview_fragment,container,false);
        View header = inflater.inflate(R.layout.playlist_tracks_header, recyclerPlaylistTracks, false);
        ImageView image = (ImageView) header.findViewById(R.id.playlist_image);
        Picasso.with(getActivity()).load(playlist.images.get(0).url).into(image);

        recyclerPlaylistTracks = (RecyclerView)view.findViewById(R.id.playlist_tracks);

        final List<String> content = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            content.add("item " + i);
        }

        final ParallaxRecyclerAdapter<String> adapter = new ParallaxRecyclerAdapter<String>(content) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<String> adapter, int i) {
                TrackViewHolder trackViewHolder = (TrackViewHolder)viewHolder;
                trackViewHolder.setTitle(content.get(i));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final ParallaxRecyclerAdapter<String> adapter, int i) {
                return new TrackViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.playlist_track_layout, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<String> adapter) {
                return content.size();
            }
        };

        adapter.setOnClickEvent(new ParallaxRecyclerAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {
                Toast.makeText(getActivity(), "You clicked '" + position + "'", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerPlaylistTracks.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setParallaxHeader(header, recyclerPlaylistTracks);
        recyclerPlaylistTracks.setAdapter(adapter);

        return view;
    }



    static class TrackViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public TrackViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

        public String getTitle() {
            return title.toString();
        }
    }
}