package com.zemnuhov.stressapp.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zemnuhov.stressapp.R;

public class SourceStressItem extends Fragment {
    private String title;
    private TextView titleStress;
    private DeletedCallBack callBack;
    private ImageView deletedButton;
    private Integer id;
    private Bundle arguments;

    public static SourceStressItem newInstance(String title,Integer id,Bundle arguments) {
        SourceStressItem fragment = new SourceStressItem();
        fragment.title=title;
        fragment.id=id;
        fragment.arguments=arguments;
        return fragment;
    }

    interface DeletedCallBack{
        public void deleted(Integer id);

    }

    public void registerCallBack(DeletedCallBack callBack){
        this.callBack=callBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container
            ,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.settings_source_of_stress,container
                ,false);
        init(view);
        titleStress.setText(title);

        if(arguments!=null){
            titleStress.setOnClickListener(v -> {
                DialogSelectedSource dialog=DialogSelectedSource.newInstance(
                        arguments.getLong("TIME_IN_INTENT")
                        ,title
                        ,arguments.getInt("PEAKS_COUNT_IN_INTENT")
                        ,arguments.getDouble("TONIC_AVG_IN_INTENT")
                );
                dialog.show(getFragmentManager(),"ADD_DIALOG");
            });
        }

        deletedButton.setOnClickListener(v -> callBack.deleted(id));
        return view;
    }

    public String getTitle() {
        return title;
    }

    private void init(View view){
        titleStress=view.findViewById(R.id.title_stress);
        deletedButton=view.findViewById(R.id.deleted_source);
    }


}
