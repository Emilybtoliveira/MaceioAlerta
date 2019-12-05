package br.emily.maceioalerta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import br.emily.maceioalerta.R;
import br.emily.maceioalerta.SignInHubActivity;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View pf = inflater.inflate(R.layout.fragment_profile, container, false);

        MaterialButton mSignOut = pf.findViewById(R.id.sign_out);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Objects.requireNonNull(getActivity()).finish();
                startActivity(new Intent(getActivity(), SignInHubActivity.class));
            }
        });

        return pf;
    }
}


