package com.cursoandroid.fragmentcommunicatortest;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.cursoandroid.fragmentcommunicatortest.fragments.CadastroFragment;
import com.cursoandroid.fragmentcommunicatortest.fragments.LoginFragment;

public class MainActivity extends FragmentActivity implements CadastroFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener{

    private Button botaoSubmit;
    private Boolean status = true;

    LoginFragment loginFragment = new LoginFragment();
    CadastroFragment cadastroFragment = new CadastroFragment();

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        managerFragmentTransaction(R.id.container1, loginFragment);
        managerFragmentTransaction(R.id.container2, cadastroFragment);

        botaoSubmit = findViewById(R.id.bt_submit);
        botaoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( status ){
                    loginFragment.onButtonPressed();
                    botaoSubmit.setText("Submit to login");

                    if (fragmentTransaction.isEmpty()) {
                        fragmentTransaction.add(R.id.container1, loginFragment);
                    } else {
                        fragmentTransaction.replace(R.id.container1, loginFragment);
                        fragmentTransaction.addToBackStack(null);
                    }

                    status = false;

                } else{
                    cadastroFragment.onButtonPressed();
                    botaoSubmit.setText("Submit to cadastro");

                    if(fragmentTransaction.isEmpty()){
                        fragmentTransaction.add(R.id.container2, cadastroFragment);
                    } else {
                        fragmentTransaction.replace(R.id.container2, cadastroFragment);
                        fragmentTransaction.addToBackStack(null);
                    }

                    status = true;
                }

            }
        });

        fragmentTransaction.commit();

    }

    //Bind all data with all yours fragments here
    @Override
    public void onFragmentInteraction() {
        //cadastroFragment received param 1 of loginFragment
        cadastroFragment.setmParam1(loginFragment.getmParam1());

        //loginFragment received param 2 of cadastroFragment
        loginFragment.setmParam2(cadastroFragment.getmParam2());
    }

    private FragmentTransaction managerFragmentTransaction (int idContainer, Fragment fragment){
        return fragmentTransaction.add(idContainer, fragment);
    }

}
