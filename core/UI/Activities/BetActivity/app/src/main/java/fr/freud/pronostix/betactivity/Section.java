package fr.freud.pronostix.betactivity;

import android.view.View;
import android.widget.TextView;

import fr.freud.pronostix.indexedlistview.adapter.Row;


public class Section extends Row {
	
	private final String text;
	private static final int textViewID = R.id.textView1;

    public Section(String text) {
        this.text = text;
    }

	public void inflateView(View view) {
		TextView textView = (TextView) view.findViewById(textViewID);
		textView.setText(text);
	}

}
