package com.example.alva.recyclerview_json;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdaptadorEjemplo.AdaptadorInterface {
    private RecyclerView recyclerView;
    private AdaptadorEjemplo adaptadorEjemplo;
    private ArrayList<ItemEjemplo> listaItemEjemplo;
    private RequestQueue miRequestQueue;
    private String urlTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaItemEjemplo = new ArrayList<>();

        miRequestQueue = Volley.newRequestQueue(this);
        analizarJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_busqueda,menu);

        MenuItem itemBuscar = menu.findItem(R.id.buscar);
        SearchView searchView = (SearchView) itemBuscar.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptadorEjemplo.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }

    private void analizarJSON() {
        String url = "https://api.deezer.com/artist/1547241/albums";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String nombreCreador = hit.getString("title");
                                String urlImangen = hit.getString("cover_big");
                                String fans = hit.getString("fans");
                                String lanzamineto = hit.getString("release_date");
                                String urlTrackList = hit.getString("tracklist");

                                listaItemEjemplo.add(new ItemEjemplo(urlImangen,nombreCreador,fans,lanzamineto,urlTrackList));
                            }
                            adaptadorEjemplo = new AdaptadorEjemplo(MainActivity.this,listaItemEjemplo,MainActivity.this);
                            recyclerView.setAdapter(adaptadorEjemplo);

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

        miRequestQueue.add(request);

    }

    @Override
    public void iraPantallaAlbum(ItemEjemplo itemEjemplo) {
        Intent intent = new Intent(MainActivity.this,ActivityAlbum.class);

        Bundle bundle = new Bundle();
        bundle.putString(ActivityAlbum.KEY_COVER_IMAGEN,itemEjemplo.getmIMagenUrl());
        bundle.putString(ActivityAlbum.KEY_URL_ALBUM,itemEjemplo.getUrlTracklist());
        intent.putExtras(bundle);
        startActivity(intent);


    }
}
