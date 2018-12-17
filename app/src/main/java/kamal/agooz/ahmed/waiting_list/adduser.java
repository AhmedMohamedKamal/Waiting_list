package kamal.agooz.ahmed.waiting_list;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kamal.agooz.ahmed.waiting_list.Data.WaitlistHelper;
import kamal.agooz.ahmed.waiting_list.Data.WaitlistContract.WaitlistEntry;


public class adduser extends AppCompatActivity
{
    EditText guestname,guestnumber;
    Button add_user;
    WaitlistHelper waitlistHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        guestname=findViewById(R.id.guest_name_field);
        guestnumber=findViewById(R.id.guest_number_field);
        add_user=findViewById(R.id.save_new_user);
        waitlistHelper =new WaitlistHelper(getApplicationContext());
        sqLiteDatabase=waitlistHelper.getWritableDatabase();

        add_user.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = guestname.getText().toString();
                String size =guestnumber.getText().toString();
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(size))
                {
                    Toast.makeText(getApplicationContext(), "please enter a valid data", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    AddNewGuest( name, size);
                    Intent n = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(n);
                }
            }
        });
    }
    public long AddNewGuest(String name ,String size)
    {
        long id;
        ContentValues contentValues=new ContentValues();
        contentValues.put(WaitlistEntry.COLUMN_GUEST_NAME,name);
        contentValues.put(WaitlistEntry.COLUMN_PARTY_SIZE,size);
        id=sqLiteDatabase.insert(
                WaitlistEntry.TABLE_NAME,
                null,
                contentValues);
        return id;
    }
}
