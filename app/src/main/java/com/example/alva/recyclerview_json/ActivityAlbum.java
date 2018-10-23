package com.example.alva.recyclerview_json;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ActivityAlbum extends AppCompatActivity implements AdaptadorAlbum.ClickListener {
    public static final String KEY_COVER_IMAGEN = "imagen_cover";
    public static final String KEY_URL_ALBUM = "url_album";

    private AdaptadorAlbum adaptadorAlbum;
    private RecyclerView recyclerViewAlbum;
    private ArrayList<Track> tracks;
    private RequestQueue miRequestQueueTrack;
    private String albumURL;
    private Bundle bundle;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private TextView textViewTrack;
    private TextView textViewNombre;
    private ImageView imageViewPlay;
    private ImageView imageViewPause;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        bundle = intent.getExtras();



        albumURL = bundle.getString(KEY_URL_ALBUM);

        recyclerViewAlbum = findViewById(R.id.recycler_view_album);
        recyclerViewAlbum.setHasFixedSize(true);
        recyclerViewAlbum.setLayoutManager(new LinearLayoutManager(this));

        tracks = new ArrayList<>();

        textViewNombre = findViewById(R.id.tvNombreArtista);
        textViewTrack = findViewById(R.id.tvNombreTrack);
        imageViewPlay = findViewById(R.id.iv_Play);
        imageViewPause = findViewById(R.id.iv_Pause);
        frameLayout = findViewById(R.id.frameLayoutReproductor);


        miRequestQueueTrack = Volley.newRequestQueue(this);
        analizarJSONTrack();
    }

    private void analizarJSONTrack() {
        String url = albumURL;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String nombreTrack = hit.getString("title");
                                String urlMp3 = hit.getString("preview");
                                String nomnbreArtista = "Anime Kei";
                                String rank = hit.getString("duration");

                                tracks.add(new Track(nombreTrack, nomnbreArtista, rank, urlMp3));
                            }
                            adaptadorAlbum = new AdaptadorAlbum(tracks, ActivityAlbum.this);
                            recyclerViewAlbum.setAdapter(adaptadorAlbum);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        miRequestQueueTrack.add(request);

    }

    @Override
    public void onClick(Track track) {
        String url = track.getMp3();
        frameLayout.setVisibility(View.VISIBLE);
        imageViewPause.setVisibility(View.VISIBLE);
        textViewTrack.setText(track.getNombreTrack()+" ° ");
        textViewNombre.setText(track.getNombreArtista());
        reproducirMp3(url, mediaPlayer);

    }

    private void reproducirMp3(String url , final MediaPlayer mediaPlayer) {
        // Set the media player audio stream type
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Try to play music/audio from url
        try {
                if (!mediaPlayer.isPlaying()) {
                    // Set the audio data source
                    mediaPlayer.setDataSource(url);
                    // Prepare the media player
                    mediaPlayer.prepare();
                    // Start playing audio from http url
                    mediaPlayer.start();
                }else {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }

            imageViewPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.reset();
                    mediaPlayer.stop();
                    frameLayout.setVisibility(View.INVISIBLE);
                }
            });


        } catch (IOException e) {
            // Catch the exception
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}

