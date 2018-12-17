package kamal.agooz.ahmed.waiting_list;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import kamal.agooz.ahmed.waiting_list.Data.WaitlistContract.WaitlistEntry;
import kamal.agooz.ahmed.waiting_list.Data.WaitlistHelper;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;

    WaitlistHelper waitlistHelper;
    SQLiteDatabase sqLiteDatabase;

    GuestAdapter guestAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.addnew_user_btn);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
        waitlistHelper=new WaitlistHelper(getApplicationContext());
        sqLiteDatabase= waitlistHelper.getReadableDatabase();
        cursor=getallguests();
        guestAdapter = new GuestAdapter(getApplicationContext(),cursor);
        recyclerView.setAdapter(guestAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent n = new Intent(getApplicationContext(),adduser.class);
                startActivity(n);
            }
        });

     new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,4|8)
     {
         @Override
         public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
         {
             return false;
         }

         @Override
         public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
         {
            long id =(Long)viewHolder.itemView.getTag();
            removeguest(id);
            guestAdapter.swapcursor(getallguests());
         }
     }).attachToRecyclerView(recyclerView);
    }
    public Cursor getallguests()

    {
        Cursor cursor;
        cursor= sqLiteDatabase.query(
                WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistEntry.COLUMN_TIMESTAMP);
                return cursor;
    }
    public  int removeguest (long id)
    {
        int count;
        count= sqLiteDatabase.delete(
                WaitlistEntry.TABLE_NAME,
                "_id=?",
                new  String[]{Long.toString(id)});
        return  count;
    }
}
