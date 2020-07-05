package gnz.julaa.kanou;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11.sabou.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authification extends AppCompatActivity {
    private EditText userEmail, userPassword;
    private TextView count_attempt, new_user;
    private int compteur=5;
    private Button login;
    private ProgressDialog progressDialog;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authification);
        ui_config();
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(Authification.this, MainActivity.class));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Authification.this, Registration.class));
            }
        });

}
private void ui_config(){
        userEmail = (EditText)findViewById(R.id.user_email);
        userPassword=(EditText)findViewById(R.id.user_password);
        count_attempt = (TextView)findViewById(R.id.nbr_attempt);
        new_user = (TextView)findViewById(R.id.new_user);
        login = (Button)findViewById(R.id.user_login);
}
private void validate(String User_mail, String UserPwd){
        progressDialog.setMessage("Patientez...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(User_mail, UserPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()){
               progressDialog.dismiss();
               startActivity(new Intent(Authification.this, MainActivity.class));
           }
           else {
               progressDialog.dismiss();
               Toast.makeText(Authification.this, "Erreur d'authification, veillez ressayer",
                       Toast.LENGTH_LONG).show();
               compteur--;
               count_attempt.setText("Nombre de tentative restant : "+compteur);
               if(compteur==0){
                   login.setEnabled(true);
               }

           }
            }
        });
}
}
