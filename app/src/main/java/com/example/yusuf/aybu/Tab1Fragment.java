package com.example.yusuf.aybu;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class Tab1Fragment extends Fragment {

    private ListView ymkLv;
    private ProgressBar prgsBar;
    private ArrayList<String> ymklstarry;
    private ArrayAdapter adapter;
    private TextView tarih,tv;
    public Tab1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        prgsBar= view.findViewById(R.id.progBar);
        ymkLv = view.findViewById(R.id.yemek_listesi);
        tv= view.findViewById(android.R.id.text1);
        //tv.setTextColor(Color.RED);
        ymkLv.setVisibility(View.INVISIBLE);
        tarih=view.findViewById(R.id.date);
        tarih.setVisibility(View.INVISIBLE);


        new veriCek().execute();

        return view;
    }

    public class veriCek extends AsyncTask<Void,Void,Void> {

        String date;

       /* @Override
        protected void onPreExecute() {

        }
        */
        @Override
        protected Void doInBackground(Void... voids) {
            ymklstarry = new ArrayList<>();
            try {
                Document doc =  Jsoup.connect("http://ybu.edu.tr/sks/").get();
                Elements elements= doc.select("font");
                date= elements.get(0).text();

                for(int i=1;i<=elements.size()-1;i++){
                    String words = elements.get(i).text();
                    ymklstarry.add("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+words);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tarih.setText(date);
            adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, ymklstarry);
            ymkLv.setAdapter(adapter);
            ymkLv.setVisibility(View.VISIBLE);
            prgsBar.setVisibility(View.INVISIBLE);
            tarih.setVisibility(View.VISIBLE);


        }
    }
}

