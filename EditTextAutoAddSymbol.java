import java.util.regex.Pattern;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * EditText回车生成所需符号分隔
 * 
 */
public class EditTextAutoAddSymbol implements TextWatcher, OnEditorActionListener {

	private int length;
	private String preStr = "";
	private EditText mEditText;
	private int onceTextLenght;// 每个所输各单元的限制长度
	private String symbol;// 符号
	private Context ctx;

	public EditTextAutoAddSymbol(Context ctx, EditText mEditText, String symbol, int length) {
		this.ctx = ctx;
		this.mEditText = mEditText;
		this.onceTextLenght = length;
		this.symbol = symbol;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		String string = s.toString();
		if (!TextUtils.isEmpty(string)) {
			if (length > s.toString().length() && isPreFenhao()) {
				String substring = string.substring(string.length() - 1, string.length());
				if (!substring.equals(symbol)) {
					if (string.contains(symbol)) {
						String substring2 = string.substring(0, string.lastIndexOf(symbol) + 1);
						mEditText.setText(substring2);
						mEditText.setSelection(mEditText.getText().toString().length());
					} else {
						mEditText.setText("");
					}
				}
			} else if (length < string.length()) {
				if (string.matches(".+\\;.+\\;.+\\;.+")) {// 这里自行修改符号分隔
					mEditText.setText(string.substring(0, string.lastIndexOf(symbol) + 1));
					mEditText.setSelection(mEditText.getText().toString().length());
				} else if ((string.lastIndexOf(symbol) != (string.length() - 1))) {
					String temp = string;
					if (string.contains(symbol)) {
						int start = string.lastIndexOf(symbol) + 1;
						if (string.length() > start) {
							temp = string.substring(start);
						}
					}
					if (temp.length() > onceTextLenght) {
						mEditText.setText(string.substring(0, string.length() - (temp.length() - onceTextLenght)));
						mEditText.setSelection(mEditText.getText().toString().length());
					}
				}
			}
		}
		length = mEditText.getText().toString().length();
		preStr = mEditText.getText().toString();
	}

	private boolean isPreFenhao() {
		return preStr.lastIndexOf(symbol) == (preStr.length() - 1);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			String string = mEditText.getText().toString();
			if (!TextUtils.isEmpty(string)) {
				if (checkName2(string.replace(";", ""))) {
					if (string.endsWith(";")) {

					} else {
						mEditText.setText(string + ";");
					}
					mEditText.setSelection(mEditText.getText().toString().length());
				} else {
					// ToastUtils.showMsg(mActThis, "只能是汉字数字英文");
				}
			}
		}
		return false;
	}

	/**
	 * 检查只能是英文汉字数字
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkName2(String name) {
		boolean result = false;
		String checkName = "^[\u4e00-\u9fa5a-zA-Z0-9]+$";
		if (Pattern.compile(checkName).matcher(name).matches()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
