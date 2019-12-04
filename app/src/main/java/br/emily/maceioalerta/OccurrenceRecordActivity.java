package br.emily.maceioalerta;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class OccurrenceRecordActivity extends AppCompatActivity {

    private static class Occurrence {

        private String type;
        private String street;
        private String neighborhood;
        private String description;

        Occurrence(String type, String street, String neighborhood, String description) {
            this.type = type;
            this.street = street;
            this.neighborhood = neighborhood;
            this.description = description;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStreet() {
            return this.street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getNeighborhood() {
            return this.neighborhood;
        }

        public void setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("occurrences");

        String occurrenceType = this.mOccurrenceTypeInput.getText().toString();
        String occurrenceStreet = Objects.requireNonNull(this.mOccurrenceStreetInput.getText()).toString();
        String occurrenceNeighborhood = Objects.requireNonNull(this.mOccurrenceNeighborhoodInput.getText()).toString();
        String occurrenceDescription = Objects.requireNonNull(this.mOccurrenceDescriptionInput.getText()).toString();

        reference.push().setValue(new Occurrence(occurrenceType,
                occurrenceStreet, occurrenceNeighborhood, occurrenceDescription));
    }
}
