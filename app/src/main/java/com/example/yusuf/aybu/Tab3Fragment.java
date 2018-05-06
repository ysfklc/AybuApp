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

public class Tab3Fragment extends Fragment{

    public Tab3Fragment() {
    }

    private ListView newsListView;
    private TextView news_text;
    private ProgressBar progressBarForNews;
    private ArrayList<String> newsList;
    private ArrayList<String> newsLink;
    private ArrayAdapter h_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment, container, false);

        progressBarForNews= view.findViewById(R.id.hbr_Bar);
        news_text= view.findViewById(R.id.News_type);
        news_text.setVisibility(View.INVISIBLE);
        newsListView = view.findViewById(R.id.haber_listem);
        newsListView.setVisibility(View.INVISIBLE);



        new haberCek().execute();
        return view;
    }
    public class haberCek extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            newsList= new ArrayList<>();
            newsLink=new ArrayList<>();
            try {
                Document doc =  Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements element= doc.select("div.contentNews").select("div.cncItem");
                Elements newsurl= doc.select("div.contentNews").select("div.cncItem").select("a");
                for(int i=0;i<=element.size()-1;i++){
                    String words = element.get(i).text();
                    newsList.add(words);
                }
                for(int i=0;i<=newsurl.size()-1;i++){
                    String hrefs = newsurl.attr("href");
                    newsLink.add(hrefs);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            h_adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1, newsList);
            newsListView.setAdapter(h_adapter);
            newsListView.setVisibility(View.VISIBLE);
            progressBarForNews.setVisibility(View.INVISIBLE);
            news_text.setVisibility(View.VISIBLE);
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (!newsLink.get(i).startsWith("http://www.ybu.edu.tr/muhendislik/bilgisayar/") && !newsLink.get(i).startsWith("http://www.ybu.edu.tr/muhendislik/bilgisayar/")){
                        String url = "http://www.ybu.edu.tr/muhendislik/bilgisayar/" + newsLink.get(i);
                        Intent internetBrow = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        getActivity().startActivity(internetBrow);
                    }

                }
            });

        }
    }
}
