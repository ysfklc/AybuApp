package com.example.yusuf.aybu;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class Tab2Fragment extends Fragment{

    public Tab2Fragment() {
    }

    private ListView announceListView;
    private TextView announe_text;
    private ProgressBar progressBarForAnnounce;
    private ArrayList<String> announceList;
    private ArrayList<String> announceLink;
    private ArrayAdapter d_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        progressBarForAnnounce= view.findViewById(R.id.duy_Bar);
        announe_text = view.findViewById(R.id.Announce_type);
        announe_text.setVisibility(View.INVISIBLE);
        announceListView = view.findViewById(R.id.duyuru_listem);
        announceListView.setVisibility(View.INVISIBLE);



        new duyuruCek().execute();
        return view;
    }

    public class duyuruCek extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            announceList= new ArrayList<>();
            announceLink= new ArrayList<>();
            try {
                Document doc =  Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements element= doc.select("div.contentAnnouncements").select("div.cncItem");
                Elements linkElement = doc.select("div.contentAnnouncements").select("div.cncItem").select("a");
                for(int i=0;i<=element.size()-1;i++){
                    String words = element.get(i).text();
                    announceList.add(words);
                }
                for(int i=0;i<=linkElement.size()-1;i++){
                    String href_announce = linkElement.attr("href");
                    announceLink.add(href_announce);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            d_adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, announceList);
            announceListView.setAdapter(d_adapter);
            announceListView.setVisibility(View.VISIBLE);
            progressBarForAnnounce.setVisibility(View.INVISIBLE);
            announe_text.setVisibility(View.VISIBLE);
            announceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (!announceLink.get(i).startsWith("http://www.ybu.edu.tr/muhendislik/bilgisayar/") && !announceLink.get(i).startsWith("http://www.ybu.edu.tr/muhendislik/bilgisayar/")){
                        String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar/" + announceLink.get(i);
                        Intent internetBrow = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        getActivity().startActivity(internetBrow);
                    }

                }
            });
        }
    }
}
