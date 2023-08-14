package org.chakuy.chakuy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.chakuy.chakuy.R;
import org.chakuy.chakuy.model.ChakuyModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChakuyAdapter extends FirestoreRecyclerAdapter<ChakuyModel,ChakuyAdapter.ViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChakuyAdapter(@NonNull FirestoreRecyclerOptions<ChakuyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull ChakuyModel HbelgaModel) {
        viewHolder.name.setText(HbelgaModel.getNombre());
        viewHolder.description.setText(HbelgaModel.getdescripcion());
        viewHolder.state.setText(HbelgaModel.gettipo());
        viewHolder.dateb.setText(HbelgaModel.getfecha());
        viewHolder.areab.setText(HbelgaModel.getubicacion());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewch_chakuy, parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, state, dateb, areab,tipo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nombrepost);
            description = itemView.findViewById(R.id.telefonopost);
             tipo = itemView.findViewById(R.id.tipopost);
            dateb = itemView.findViewById(R.id.fechanacimientopost);
            areab = itemView.findViewById(R.id.ciudadpost);

        }
    }
}

