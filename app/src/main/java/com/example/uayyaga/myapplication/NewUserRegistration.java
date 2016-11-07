package com.example.uayyaga.myapplication;

import android.app.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import static android.R.string.cancel;


/**
 * Created by uayyaga on 10/24/16.
 */

public class NewUserRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText username,fathername,age,email,password,confirmPassword,male_valuehidden,datePicker;
    RadioButton male_radiobutton,female_radiobutton;
    RadioGroup RadioGroup;

    ArrayList<String> selection= new ArrayList<String>();
    DBHelper dbHelper;
    CheckBox englishchecked,hindichecked,tamilchecked,frecnhchecked,teluguchecked,malayalamchecked,germanchecked;
    TextView age_edittext;
    Spinner spinner;
    Button submit_button,reset_button;
    private int mYear, mMonth, mDay;
    private LoginActivity.UserLoginTask mAuthTask = null;
    boolean cancel = false;
    View focusView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_newuserregistration);
        dbHelper = new DBHelper(NewUserRegistration.this);
        RadioGroup = (RadioGroup)findViewById(R.id.RadioGroup);
        spinner = (Spinner) findViewById(R.id.religion_spinner);
        spinner.setOnItemSelectedListener(NewUserRegistration.this);
        List<String> categories = new ArrayList<String>();
        categories.add("Hindu");
        categories.add("Christian");
        categories.add("Muslim");
        username = (EditText)findViewById(R.id.username);
        fathername = (EditText)findViewById(R.id.father_name);
        age = (EditText)findViewById(R.id.age_edittext);
        male_radiobutton = (RadioButton)findViewById(R.id.male_radiobutton);
        female_radiobutton = (RadioButton)findViewById(R.id.female_radiobutton);
        email = (EditText)findViewById(R.id.email_edittext);
        password = (EditText)findViewById(R.id.password_edittext);
        confirmPassword = (EditText)findViewById(R.id.confirm_password);
        reset_button = (Button)findViewById(R.id.reset_button);

        //languages list check box
        englishchecked=(CheckBox)findViewById(R.id.english_checkbox);
        frecnhchecked=(CheckBox)findViewById(R.id.french_checkbox);
        tamilchecked=(CheckBox)findViewById(R.id.tamil_checkbox);
        germanchecked=(CheckBox)findViewById(R.id.german_checkbox);
        malayalamchecked=(CheckBox)findViewById(R.id.malayalam_checkbox);
        hindichecked=(CheckBox)findViewById(R.id.hindi_checkbox);
        teluguchecked=(CheckBox)findViewById(R.id.telugu_checkbox);
        submit_button=(Button)findViewById(R.id.submit_button);

        //age calculation using date spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        datePicker = (EditText) findViewById(R.id.date_of_birth);
        age_edittext = (TextView) findViewById(R.id.age_edittext);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewUserRegistration.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                int age = Calendar.getInstance().get(Calendar.YEAR) - year;
                                datePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Log.d("age", String.valueOf(age));
                                age_edittext.setText(String.valueOf(age));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                Log.d("year", String.valueOf(mYear) + String.valueOf(mMonth) + String.valueOf(mDay));
            }
        });


        //action performed when submit button is clicked
        submit_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        RadioButton selectRadio = (RadioButton) findViewById(RadioGroup.getCheckedRadioButtonId());
        RadioButton rb = (RadioButton) RadioGroup.findViewById(RadioGroup.getCheckedRadioButtonId());
        StringBuilder result=new StringBuilder();
        String uname=username.getText().toString();
        String fname = fathername.getText().toString();
        String dob = datePicker.getText().toString();
        String age = age_edittext.getText().toString();
        String religion = spinner.getSelectedItem().toString();
        String sex = rb.getText().toString();
        String email1 = email.getText().toString();
        String pwd = password.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(pwd) && !isPasswordValid(pwd)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email1)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(email1)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        //handling cases for language selection
        result.append(":");
        if(englishchecked.isChecked()){
            result.append("\nEnglish");
        }
        if(tamilchecked.isChecked()){
            result.append("\nTamil");
        }
        if(teluguchecked.isChecked()){
            result.append("\nTelugu");
        }
        if(frecnhchecked.isChecked()){
            result.append("\nFrench");
        }
        if(malayalamchecked.isChecked()){
            result.append("\nMalayalam");
        }
        if(germanchecked.isChecked()){
            result.append("\nGerman");
        }
        if(hindichecked.isChecked()){
            result.append("\nHindi");
        }
           dbHelper.insertData(uname, fname, dob, age, religion, sex, email1, pwd, result.toString());
        Toast.makeText(getApplicationContext(), "Details submitted successfully", Toast.LENGTH_LONG).show();
    }
});
        //giving action for reset button
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                fathername.setText("");
                datePicker.setText("");
                age_edittext.setText("");

                if(male_radiobutton.isChecked())
                {
                    male_radiobutton.toggle();
                }
                if(female_radiobutton.isChecked())
                {
                    female_radiobutton.toggle();
                }
                email.setText("");
                password.setText("");
                confirmPassword.setText("");
                if(englishchecked.isChecked())
                {
                    englishchecked.toggle();
                }
                if(hindichecked.isChecked())
                {
                    hindichecked.toggle();
                }
                if(frecnhchecked.isChecked())
                {
                    frecnhchecked.toggle();
                }
                if(tamilchecked.isChecked())
                {
                    tamilchecked.toggle();
                }
                if(teluguchecked.isChecked())
                {
                    teluguchecked.toggle();
                }
                if(malayalamchecked.isChecked())
                {
                    malayalamchecked.toggle();
                }
                if(germanchecked.isChecked())
                {
                    germanchecked.toggle();
                }

            }
        });

    }

    public void selectItems( View view) {
        boolean checked = ((CheckBox) view).isChecked();
        //selecting the languages using a switch case
        switch (view.getId())

        {
            case R.id.english_checkbox:
                if (checked) {
                    selection.add("English");
                } else {
                    selection.remove("English");
                }
                break;

            case R.id.hindi_checkbox:
                if (checked) {
                    selection.add("Hindi");
                } else {
                    selection.remove("Hindi");
                }
                break;
            case R.id.french_checkbox:
                if (checked) {
                    selection.add("French");
                } else {
                    selection.remove("French");
                }
                break;
            case R.id.tamil_checkbox:
                if (checked) {
                    selection.add("Tamil");
                } else {
                    selection.remove("Tamil");
                }
                break;
            case R.id.telugu_checkbox:
                if (checked) {
                    selection.add("Telugu");
                } else {
                    selection.remove("Telugu");
                }
                break;
            case R.id.malayalam_checkbox:
                if (checked) {
                    selection.add("Malayalam");
                } else {
                    selection.remove("Malayalam");
                }
                break;
            case R.id.german_checkbox:
                if (checked) {
                    selection.add("German");
                } else {
                    selection.remove("German");
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
    }

    private boolean isEmailValid(String email) {
        //checking email is valid or not using regex
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String pass) {
        //checking password is valid or not using regex
        if (pass != null && pass.length() > 3) {
            String PASSWORD_PATTERN = "^((?=\\S*?[A-Z])(?=\\S*?[a-z])(?=\\S*?[0-9])(?=.*[!@#$&*]).{4,})\\S$";
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(pass);
            return matcher.matches();
        }
        return false;
    }
}