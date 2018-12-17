package kamal.agooz.ahmed.waiting_list;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kamal.agooz.ahmed.waiting_list.GuestAdapter.GuestViewHolder;
import kamal.agooz.ahmed.waiting_list.Data.WaitlistContract.WaitlistEntry;


public class GuestAdapter extends RecyclerView.Adapter<GuestViewHolder>
{
    Context context;
    Cursor cursor;
    WhenItemClicked whenItemClicked;

    public GuestAdapter(Context context, Cursor cursor)
    {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position)
    {
        if(!cursor.moveToPosition(position))
        {
            return;
        }
        String name = cursor.getString(cursor.getColumnIndex(WaitlistEntry.COLUMN_GUEST_NAME));
        String number = cursor.getString(cursor.getColumnIndex(WaitlistEntry.COLUMN_PARTY_SIZE));
        long id = cursor.getLong(cursor.getColumnIndex(WaitlistEntry._ID));

        holder.name.setText(name);
        holder.number.setText(number);
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }

    public void swapcursor(Cursor cursor)
    {
     if(this.cursor != null)
     {
        this.cursor.close();
     }
        this.cursor=cursor;
     if(cursor!=null)
     {
         notifyDataSetChanged();
     }


    }

    public class  GuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
             TextView name,number;

        public GuestViewHolder(View itemView)
        {
            super(itemView);
            name= itemView.findViewById(R.id.guest_name);
            number=itemView.findViewById(R.id.guest_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if(whenItemClicked != null)
            {
                int position = getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION)
                {
                    whenItemClicked.onClick(position);
                }
            }
        }
    }
    public interface WhenItemClicked
    {
        void onClick(int position);
    }
    public void Lamatdos3alaitem (WhenItemClicked whenItemClicked)

    {
        this.whenItemClicked=whenItemClicked;
    }
    ;

}

