package gnz.julaa.kanou;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a11.sabou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {
    private EditText  UserEmail, UserPass,UserConfPass;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private ProgressDialog dialog;
    private Button User_SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("UsersEmails");
        dialog = new ProgressDialog(this);
        ui_config();
        User_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    signUp();
                }
                else {
                    validate();
                }
            }
        });
    }
    private void ui_config()
    {
        UserEmail = (EditText)findViewById(R.id.user_email);
        UserPass = (EditText)findViewById(R.id.user_password);
        UserConfPass = (EditText)findViewById(R.id.user_conf_password);
        User_SignUp = (Button)findViewById(R.id.user_signup);
    }
    private Boolean validate(){
        Boolean result = false;
        if((UserEmail.getText().toString().isEmpty() || (UserPass.getText().toString().isEmpty()) ||
                (UserConfPass.getText().toString().isEmpty())))
        {
            Toast.makeText(Registration.this,"Veuillez renseigner tous les champs", Toast.LENGTH_LONG).show();

        }
        else if(!(UserConfPass.getText().toString().equals(UserConfPass.getText().toString())))
        {
            Toast.makeText(Registration.this, "Mot de passe incorrect, veuillez ressayer",
                    Toast.LENGTH_LONG).show();
        }
        else {
            result = true;
        }
        return result;
    }
    private void signUp(){
        reference.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(String.class).equals(UserEmail.getText().toString()))
                {
                    Toast.makeText(Registration.this, "Cet email existe déja dans notre base des donées, " +
                            "veuillez vérifier", Toast.LENGTH_LONG).show();
                }
                else {
                    dialog.setMessage("Patientez...");
                    dialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(UserEmail.getText().toString(),
                            UserPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Toast.makeText(Registration.this, "Succès", Toast.LENGTH_LONG).show();
                                reference.push().setValue(new EmailInfos(UserEmail.getText().toString())).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Registration.this, "Merci", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Registration.this, UserInformation.class));
                                    }
                                });

                            }

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Registration.this, "erreur de souscription", Toast.LENGTH_LONG).show();
            }
        });
    }
}
