package com.example.keyborad;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class helloIme extends InputMethodService {
	private List<String> suggestionlist;
	private List<String> suggestionlist1;
	private Hashtable<String, List<String>> data; 
	private CandidateView mCandView;
	private int mCursorSwitch =0;
	int mSelectedIndex = 0;
	public void onInitializeInterface() { 
		suggestionlist= new ArrayList<String>();
		suggestionlist1= new ArrayList<String>();
		suggestionlist.add("1233456789100");
		suggestionlist.add("1123145");
	
	}
	
	public View onCreateInputView() {
		Log.i("**************onCreateInputView", "执行的次数");
		mCandView = new CandidateView(this);
		mCandView.setService(this);
		return mCandView;
	}


@Override
public void onStartInputView(EditorInfo info, boolean restarting) {
	// TODO Auto-generated method stub
	super.onStartInputView(info, restarting);
	mCandView.setSuggestions(suggestionlist);
}

	public void pickSuggestionManually(int mSelectedIndex) {
		getCurrentInputConnection().commitText(
				suggestionlist.get(mSelectedIndex), 1); 
		setCandidatesViewShown(false);
	}

	class CandidateView extends RelativeLayout {
		TextView tv; 
		helloIme listener; 
		List<String> suggestions;
		

		public CandidateView(Context context) {
			super(context);
			tv = new TextView(context);
			RelativeLayout.LayoutParams lpCenter = new RelativeLayout.LayoutParams(
					200, ViewGroup.LayoutParams.WRAP_CONTENT);
			lpCenter.addRule(RelativeLayout.CENTER_IN_PARENT);
			addView(tv, lpCenter);
			tv.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					
				}
			});
		}

		public void setService(helloIme listener) {
			this.listener = listener;
		}

		
		public void setSuggestions(List<String> suggestion) {	
			this.suggestions = suggestion;
			tv.setText(suggestions.get(mSelectedIndex));
			listener.pickSuggestionManually(mSelectedIndex);
			if(mCursorSwitch==0) {
				switchNext();
				mSelectedIndex=mSelectedIndex+1;
				mCursorSwitch = 1;
			}
			
			
		}
	}
	
	public void switchNext() {
		final int keyCode = KeyEvent.KEYCODE_TAB;
		
		new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {

					Instrumentation inst = new Instrumentation();
					// inst.sendKeyDownUpSync(keyCode);
					helloIme.this.sendDownUpKeyEvents(KeyEvent.KEYCODE_TAB);
					helloIme.this.sendDownUpKeyEvents(KeyEvent.KEYCODE_TAB);
					
				} catch (Exception e) {

				}
			}
		}).start();
	
	
		
//		if(mCursorSwitch == 1){
//			pickSuggestionManually(1);	
//			mCursorSwitch =0;
//		}
		
		
		
		new Handler().postDelayed(new Runnable() {
			public void run() {
//				pickSuggestionManually(1);
				helloIme.this.sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
				mCursorSwitch = 0;
				mSelectedIndex=mSelectedIndex-1;
				System.out.println("min.zhu mCursorSwitch = 0;");
			}
		}, 2000);	
		
		
		 
	}


}
