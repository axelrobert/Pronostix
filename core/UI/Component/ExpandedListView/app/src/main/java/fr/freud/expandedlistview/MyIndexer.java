package fr.freud.expandedlistview;

import java.util.ArrayList;
import java.util.List;

import indexedlistview.adapter.Row;
import indexedlistview.index.Indexer;

/**
 * Created by François_2 on 22/12/2014.
 */
public class MyIndexer extends Indexer {

    private List<String> collection;

    public void setCollection(List<String> _collection) {
        collection = new ArrayList<String>(_collection);
    }

    public List<String> getCollection() {
        return collection;
    }

    @Override
    public List<Row> buildAlphabet() {
        List<Row> rows = new ArrayList<>();
        for (String item : collection) {
            rows.add(new ;
        }

        return null;
    }
}
