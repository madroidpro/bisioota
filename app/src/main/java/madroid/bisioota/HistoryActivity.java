package madroid.bisioota;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try{
            SQLiteDatabase TeacherDB = openOrCreateDatabase("TeacherDB",MODE_PRIVATE,null);
            Cursor resultSet = TeacherDB.rawQuery("Select * from logs", null);
            // Log.d("c_data",resultSet+"");
            resultSet.moveToFirst();

            TableLayout l1 = (TableLayout) findViewById(R.id.main_table);
            //l1.setStretchAllColumns(true);
            l1.bringToFront();



            do
            {   TableRow tr =  new TableRow(getBaseContext());
                TextView t1 = new TextView(getBaseContext());
                t1.setTextColor(Color.parseColor("#000000"));
                TextView t2 = new TextView(getBaseContext());
                t2.setTextColor(Color.parseColor("#000000"));
                TextView t3 = new TextView(getBaseContext());
                t3.setTextColor(Color.parseColor("#000000"));
                TextView t4 = new TextView(getBaseContext());
                t4.setTextColor(Color.parseColor("#000000"));
                TextView t5 = new TextView(getBaseContext());
                t5.setTextColor(Color.parseColor("#000000"));
                // t1.setBackgroundResource(R.drawable.);
                t1.setText(resultSet.getString(0));
                t2.setText(resultSet.getString(1));
                t3.setText(resultSet.getString(2));
                t4.setText(resultSet.getString(3));
                t5.setText(resultSet.getString(4));

                tr.addView(t1);
                tr.addView(t2);
                tr.addView(t3);
                tr.addView(t4);
                tr.addView(t5);

                l1.addView(tr);
            }while(resultSet.moveToNext());
            resultSet.close();
        }catch (Exception ex){
            Log.d("sql_err",""+ex);
        }

        Button ext_btn=(Button)findViewById(R.id.exit_button);
        ext_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    // Initiating Menu XML file (menu.xml)

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(3).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

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

            case R.id.menu_export:
                exportDB();
                return true;

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



            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void exportDB() {

        SQLiteDatabase TeacherDB = openOrCreateDatabase("TeacherDB",MODE_PRIVATE,null);
        Cursor resultSet = TeacherDB.rawQuery("Select * from logs", null);
        // Log.d("c_data",resultSet+"");
        resultSet.moveToFirst();
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "teacherlog.csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = TeacherDB.rawQuery("Select * from logs", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2),curCSV.getString(3), curCSV.getString(4)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
            Toast.makeText(getApplicationContext(),"Exported Successfully", Toast.LENGTH_LONG).show();
        } catch (Exception sqlEx) {
            Log.e("export_err", sqlEx+"");
        }


    }
}
