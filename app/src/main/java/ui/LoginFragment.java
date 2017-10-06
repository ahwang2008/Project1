package ui;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ahwang13.familymapserver.R;

import net.ServerProxy;

import model.Model;
import model.Person;
import request.LoginRequest;
import request.RegisterRequest;
import result.EventResult;
import result.LoginResult;
import result.PersonResult;
import result.RegisterResult;

public class LoginFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    private MainActivity mainActivity;

    private EditText hostText;
    private EditText portText;
    private EditText userNameText;
    private EditText passwordText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;

    private String hostInput;
    private String portInput;
    private String userNameInput;
    private String passwordInput;
    private String firstNameInput;
    private String lastNameInput;
    private String emailInput;
    private String genderInput;

    private RadioButton maleButton;
    private RadioButton femaleButton;
    private Button loginButton;
    private Button registerButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        hostText = (EditText) v.findViewById(R.id.hostEditText);
        portText = (EditText) v.findViewById(R.id.portEditText);
        userNameText = (EditText) v.findViewById(R.id.userNameEditText);
        passwordText = (EditText) v.findViewById(R.id.passwordEditText);
        firstNameText = (EditText) v.findViewById(R.id.firstNameEditText);
        lastNameText = (EditText) v.findViewById(R.id.lastNameEditText);
        emailText = (EditText) v.findViewById(R.id.emailEditText);


        maleButton = (RadioButton) v.findViewById(R.id.maleButton);
        maleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderInput = "m";
            }
        });
        femaleButton = (RadioButton) v.findViewById(R.id.femaleButton);
        femaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderInput = "f";
            }
        });
        loginButton = (Button) v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonClicked();
            }
        });
        registerButton = (Button) v.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerButtonClicked();
            }
        });
        return v;
    }

    public void switchFragments()
    {
        //MainActivity ma = new MainActivity();
        //ma.setMainActivity();
        mainActivity = (MainActivity) getActivity();
        mainActivity.switchFragments();
    }

    public void loginButtonClicked()
    {
        hostInput = hostText.getText().toString();
        portInput = portText.getText().toString();
        userNameInput = userNameText.getText().toString();
        passwordInput = passwordText.getText().toString();
        LoginTask task = new LoginTask();
        //task.setLoginFragment(this);
        task.execute();
    }

    public void registerButtonClicked()
    {
        hostInput = hostText.getText().toString();
        portInput = portText.getText().toString();
        userNameInput = userNameText.getText().toString();
        passwordInput = passwordText.getText().toString();
        firstNameInput = firstNameText.getText().toString();
        lastNameInput = lastNameText.getText().toString();
        emailInput = emailText.getText().toString();
        RegisterTask task = new RegisterTask();
        task.execute();
    }

    public class LoginTask extends AsyncTask<Void, Boolean, Boolean>
    {
        //private LoginFragment loginFragment;

        //public void setLoginFragment(LoginFragment loginFragment) {
            //this.loginFragment = loginFragment;
        //}

        boolean success = false;
        @Override
        protected Boolean doInBackground(Void... voids) {
            if(hostInput.equals("") || portInput.equals("") || userNameInput.equals("") || passwordInput.equals(""))
            {
                return false;
            }
            else
            {
                LoginRequest loginRequest = new LoginRequest(userNameInput, passwordInput);
                ServerProxy sp = new ServerProxy();

                LoginResult loginResult = sp.login(hostInput, portInput, loginRequest);
                Model.instance().setAuthToken(loginResult.getAuthToken());
                Model.instance().setCurrPersonID(loginResult.getPersonID());
                success = sp.isSuccess();
                return true;
            }
            //return null;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool == false)
            {
                Toast.makeText(getContext(), "Login failed.", Toast.LENGTH_LONG).show();
            }
            if (success == true)
            {
                //Switch login Fragment to Map fragment and fetch data (Get Data task)
                GetDataTask task = new GetDataTask();
                //task.setLoginFragment(loginFragment);
                task.execute();
                //Toast.makeText(getContext(), Model.instance().getUser().getFirstName() + " " + Model.instance().getUser().getLastName(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(), "Username and/or Password not found within the database.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class GetDataTask extends AsyncTask<Void, Person, Person>
    {
        //private LoginFragment loginFragment;

        //public void setLoginFragment(LoginFragment loginFragment) {
            //this.loginFragment = loginFragment;
        //}

        @Override
        protected Person doInBackground(Void... voids)
        {
            ServerProxy sp = new ServerProxy();
            PersonResult personResult = sp.fetchPerson(hostInput, portInput, Model.instance().getAuthToken());
            EventResult eventResult = sp.fetchEvent(hostInput, portInput, Model.instance().getAuthToken());
            Person person = null;
            if (personResult.getErrorMessage() == null && eventResult.getErrorMessage() == null) //Success
            {
                Model.instance().setPeopleList(personResult.getData());
                Model.instance().setEventList(eventResult.getData());
                person = Model.instance().getPersonByPersonID(Model.instance().getCurrPersonID());
            }
            return person;
        }

        @Override
        protected void onPostExecute(Person person)
        {
            Toast.makeText(getContext(), person.getFirstName() + " " + person.getLastName(), Toast.LENGTH_LONG).show();
            //LoginFragment loginFragment = new LoginFragment();
            switchFragments();
        }
    }

    public class RegisterTask extends AsyncTask<Void, Boolean, Boolean>
    {
        boolean success = false;
        @Override
        protected Boolean doInBackground(Void... voids) {
            if(hostInput.equals("") || portInput.equals("") || userNameInput.equals("") || passwordInput.equals("") || emailInput.equals("") ||
                    firstNameInput.equals("") || lastNameInput.equals("") || genderInput.equals("")) {
                return false;
                //
            }
            else {
                RegisterRequest registerRequest = new RegisterRequest(userNameInput, passwordInput, emailInput, firstNameInput, lastNameInput, genderInput);
                ServerProxy sp = new ServerProxy();
                RegisterResult registerResult = sp.register(hostInput, portInput, registerRequest);
                Model.instance().setCurrPersonID(registerResult.getPersonID());
                Model.instance().setAuthToken(registerResult.getAuthToken());
                success = sp.isSuccess();
                return true;
            }
            //return null;
        }

        @Override
        protected void onPostExecute(Boolean bool)
        {
            //super.onPostExecute(aVoid);
            if (bool == false)
            {
                Toast.makeText(getContext(), "Registration failed.", Toast.LENGTH_LONG).show();
            }
            if (success == true)
            {
                GetDataTask task = new GetDataTask();
                task.execute();
                //Toast.makeText(getContext(), Model.instance().getUser().getFirstName() + " " + Model.instance().getUser().getLastName(), Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getContext(), "Registration failed.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
