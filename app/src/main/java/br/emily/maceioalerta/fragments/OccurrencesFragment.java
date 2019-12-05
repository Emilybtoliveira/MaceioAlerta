package br.emily.maceioalerta.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import br.emily.maceioalerta.OccurrenceRecordActivity;
import br.emily.maceioalerta.R;
import br.emily.maceioalerta.objects.Occurrence;

public class OccurrencesFragment extends Fragment {

    public OccurrencesFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View of = inflater.inflate(R.layout.fragment_occurrences, container, false);

        MaterialButton mOccurrenceRecord = of.findViewById(
                R.id.occurrence_record);
        mOccurrenceRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OccurrenceRecordActivity.class));
            }
        });

        return of;
    }
}


