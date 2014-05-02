package ru.fotki3D;

//import ru.fotki3D.compoundinterest.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_main);
	// }

	private EditText annual;
	private Double annualVal=12.0;
	private EditText period;
	private Integer periodVal=5;
	private TextView result;
	private GraphView graphView;

	private void updateResult() {
		if (annualVal != null && periodVal != null) {
			
			Double y = getCompoundAmount(annualVal, periodVal);
			result.setText(String.format("�����. �����: %.2f", y));
			
			int num = periodVal;
	        GraphViewData[] simpleData = new GraphViewData[num];
	        GraphViewData[] compoundData = new GraphViewData[num];
	        
	        for (int i = 1; i<=periodVal; i++)
			{
	        	simpleData[i-1] = new GraphViewData(i, 1+annualVal/100*i);
	        	compoundData[i-1] = new GraphViewData(i, getCompoundAmount(annualVal, i));
			}
			
	        graphView.removeAllSeries();
	        graphView.addSeries(new GraphViewSeries(simpleData));
	        graphView.addSeries(new GraphViewSeries(compoundData));
	        graphView.setVisibility(View.VISIBLE);
		}
		else
		{
			result.setText("");
			graphView.removeAllSeries();
			graphView.setVisibility(View.INVISIBLE);
		}
	}

	private double getCompoundAmount(Double annual, Integer period) {
		return Math.pow(1.0 + annual / 100, period);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		annual = (EditText) findViewById(R.id.editText1);
		period = (EditText) findViewById(R.id.editText2);
		result = (TextView) findViewById(R.id.textView3);

		result.setText("");

		period.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null) {
					if (s.length() == 0)
					{
						periodVal = null;
					}
					else
					{
					periodVal = Integer.parseInt(s.toString());
					}
					updateResult();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		annual.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null) {
					if (s.length() == 0)
					{
						annualVal = null;
					}
					else
					{
					annualVal = Double.parseDouble(s.toString());
					}
					updateResult();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		
		// graph with dynamically genereated horizontal and vertical labels
		graphView = new LineGraphView(
				MainActivity.this, "GraphViewDemo" // heading
		);
	
		LinearLayout layout = (LinearLayout) findViewById(R.id.rootLinear);
		layout.addView(graphView);
		
		updateResult();
	}
}
