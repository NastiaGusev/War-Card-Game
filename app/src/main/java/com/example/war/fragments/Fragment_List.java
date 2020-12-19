package com.example.war.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.war.utils.CallBack_List;
import com.example.war.R;
import com.example.war.objects.TopTen;
import com.example.war.utils.MySP;
import com.example.war.utils.RecordListAdapter;
import com.google.gson.Gson;

public class Fragment_List extends Fragment {

    private ListView list_LSTV_list;
    private TopTen topScores;
    private CallBack_List callBack_List;

    public void setCallBack_List(CallBack_List callBack_List){
        this.callBack_List = callBack_List;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        showList();
        initView();
        return view;
    }

    private void findViews(View view) {
        list_LSTV_list = view.findViewById(R.id.list_LSTV_list);
    }

    private void initView(){
        list_LSTV_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBack_List.sendIdRecord(position);
            }
        });
    }

    private void getTopTenList(){
        String stringTopScores = MySP.getInstance().getString(MySP.KEYS.KEY_TOP_TEN_SCORES, null);
        if (stringTopScores == null) {
            topScores = new TopTen();
        } else {
            topScores = new Gson().fromJson(stringTopScores, TopTen.class);
        }
    }

    private void showList(){
        getTopTenList();
        RecordListAdapter adapter = new RecordListAdapter(this.getActivity(), R.layout.listview_adapter, topScores.getRecords());
        list_LSTV_list.setAdapter(adapter);
    }
}
