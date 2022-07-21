package com.example.myshopee.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.R;
import com.google.android.material.textfield.TextInputLayout;

public class InfomationFragment extends Fragment {

    private View view;
    private TextView txvUsername;
    private TextView txvBalance;
    private Button btnUserInfo;
    private Button btnRecharge;
    private Button btnLogOut;

    private void mapping(View view) {
        txvUsername = view.findViewById(R.id.txvUsername);
        txvBalance = view.findViewById(R.id.txvBalance);
        btnUserInfo = view.findViewById(R.id.btnUserInfo);
        btnRecharge = view.findViewById(R.id.btnRecharge);
        btnLogOut = view.findViewById(R.id.btnLogOut);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        mapping(view);
        setListeners();
        return view;
    }

    private void setListeners() {
        btnRecharge.setOnClickListener(v -> {
            openRechargeDialog(Gravity.CENTER);
        });
    }

    private void openRechargeDialog(int gravity) {
        double money = 0;

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_recharge);

        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        TextInputLayout textInputMoney = dialog.findViewById(R.id.dialog_recharge_textInputMoney);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                String currentTextInput = textInputMoney.getEditText().getText().toString().trim();
                Log.d(String.valueOf(InfomationFragment.this), "Current Text in filter: " + currentTextInput);
                String charSequenceToString = charSequence.toString();
                charSequenceToString = charSequenceToString.replace(",", "");

                int length = charSequenceToString.length();
                Log.d(String.valueOf(InfomationFragment.this), "Character is being filtered: " + charSequenceToString);
                if(Character.compare(charSequenceToString.charAt(length - 1), 'đ') == 0) {
                    charSequenceToString = charSequenceToString.substring(0, length - 1);
                }

                if(Double.parseDouble(charSequenceToString) > 10000000) {
                    return charSequenceToString.substring(0, length - 3) + "đ";
                }
                return null;
//
//                currentTextInput = currentTextInput.replace(",", "");
//                currentTextInput = currentTextInput.substring(0, currentTextInput.length() - 1);
////                charSequenceToString = "" + charSequenceToString.charAt(charSequence.length() - 2);
//                String textAfterChanged = currentTextInput + "" + charSequenceToString;
//                Double value = Double.parseDouble(textAfterChanged);
//                if(value > 0) {
//                    Log.d(String.valueOf(InfomationFragment.this), CommonUtils.getReadableCostFromDouble(value));
//                }
//                Log.d(String.valueOf(InfomationFragment.this), CommonUtils.getReadableCostFromDouble(value));
            }
        };

        textInputMoney.getEditText().addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(String.valueOf(InfomationFragment.this), "CharSequence on text changed: " + charSequence);

                if(!ignoreChange){
                    String getInput = charSequence.toString();
                    Log.d(String.valueOf(InfomationFragment.this), "On text changed: " + getInput);

                    getInput = getInput.replace(",", "");
                    int length = getInput.length();
                    if(length > 1 && Character.compare(getInput.charAt(length - 1), 'đ') == 0) {
                        getInput = getInput.substring(0, length - 1);
                        String formatted = CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput)) + "đ";
                        ignoreChange = true;
                        textInputMoney.getEditText().setText(formatted);
                        textInputMoney.getEditText().setSelection(formatted.length() - 1);
                        ignoreChange = false;
                    }
                    else if(length == 1 && Character.compare(getInput.charAt(length - 1), 'đ') == 0) {
                        ignoreChange = true;
                        textInputMoney.getEditText().setText("");
                        ignoreChange = false;
                    }
                    else {
                        ignoreChange = true;
                        textInputMoney.getEditText().setText(CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput)) + "đ");
                        textInputMoney.getEditText().setSelection(textInputMoney.getEditText().length() - 1);
                        ignoreChange = false;
                    }


                }

                textInputMoney.getEditText().setFilters(new InputFilter[] {filter});

//                if(length > 0 && Character.compare(getInput.charAt(length - 1),'đ') == 0) {
//                    getInput = getInput.substring(0, length - 1);
//                    String formatted = CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput));
////                    formatted += "đ";
//                    textInputMoney.getEditText().setText(formatted);
//                    textInputMoney.getEditText().setSelection(formatted.length() - 1);
//                }
//                else if(length > 0 && Character.compare(getInput.charAt(length - 1), 'đ') != 0 ) {
//                    String formatted = CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput));
////                    formatted += "đ";
//                    textInputMoney.getEditText().setText(formatted);
//                    textInputMoney.getEditText().setSelection(formatted.length() - 1);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button btnCancel = dialog.findViewById(R.id.dialog_recharge_btnCancel);
        Button btnConfirm = dialog.findViewById(R.id.dialog_recharge_btnConfirm);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnConfirm.setOnClickListener(v -> {
            // Handle recharge for user
            dialog.dismiss();
        });

        dialog.show();
    }
}
