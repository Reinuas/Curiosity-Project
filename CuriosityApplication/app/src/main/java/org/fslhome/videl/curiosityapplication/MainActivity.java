package org.fslhome.videl.curiosityapplication;

import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.fslhome.videl.curiosityapplication.model.CuriosityDBAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mLayout = (LinearLayout) findViewById(R.id.homepage_wall);
        List<Button> list_buttons = getDeepButtons(mLayout);

        for(Button button : list_buttons)
        {
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Intent myIntent = new Intent(MainActivity.this, CuriositiesActivity.class);
                    CharSequence text = ((Button) arg0).getText();
                    myIntent.putExtra("org.fslhome.curiosity.curiosities.button_clicked", text); // Optional parameters
                    Log.i("Videl", "Clicked on button: " + text);
                    startActivity(myIntent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent myIntent = new Intent(this, SettingsActivity.class);
            startActivity(myIntent);
            return true;
        } else if (id == R.id.action_drop_db) {
            CuriosityDBAdapter dbAdapter = new CuriosityDBAdapter(this);
            dbAdapter.open_write().completeDatabaseErase();
            dbAdapter.close();

            Toast.makeText(this, "Erased DB", Toast.LENGTH_SHORT).show();

            return true;
        } else if (id == R.id.action_clean_db) {
            CuriosityDBAdapter dbAdapter = new CuriosityDBAdapter(this);
            dbAdapter.open_write().addNewCuriosityData("Maynooth", "The Roost", "Every Thursday night, this place is rocking it!", 53.38080, -6.59194);
            dbAdapter.open_write().addNewCuriosityData("Maynooth", "Home", "Developper's home ;)", 53.37441, -6.58603);
            dbAdapter.open_write().addNewCuriosityData("Maynooth", "Swimming pool", "A really nice swimming pool. Open every day for staff and student.", 53.378845, -6.596513);
            dbAdapter.open_write().addNewCuriosityData("Maynooth", "Braddys", "Every Tuesday evening from 10 to midnight, free Jazz music!", 53.381371, -6.590436);
            dbAdapter.open_write().addNewCuriosityData("Maynooth", "Museum", "For an hour to spare, you can visit the Museum of Antique studying tools in Maynooth University.", 53.378613, -6.598182);
            dbAdapter.open_write().addNewCuriosityData("Dublin", "Queen of Tarts", "Delicious pies and whatnot are sold here.", 53.344294, -6.268979);
            dbAdapter.open_write().addNewCuriosityData("Dublin", "Oscar Wilde Memorial Statue", "This statue in the Merrion Square will bring you back in time. Enjoy the Square too ;).", 53.340646, -6.250571);
            dbAdapter.open_write().addNewCuriosityData("Dublin", "Phoenix Park", "This park is really big. And you will find roaming deers too! Don't scare them though!", 53.356431, -6.331944);
            dbAdapter.open_write().addNewCuriosityData("Dublin", "Trinity College", "The famous University of Dublin, Trinity College. The Book of Kells can be seen here too.", 53.344477, -6.259334);
            dbAdapter.close();

            Toast.makeText(this, "Added default data.", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Button> getDeepButtons(LinearLayout mLayout)
    {
        List<Button> list_buttons = new ArrayList<Button>();

        for(int i = 0; i < mLayout.getChildCount(); i++)
        {
            View aview = mLayout.getChildAt(i);
            if(aview instanceof Button)
            {
                // Add click listener to each of them
                Log.i("Videl", "Button detected!");
                list_buttons.add((Button) aview);
            }
            else if(aview instanceof LinearLayout)
            {
                // Recursive
                list_buttons.addAll(getDeepButtons((LinearLayout) aview));
            }
        }

        return list_buttons;
    }

    public void cleanDB() {
        Toast tt = new Toast(this);
        tt.setText("Restored a clean DB.");
        tt.show();
    }

    public void dropDB() {
        Toast tt = new Toast(this);
        tt.setText("Erased DB.");
        tt.show();
    }

}
