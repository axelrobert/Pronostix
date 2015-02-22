package indexedlistview.adapter;

import java.util.List;

import fr.freud.expandedlistview.AnimatedExpandableListView;

/**
 * Created by François_2 on 19/10/2014.
 */
public abstract class IndexBaseAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    protected List<Row> mRows;

    public void setRows(List<Row> _rows) {
        mRows = _rows;
    }
}
