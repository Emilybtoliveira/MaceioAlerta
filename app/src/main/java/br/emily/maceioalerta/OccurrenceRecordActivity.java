package br.emily.maceioalerta;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import br.emily.maceioalerta.objects.Occurrence;

public class OccurrenceRecordActivity extends AppCompatActivity {

    private static final String[] OCCURRENCE_TYPES = new String[] {
            "Roubo ou Furto de Veículos",
            "Furto de Documentos/Celular/Placa de Veículo/Bicicleta",
            "Perda de Documentos/Celular/Placa de Veículo",
            "Roubo de Documentos, Celulares e/ou Objetos",
            "Injúria, Calúnia ou Difamação",
            "Acidente de Trânsito Sem Vítimas",
            "Desaparecimento de Pessoas",
            "Encontro de Pessoas",
            "Furto de Fios/Cabos em vias públicas (somente para empresas concessionárias)",
            "DEPA - Proteção Animal"
    };

    private AutoCompleteTextView mOccurrenceTypeInput;
    private TextInputEditText mOccurrenceStreetInput;
    private TextInputEditText mOccurrenceNeighborhoodInput;
    private TextInputEditText mOccurrenceDescriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occurrence_record);

        this.mOccurrenceTypeInput = findViewById(R.id.occurrencerecord_types);
        this.mOccurrenceStreetInput = findViewById(R.id.occurrencerecord_street);
        this.mOccurrenceNeighborhoodInput = findViewById(R.id.occurrencerecord_neighborhood);
        this.mOccurrenceDescriptionInput = findViewById(R.id.occurrencerecord_description);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, OCCURRENCE_TYPES);
        this.mOccurrenceTypeInput.setAdapter(adapter);

        MaterialButton mOccurrenceRecordButton = findViewById(R.id.occurrencerecord_record);
        mOccurrenceRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });
    }

    private void record() {
        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference("occurrences");

        String occurrenceType = this.mOccurrenceTypeInput.getText().toString();
        String occurrenceStreet = Objects.requireNonNull(this.mOccurrenceStreetInput.getText())
                .toString();
        String occurrenceNeighborhood = Objects.requireNonNull(this.mOccurrenceNeighborhoodInput
                .getText()).toString();
        String occurrenceDescription = Objects.requireNonNull(this.mOccurrenceDescriptionInput
                .getText()).toString();

        if (Objects.equals(occurrenceType, "")) {
            Toast.makeText(getApplicationContext(), R.string.occurrence_type_empty,
                    Toast.LENGTH_SHORT).show();
        } else {
            if (Objects.equals(occurrenceNeighborhood, "")) {
                Toast.makeText(getApplicationContext(), R.string.occurrence_neighborhood_empty,
                        Toast.LENGTH_SHORT).show();
            } else {
                if (Objects.equals(occurrenceDescription, "")) {
                    Toast.makeText(getApplicationContext(), R.string.occurrence_description_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    reference.push().setValue(new Occurrence(occurrenceType,
                                    occurrenceStreet, occurrenceNeighborhood,
                                    occurrenceDescription),
                            new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(
                                        @Nullable DatabaseError databaseError,
                                        @NonNull DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Toast.makeText(getApplicationContext(),
                                                R.string.occurrence_error,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                R.string.occurrence_saved,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    finish();
                }
            }
        }
    }
}
