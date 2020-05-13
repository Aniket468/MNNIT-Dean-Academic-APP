package com.example.aniketkumar.mnnit_portal;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailingActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private EditText mail,pass;
    LinearLayout save;
    Button savemail;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailing);

        mail=findViewById(R.id.mail);
        pass=findViewById(R.id.pass);
        save=findViewById(R.id.save);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextMessage = findViewById(R.id.editTextMessage);


        buttonSend = findViewById(R.id.buttonSend);


        buttonSend.setOnClickListener(this);
    }
    public void saveMail(View view)
    {
        String m,p;
        m=mail.getText().toString().trim();
        p=pass.getText().toString().trim();
        if(m.equals("")||p.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Empty Details can't be Save",Toast.LENGTH_LONG).show();
        }
        else {
            new Config(m, p);

            save.setVisibility(View.GONE);
        }


    }
    public void changemail(View view)
    {
        save.setVisibility(View.VISIBLE);
    }

    private void sendEmail() {

        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if(email.equals("")||subject.equals("")||message.equals(""))
        {
            Toast.makeText(getApplicationContext(),"Please fill all credentials first",Toast.LENGTH_LONG).show();
        }
        else{
        SendMail sm = new SendMail(this, email, subject, message);

        sm.execute();}
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
    public class SendMail extends AsyncTask<Void,Void,Void> {
  int p=1;

        private Context context;
        private Session session;

        private String email;
        private String subject;
        private String message;
        private ProgressDialog progressDialog;

        public SendMail(Context context, String email, String subject, String message){



            this.context = context;
            this.email = email;
            this.subject = subject;
            this.message = message;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if(p==0)
            {
                Toast.makeText(getApplicationContext(),"Authetication Error",Toast.LENGTH_LONG).show();
            }
            else
            Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Creating properties
            Properties props = new Properties();
            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.googlemail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
//            new Config("aniket468kr@gmail.com","password");
            //Creating a new session
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);

                        }
                    });

            try {
                //Creating MimeMessage object
                MimeMessage mm = new MimeMessage(session);

                                             //email write
                mm.setFrom(new InternetAddress(Config.EMAIL));
                Log.e("TAG",Config.EMAIL);
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //Adding subject
                mm.setSubject(subject);
                //Adding message
                mm.setText(message);
                //Sending email
                Transport.send(mm);

            } catch (MessagingException e) {
                Log.e("TAG","kya h"+e);
                p=0;
                e.printStackTrace();
            }
            return null;
        }
    }
    public void opengmail(View view)
    {
        try{
            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + ""));
            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),"Error opening Gmail ",Toast.LENGTH_LONG).show();
            //TODO smth
        }
    }
}