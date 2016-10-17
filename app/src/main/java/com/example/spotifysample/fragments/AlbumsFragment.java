package com.example.spotifysample.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.spotifysample.R;
import com.example.spotifysample.SpotifySampleApplication;
import com.example.spotifysample.Utils.ErrorHandler;
import com.example.spotifysample.adapters.AlbumsAdapter;
import com.example.spotifysample.spotifyResponseModels.itemModels.AlbumItem;
import com.example.spotifysample.spotifyResponseModels.pagingObjectModels.AlbumPagingObject;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumsFragment extends Fragment
        implements SearchView.OnQueryTextListener{

    private String TAG = AlbumsFragment.class.getSimpleName();

    private SearchView mSearchView;
    private MenuItem searchMenuItem;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private AlbumsAdapter mAdapter;
    private TextView noAlbumsText;

    private ArrayList<AlbumItem> listOfAlbums;

    /**
     * Current value of the search term to be used
     */
    private String searchTerm;

    /**
     * Limit for the number of items to be shown in each iteration
     */
    private int ALBUM_RESPONSE_LIMIT = 10;

    /**
     * URL for searching an album on spotify api
     */
    private String URL_ALBUM_SEARCH = "https://api.spotify.com/v1/search?type=album";

    /**
     * JSON key for handling the response from album search
     */
    private String JSON_ALBUMS_PAGING_KEY = "albums";

    /**
     * Key value for saving the search term in saveInstanceState
     */
    private String KEY_SEARCH_TERM = "searchTerm";

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchTerm = query;
        searchForAlbum(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public AlbumsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment
     *
     * @return A new instance of fragment AlbumsFragment.
     */
    public static AlbumsFragment newInstance() {
        return new AlbumsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listOfAlbums = new ArrayList<>();

        if(savedInstanceState != null){
            searchTerm = savedInstanceState.getString(KEY_SEARCH_TERM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

        if(mToolbar != null){
            ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
            mToolbar.setTitle("Albums");
        }

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new AlbumsAdapter(listOfAlbums, new AlbumsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(String albumName, String albumId) {
                Log.i(TAG, "RecyclerView item clicked. AlbumName : "+albumName);
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        noAlbumsText = (TextView) rootView.findViewById(R.id.no_albums_text);
        if(mAdapter.getItemCount() == 0){
            noAlbumsText.setText(getString(R.string.no_albums_text));
            noAlbumsText.setVisibility(View.VISIBLE);
        }else{
            noAlbumsText.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_albums_fragment, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(!TextUtils.isEmpty(searchTerm)){
            searchForAlbum(searchTerm);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();

        //cancel all the remaining requests in the queue
        SpotifySampleApplication.getApplicationInstance().
                cancelPendingRequests(TAG);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save the current search term in case we need to recreate the fragment
        if(!TextUtils.isEmpty(searchTerm)){
            outState.putString(KEY_SEARCH_TERM, searchTerm);
        }
    }

    /**
     * Search for albums using album name
     * @param albumName name to be used
     */
    private void searchForAlbum(String albumName){

        String finalURL = URL_ALBUM_SEARCH + "&limit="+ALBUM_RESPONSE_LIMIT + "&q="+albumName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(finalURL, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i(TAG, "Response received from server");

                        JSONObject albumPagingObject = response.optJSONObject(JSON_ALBUMS_PAGING_KEY);
                        AlbumPagingObject albumResponse = new AlbumPagingObject(albumPagingObject);

                        //clear the current list of albums
                        listOfAlbums.clear();
                        listOfAlbums.addAll(albumResponse.getListOfAlbums());

                        mRecyclerView.getAdapter().notifyDataSetChanged();

                        if(mRecyclerView.getAdapter().getItemCount() == 0){
                            noAlbumsText.setText(getString(R.string.no_albums_search_result));
                            noAlbumsText.setVisibility(View.VISIBLE);
                        }else{
                            noAlbumsText.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String message = ErrorHandler.getMessage(error, getActivity());
                        Log.d(TAG, "Error message : "+message);

                        listOfAlbums.clear();
                        mRecyclerView.getAdapter().notifyDataSetChanged();

                        noAlbumsText.setText(message);
                        noAlbumsText.setVisibility(View.VISIBLE);
                    }
                }
        );

        //add the request to the queue
        SpotifySampleApplication.getApplicationInstance()
                .addToRequestQueue(jsonObjectRequest, TAG);

    }

}
