package com.markandersonix.localpets;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.markandersonix.localpets.Models.Favorites.FavoritesContract;
import com.markandersonix.localpets.Models.Favorites.FavoritesDbHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {
    ArrayList<String> ids;

    @BindView(R.id.favorites_recycler_view) RecyclerView favoritesRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        //String[] projection = {FavoritesContract.FavoriteEntry.COLUMN_NAME_URL};
        ids = new ArrayList<>();
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
            FavoritesContract.FavoriteEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null);
        c.moveToFirst();
        if(c != null && c.getCount() > 0) {
            do{
                ids.add(c.getString(1));
            }while(c.moveToNext());
        }else{
            //
        }

        favoritesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        favoritesRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoriteAdapter(this, ids);
        favoritesRecyclerView.setAdapter(mAdapter);
        //Toast.makeText(this, ids.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favorites, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_clear_favorites){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.clear_favorites_message);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    FavoritesDbHelper dbHelper = new FavoritesDbHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL(FavoritesDbHelper.getSqlDeleteEntries());
                    db.execSQL(FavoritesDbHelper.getSqlCreateEntries());
                    ids.clear();
                    mAdapter.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
