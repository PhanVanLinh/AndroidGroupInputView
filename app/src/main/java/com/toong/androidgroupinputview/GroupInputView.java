package com.toong.androidgroupinputview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by PhanVanLinh on 27/07/2017.
 * phanvanlinh.94vn@gmail.com
 */

public class GroupInputView extends LinearLayout {
    @StringDef({ MyInputType.NUMBER })
    @interface MyInputType {
        String NUMBER = "number";
        String INTEGER = "integer";
    }

    @StringDef({ Rule.NONE_EMPTY })
    @interface Rule {
        String NONE_EMPTY = "none_empty";
        String RANGE = "range";
        String EMAIL = "email";
    }

    private TextView tvTitle;
    private EditText edtInput;
    private TextView tvError;
    private String[] rules;
    private String errorMessage;

    public GroupInputView(Context context) {
        this(context, null);
    }

    public GroupInputView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GroupInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.GroupInputView);
        try {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_group_input_view, this, true);
            tvTitle = (TextView) findViewById(R.id.text_title);
            edtInput = (EditText) findViewById(R.id.edit_input);
            tvError = (TextView) findViewById(R.id.text_error);

            String title = ta.getString(R.styleable.GroupInputView_giv_title);
            String inputHint = ta.getString(R.styleable.GroupInputView_giv_inputHint);
            String inputText = ta.getString(R.styleable.GroupInputView_giv_inputText);
            String inputType = ta.getString(R.styleable.GroupInputView_giv_inputType);
            int maxLength = ta.getInt(R.styleable.GroupInputView_giv_input_maxLength, -1);

            String ruleString = ta.getString(R.styleable.GroupInputView_giv_rule);
            if (ruleString != null) {
                rules = ruleString.split(",");
            }

            tvTitle.setText(title);
            edtInput.setText(inputText);
            edtInput.setHint(inputHint);
            configInputType(inputType);
            configMaxLength(maxLength);
        } finally {
            ta.recycle();
        }

        edtInput.addTextChangedListener(new ValidateTextWatcher());
    }

    private void configInputType(String inputType) {
        if (inputType == null || inputType.isEmpty()) {
            return;
        }
        if (inputType.equals(MyInputType.NUMBER)) {
            edtInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            return;
        }
        if (inputType.equals(MyInputType.INTEGER)) {
            edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
            return;
        }
    }

    private void configMaxLength(int maxLength) {
        if (maxLength != -1) {
            edtInput.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        }
    }

    public String getText() {
        return edtInput.getText().toString();
    }

    public String getErrorMessage() {
        return tvError.getText().toString();
    }

    private class ValidateTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (rules == null || rules.length == 0) {
                return;
            }
            for (int i = 0; i < rules.length; i++) {
                if (rules[i].equals(Rule.NONE_EMPTY)) {
                    if (validateNoneEmpty()) {
                        return;
                    }
                }
                if (rules[i].contains(Rule.RANGE)) {
                    if (validateRange(rules[i])) {
                        return;
                    }
                }
            }
        }
    }

    private boolean validateNoneEmpty() {
        if (edtInput.getText().toString().isEmpty()) {
            errorMessage = getContext().getString(R.string.msg_error_none_empty);
            tvError.setText(errorMessage);
            tvError.setVisibility(VISIBLE);
            return true;
        }
        tvError.setVisibility(GONE);
        return false;
    }

    private boolean validateRange(String ruleRange) {
        ruleRange = ruleRange.replace("range","").replace("(","").replace(")","");
        int minRange = Integer.parseInt(ruleRange.split(":")[0]);
        int maxRange = Integer.parseInt(ruleRange.split(":")[1]);

        if (edtInput.getText().toString().length() < minRange) {
            errorMessage = "hay nhap du";
            tvError.setText(errorMessage);
            tvError.setVisibility(VISIBLE);
            return true;
        }
        if (edtInput.getText().toString().length() > maxRange) {
            errorMessage = "nhap qua";
            tvError.setText(errorMessage);
            tvError.setVisibility(VISIBLE);
            return true;
        }
        tvError.setVisibility(GONE);
        return false;
    }
}
