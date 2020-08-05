package chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tinderapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {
    private RecyclerView mrecycleview;
    private RecyclerView.Adapter mchatAdapter;
    private RecyclerView.LayoutManager mchatLayoutManger;
    private String currentuserid,matchId,chatid;

    DatabaseReference mdatabaseuser,mdatabasechat;
    private EditText mSendEditText;
    private Button msendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        matchId=getIntent().getExtras().getString("matchId");
        currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mdatabaseuser= FirebaseDatabase.getInstance().getReference().child("Users").child(currentuserid).child("connections").child("matches").child(matchId).child("ChatId");
        mdatabasechat= FirebaseDatabase.getInstance().getReference().child("Chat");
        getChildId();
        mrecycleview=(RecyclerView)findViewById(R.id.recycleview);
        mrecycleview.setNestedScrollingEnabled(false);
        mrecycleview.setHasFixedSize(false);
        mchatLayoutManger=new LinearLayoutManager(ChatActivity.this);
        mrecycleview.setLayoutManager(mchatLayoutManger);
        mchatAdapter= new ChatAdapter(getDataSetChats(),ChatActivity.this);
        mrecycleview.setAdapter(mchatAdapter);
        mSendEditText=findViewById(R.id.message);
        msendButton=findViewById(R.id.send);

        msendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String sendMessagetext=mSendEditText.getText().toString();
        if(!sendMessagetext.isEmpty())
        {
            DatabaseReference newmessagedb= mdatabasechat.push();
            Map newmessage=new HashMap<>();
            newmessage.put("createByUser",currentuserid);
            newmessage.put("text",sendMessagetext);
            newmessagedb.setValue(newmessage);
        }
        mSendEditText.setText(null);
    }
    private void getChildId()
    {
        mdatabaseuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    chatid=snapshot.getValue().toString();
                    mdatabasechat=mdatabasechat.child(chatid);
                    getChatMessages();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getChatMessages() {
        mdatabasechat .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String message=null;
                    String createbyuser=null;
                    if(snapshot.child("text").getValue()!=null)
                    {
                        message=snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("createByUser").getValue()!=null)
                    {
                        createbyuser=snapshot.child("createByUser").getValue().toString();
                    }
                    if(message!=null&& createbyuser!=null)
                    {
                        Boolean currentuserboolean=false;
                        if(createbyuser.equals(currentuserid))
                        {
                            currentuserboolean=true;
                        }
                        ChatObject newMessage=new ChatObject(message,currentuserboolean);
                        resultschat.add(newMessage);
                        mchatAdapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private ArrayList<ChatObject> resultschat =new ArrayList<ChatObject>();
    private ArrayList<ChatObject> getDataSetChats() {
        return resultschat;
    }
}
