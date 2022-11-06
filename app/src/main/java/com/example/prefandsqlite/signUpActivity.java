package com.example.prefandsqlite;

import android.content.Context;
import android.content.Intent;
        import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
        import android.preference.Preference;
        import android.preference.PreferenceActivity;
        import android.preference.PreferenceManager;
        import android.widget.Toast;

public class signUpActivity  extends PreferenceActivity {
    public static String nom;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       SQLiteDatabase dataBase=openOrCreateDatabase("UserDB", Context.MODE_PRIVATE, null);
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS user(username VARCHAR,mail VARCHAR,password VARCHAR);");
        addPreferencesFromResource(R.xml.sign_up);
        Preference btn = (Preference) findPreference(getString(R.string.loginval));
        btn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences prefs = PreferenceManager
                        .getDefaultSharedPreferences(signUpActivity.this);

                String pass = prefs.getString("password", "Default Password");
                String email = prefs.getString("mail", "Default Mail");
                String username = prefs.getString("username", "Default Mail");

                if ((!(username.isEmpty()))&&(!(email.isEmpty()))&&(!(pass.isEmpty()))){
                    //has data
                    Toast.makeText(signUpActivity.this, "please wait until we check data", Toast.LENGTH_SHORT).show();

                    // Searching roll number
                    Cursor cursor=dataBase.rawQuery("SELECT * FROM user WHERE mail='"+email+"'", null);
                    if(cursor.moveToFirst())
                    {
                        Toast.makeText(signUpActivity.this, "this user already registred", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        dataBase.execSQL("INSERT INTO user VALUES('"+username+"','"+ email+"','"+pass+"');");
                        Toast.makeText(signUpActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                      /*  Intent intent = new Intent(first_activity.this,
                                second_activity.class);
                        startActivity(intent);*/
                    }



                }else{
                    Toast.makeText(signUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });
    }}