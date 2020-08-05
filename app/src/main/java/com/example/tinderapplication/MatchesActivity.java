package com.example.tinderapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView mrecycleview;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManger;
    private String currentuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mrecycleview=(RecyclerView)findViewById(R.id.recycleview);
        mrecycleview.setNestedScrollingEnabled(false);
        mrecycleview.setHasFixedSize(true);
        mMatchesLayoutManger=new LinearLayoutManager(MatchesActivity.this);
        mrecycleview.setLayoutManager(mMatchesLayoutManger);
        mMatchesAdapter= new MatchesAdapter(getDataSetMatches(),MatchesActivity.this);
        mrecycleview.setAdapter(mMatchesAdapter);
        getusermatchid();


    }

    private void getusermatchid() {

        DatabaseReference matchdb= FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserid).child("connections").child("matches");
        matchdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot match: snapshot.getChildren())
                    {
                        Fetchmatchinfo(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Fetchmatchinfo(String key) {
        DatabaseReference userdb= FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String userid=snapshot.getKey();
                    String name="";
                    String profileImageUrl="";
                    if(snapshot.child("name").getValue()!=null)
                    {
                        name=snapshot.child("name").getValue().toString();
                    }
                    if(snapshot.child("profileImageUrl").getValue()!=null)
                    {
                        profileImageUrl=snapshot.child("profileImageUrl").getValue().toString();
                    }

                    MatchesObject obj = new MatchesObject(userid,name,profileImageUrl);
                    resultsmatches.add(obj);


                    mMatchesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<MatchesObject> resultsmatches =new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsmatches;
    }
}
