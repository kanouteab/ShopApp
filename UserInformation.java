package gnz.julaa.kanou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a11.sabou.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInformation extends AppCompatActivity {
    private  EditText UserName;
    private EditText UserTel;
    private String CountrySelected;
    private Button Save;
    private String Firebase_Database_Path = "Infos";
    private FirebaseAuth auth;
    private  FirebaseDatabase database;
    public static  final String FB_DATABASE_PATH="Infos";
    private DatabaseReference reference;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        ui_config();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Firebase_Database_Path);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfos();
            }
        });
    }
    private void ui_config(){
        UserName = (EditText)findViewById(R.id.user_name);
        UserTel = (EditText)findViewById(R.id.user_phone);
        Save = (Button)findViewById(R.id.user_signup);
        spinner = (Spinner)findViewById(R.id.spiner);
    }
    private void saveInfos(){
        FirebaseUser user   = auth.getCurrentUser();
        String user_email = user.getEmail();
        String user_directory = user_email.replace(".","_");
        reference.child((user_directory)).setValue(new AuthentInfos(UserName.getText().toString(), UserTel.getText().toString(),
                CountrySelected)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserInformation.this, "Saved", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserInformation.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserInformation.this, "Failed", Toast.LENGTH_LONG).show();
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(UserInformation.this, "Canceled", Toast.LENGTH_LONG).show();
            }
        });
    }
}
