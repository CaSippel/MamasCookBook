package com.example.mamascookbook;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

import static android.util.Log.d;

public class AppDatabase {
    // a wrapper around the SQLite db handler to isolate the handler's usage
    private static final String TAG = "Tag-AppDatabase";

    private static class SQLiteDatabaseHandler extends SQLiteOpenHelper {
        //private static final String TAG = "Tag-AppDbHandler";

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "RecipesDB";
        private static final String TABLE_NAME = "Recipes";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_INGREDIENTS = "ingredients";
        private static final String KEY_INSTRUCTIONS = "instructions";
        private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_INGREDIENTS, KEY_INSTRUCTIONS};

        private SQLiteDatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        private void deleteAll() {
            d(TAG, "deleteAll()");
            String q = "DELETE FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(q);
            db.close();
            // reset the counter
            mNextId = 0;
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putInt(mPreferencesKey, mNextId);
            editor.commit();
        }

        private void deleteOne(String name) {
            //todo: should we replace this with an int or the name of the recipe?
            d(TAG, "deleteOne(" + name +")");
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(
                    TABLE_NAME,
                    KEY_NAME + " = ?", // was KEY_ID
                    new String[] {name}
            );
            db.close();
        }

        private RecipeInfo getObjectFromCursor(Cursor c) {
            d(TAG, "getObjectFromCursor");
            //todo: change this back to an assertion
            if(!isValidCursor(c)) {
                d(TAG, "  ERR: getObject from cursor, cursor is invalid");
                return new RecipeInfo();
            }
            RecipeInfo ri = new RecipeInfo(Integer.parseInt(c.getString(0)),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            );
            d(TAG, "  getting recipe info... (" + ri.getId() + ") " + ri.getName());
            return ri;
        }

        private RecipeInfo getOne(String name) {
            d(TAG, "getOne(" + name + ")");

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME,
                    COLUMNS,
                    KEY_NAME + " = ?",
                    new String[] {name}, // params
                    null,
                    null,
                    null,
                    null
            );

            if (isValidCursor(cursor) && cursor.moveToFirst()) {
                return getObjectFromCursor(cursor);
            } else {
                return new RecipeInfo();
            }
        }

        private ContentValues getValuesFromRecipe(RecipeInfo recipe) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, recipe.getId());
            values.put(KEY_NAME, recipe.getName());
            values.put(KEY_INGREDIENTS, recipe.getIngredient());
            values.put(KEY_INSTRUCTIONS, recipe.getInstructions());
            return values;
        }

        private long insert(RecipeInfo recipe) {
            d(TAG, "inserting : (" + recipe.getId() + ")" + recipe.getName());

            SQLiteDatabase db = this.getWritableDatabase();
            long err = db.insert(TABLE_NAME, null, getValuesFromRecipe(recipe));
            db.close();
            return err;
        }

        private boolean isValidCursor(Cursor c) {
            return c != null && c.getCount() > 0;
        }

        private List<RecipeInfo> listAll() {
            d(TAG, "listAll()");
            List<RecipeInfo> recipes = new LinkedList<RecipeInfo>();
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (isValidCursor(cursor) && cursor.moveToFirst()) { // is checking for validity redundant?
                do {
                    recipes.add(getObjectFromCursor(cursor));
                } while (cursor.moveToNext());
            }
            return recipes;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            d(TAG, "OnCreate");
            String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT, " +
                    KEY_INGREDIENTS + " TEXT, " +
                    KEY_INSTRUCTIONS + " TEXT)";
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            d(TAG, "OnUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(db);
        }

        private int update(RecipeInfo recipe) {
            d(TAG, "update(" + recipe.getName() + ")");
            SQLiteDatabase db = this.getWritableDatabase();

            int i = db.update(TABLE_NAME,
                    getValuesFromRecipe(recipe),
                    KEY_ID + " = ?",
                    new String[] {String.valueOf(recipe.getId())}
            );
            db.close();
            return i;
        }
    }
    private static SQLiteDatabaseHandler mDatabaseHandler;
    private static SharedPreferences mPreferences;
    private static int mNextId = 0;
    private static final String mPreferencesName = "db_preferences";
    private static final String mPreferencesKey = "next_id";

    private static int getNextId() {
        assert(mPreferences != null);
        int id = mNextId;
        SharedPreferences.Editor editor = mPreferences.edit();
        d(TAG, "storing next id: " + ++mNextId);
        editor.putInt(mPreferencesKey, mNextId);
        editor.commit();
        return id;
    }

    public AppDatabase(Context context) {
        if (mDatabaseHandler == null) {
            mDatabaseHandler = new SQLiteDatabaseHandler(context);
        }
        if (mNextId == 0) {
            d(TAG, "getting nextId from preferences");
            mPreferences = context.getSharedPreferences(mPreferencesName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mPreferences.edit();
            mNextId = mPreferences.getInt(mPreferencesKey, 0);
        }
        d(TAG, "starting nextId is: " + mNextId);
    }

    public long addRecipe(String name, String ingredient, String instructions) {
        // returns the id if it was successful, else -1 to indicate an error
        return mDatabaseHandler.insert(new RecipeInfo(getNextId(), name, ingredient, instructions));
    }

    public List<RecipeInfo> allRecipes() {
        return mDatabaseHandler.listAll();
    }

    public void deleteAllRecipes() {
        mDatabaseHandler.deleteAll();
    }

    public void deleteRecipe(String name) {
        mDatabaseHandler.deleteOne(name);
    }

    public RecipeInfo getRecipe(String name) {
        return mDatabaseHandler.getOne(name);
    }

    public int updateRecipeInfo(RecipeInfo recipe) {
        return mDatabaseHandler.update(recipe);
    }
}
