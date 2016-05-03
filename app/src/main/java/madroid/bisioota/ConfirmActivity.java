package madroid.bisioota;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private String center_number;
    private String date_select;
    private String teacher_count;
    private String maid_count;
    private String student_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        createDatabase();
        Intent intent = getIntent();
         center_number = intent.getExtras().getString("center_number");
         date_select =intent.getExtras().getString("date_select");
         teacher_count = intent.getExtras().getString("teacher_count");
         maid_count = intent.getExtras().getString("maid_count");
         student_count = intent.getExtras().getString("student_count");

        String final_message=date_select+','+teacher_count+','+maid_count+','+student_count;
        Log.d("msg",""+final_message);
        TextView success_text= (TextView) findViewById(R.id.success_msg);
        ImageView img=(ImageView)findViewById(R.id.success_image);
        // Send SMS
       try{
           SmsManager sms = SmsManager.getDefault();
           sms.sendTextMessage(center_number, null, final_message, null, null);

           img.setVisibility(View.VISIBLE);
           success_text.setVisibility(View.VISIBLE);
           insertIntoDB();
       }catch (Exception ex){
           Log.d("error",""+ex);
           img.setImageResource(R.drawable.no);
           success_text.setText("Something Went wrong, please try again.");
           success_text.setVisibility(View.VISIBLE);
       }
        Button hist_btn=(Button)findViewById(R.id.history_button);
        hist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history = new Intent(ConfirmActivity.this,HistoryActivity.class);
                startActivity(history);

            }
        });

        Button ext_btn = (Button)findViewById(R.id.confirm_exit_button);
        ext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi,Download Bisiuuta App on google play");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            case R.id.menu_rate:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName()));
                startActivity(rateIntent);
                //Toast.makeText(MainActivity.this, "Rate is Selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menu_history:
                Intent history = new Intent(ConfirmActivity.this,HistoryActivity.class);
                startActivity(history);
                //Toast.makeText(MainActivity.this, "Rate is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Creating database

    protected void createDatabase(){
        db=openOrCreateDatabase("TeacherDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS logs(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, date_select VARCHAR,teacher_count VARCHAR,maid_count VARCHAR,student_count VARCHAR);");
    }

    protected void insertIntoDB(){

        if(date_select.equals("") || teacher_count.equals("") || maid_count.equals("") || student_count.equals("")){
            Toast.makeText(getApplicationContext(),"Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        String query = "INSERT INTO logs (date_select,teacher_count,maid_count,student_count) VALUES('"+date_select+"', '"+teacher_count+"','"+maid_count+"','"+student_count+"');";
        db.execSQL(query);
        Toast.makeText(getApplicationContext(),"Saved Successfully", Toast.LENGTH_LONG).show();
    }


}
