package madroid.bisioota;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
}
