package ru.fotki3D;

//import ru.fotki3D.compoundinterest.R;
import java.util.ArrayList;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewStyle;
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
	private TextView littleText;
	private GraphView graphView;

	private void updateResult() {
		if (annualVal != null && periodVal != null) {
			
			Double y = getCompoundAmount(annualVal, periodVal);
			result.setText(String.format("Коэфф. роста: %.2f", y));
			
			int num = periodVal;
	        GraphViewData[] simpleData = new GraphViewData[num+1];
	        GraphViewData[] compoundData = new GraphViewData[num+1];
	        
	        List<String> horizontalLabels = new ArrayList<String>();
	        for (int i = 0; i<=periodVal; i++)
			{
	        	simpleData[i] = new GraphViewData(i, 1+annualVal/100*i);
	        	compoundData[i] = new GraphViewData(i, getCompoundAmount(annualVal, i));
	        	
	        	horizontalLabels.add(String.valueOf(i));
			}
			
	        graphView.removeAllSeries();
	        graphView.addSeries(new GraphViewSeries(simpleData));
	        graphView.addSeries(new GraphViewSeries(compoundData));
	        
	        graphView.setHorizontalLabels( horizontalLabels.toArray(new String[horizontalLabels.size()]));
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
		littleText = (TextView) findViewById(R.id.textView2);
		

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
		
		graphView.setTitle("График доходности для простого и сложного процента в зависимости от времени");
		graphView.setShowLegend(true);
		GraphViewStyle sdf = graphView.getGraphViewStyle();
		sdf.setTextSize(littleText.getTextSize()-2);
	
		LinearLayout layout = (LinearLayout) findViewById(R.id.rootLinear);
		layout.addView(graphView);
		
		updateResult();
	}
}
