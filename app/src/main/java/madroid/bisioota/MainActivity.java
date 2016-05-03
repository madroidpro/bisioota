package madroid.bisioota;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {


    public static final String Center_name_saved = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText center_number=(EditText)findViewById(R.id.center_number);
        final EditText date_select=(EditText)findViewById(R.id.date_select);
        final EditText teacher_count=(EditText)findViewById(R.id.no_teachers);
        final EditText maid_count=(EditText)findViewById(R.id.no_maids);
        final EditText student_count=(EditText)findViewById(R.id.no_students);

        date_select.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    DateDialog dialog = new DateDialog(view);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                    teacher_count.requestFocus();

                }
            }

        });

       // View view_p;
        SharedPreferences prefs = getSharedPreferences(Center_name_saved, MODE_PRIVATE);
        String restoredText = prefs.getString("center_number_saved", null);
        if (restoredText != null) {
            center_number.setText(restoredText);
            //center_number.clearFocus();
            date_select.requestFocus();

        }

        Button submit_button = (Button)findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.d("val",""+center_number.getText().length());
                if(center_number.getText().length() == 0 || center_number.getText().length() != 10){
                    center_number.setError("Invalid Center number");
                    Log.d("val",""+center_number.getText().length());
                    return;
                }
                if(date_select.getText().length() == 0){
                    date_select.setError("Invalid Date Count");
                    // Log.d("val",""+center_number.getText().length());
                    return;
                }
                if(teacher_count.getText().length() == 0){
                    teacher_count.setError("Invalid Teacher Count");
                   // Log.d("val",""+center_number.getText().length());
                    return;
                }
                if(maid_count.getText().length() == 0){
                    maid_count.setError("Invalid Maid Count");
                    // Log.d("val",""+center_number.getText().length());
                    return;
                }
                if(student_count.getText().length() == 0){
                    student_count.setError("Invalid Student Count");
                    // Log.d("val",""+center_number.getText().length());
                    return;
                }

                //Save Center number for next use
                SharedPreferences.Editor editor = getSharedPreferences(Center_name_saved, MODE_PRIVATE).edit();
                editor.putString("center_number_saved", "" + center_number.getText());
                editor.commit();

                Log.d("cn", "" + center_number.getText());
                // Prepare Transfer
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                intent.putExtra("center_number",""+center_number.getText());
                intent.putExtra("date_select",""+date_select.getText());
                intent.putExtra("teacher_count",""+teacher_count.getText());
                intent.putExtra("maid_count", ""+maid_count.getText());
                intent.putExtra("student_count",""+student_count.getText());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi,Download Bisioota Android App from https://play.google.com/store/apps/details?id="+getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            case R.id.menu_rate:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName()));
                startActivity(rateIntent);
                //Toast.makeText(MainActivity.this, "Rate is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_history:
                Intent history = new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(history);
                //Toast.makeText(MainActivity.this, "Rate is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
