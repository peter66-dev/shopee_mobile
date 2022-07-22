package com.example.myshopee.fragment;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myshopee.ChangeInformationActivity;
import com.example.myshopee.MainActivity;
import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.MyUtils.PreferenceManager;
import com.example.myshopee.R;
import com.google.android.material.textfield.TextInputLayout;

import DAO.AccountDAO;
import Model.User;

public class ProfileFragment extends Fragment {

    private View view;
    private AccountDAO accountDAO;
    private User currentUser;
    private TextView txvUsername;
    private TextView txvBalance;
    private Button btnUserInfo;
    private Button btnRecharge;
    private Button btnLogOut;
    private boolean shouldRefreshOnResume = false;


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
        accountDAO = new AccountDAO(getActivity());
        currentUser = CommonUtils.getCurrentUser(getActivity());
        txvUsername.setText(currentUser.getUserName());
        txvBalance.setText(CommonUtils.getReadableCostFromDouble(currentUser.getBudget()));
        setListeners();
        return view;
    }



    private void setListeners() {
        btnRecharge.setOnClickListener(v -> {
            openRechargeDialog(Gravity.CENTER);
        });
        btnUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangeInformationActivity.class);
            startActivity(intent);
        });

        btnLogOut.setOnClickListener(v -> {
            // Call Preference Manager to clear user's informations from SharedPreference
//            PreferenceManager preferenceManager = new PreferenceManager(getActivity());
//            preferenceManager.clear();
            // Create new intent in order to navigate user to Login view
            Intent intent = new Intent(getActivity(), MainActivity.class);
            // Set Flag to clear all previous activities/fragments, will only start with login view
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void openRechargeDialog(int gravity) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_recharge);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        Button btnCancel = dialog.findViewById(R.id.dialog_recharge_btnCancel);
        Button btnConfirm = dialog.findViewById(R.id.dialog_recharge_btnConfirm);

        btnCancel.setOnClickListener(v -> dialog.dismiss());



        TextInputLayout textInputMoney = dialog.findViewById(R.id.dialog_recharge_textInputMoney);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {

                final CharSequence replacementText = charSequence.subSequence(i, i1);
                int length = replacementText.length();
                if (length != 0) {
                    Log.d(String.valueOf(ProfileFragment.this), "replacementText in filter: " + replacementText);
                    String charSequenceToString = replacementText.toString();
                    if (Character.compare(charSequenceToString.charAt(length - 1), 'đ') == 0) {
                        charSequenceToString = charSequenceToString.substring(0, length - 1);
                        charSequenceToString = charSequenceToString.replace(",", "");

                        if (Double.parseDouble(charSequenceToString) > 10000000) {
                            charSequenceToString = charSequenceToString.substring(0, charSequenceToString.length() - 1);
                            charSequenceToString = CommonUtils.getReadableCostFromDouble(Double.parseDouble(charSequenceToString)) + "đ";

                            // Show error because your maximum limit is 10 milion
                            textInputMoney.setErrorEnabled(true);
                            textInputMoney.setError("Hạn mức tối đa của bạn là 10.000.000");
                            btnConfirm.setEnabled(false);
                            return charSequenceToString;
                        }
                    }
                }
                if (textInputMoney.isErrorEnabled()) {
                    textInputMoney.setErrorEnabled(false);
                }
                btnConfirm.setEnabled(true);
                return null;
            }
        };

        btnConfirm.setOnClickListener(v -> {
            // Handle recharge for user
            String getBudget = textInputMoney.getEditText().getText().toString();
            getBudget = getBudget.substring(0, getBudget.length() - 1);
            getBudget = getBudget.replace(",", "");
            Double addBudge = Double.valueOf(getBudget);
            User budgetChanged = accountDAO.moneyRecharge(currentUser, addBudge);
            if (budgetChanged != null) {
                currentUser = budgetChanged;
                txvBalance.setText(CommonUtils.getReadableCostFromDouble(currentUser.getBudget()));
                Toast.makeText(getActivity(), "Nạp tiền thành công!", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

        });

        textInputMoney.getEditText().addTextChangedListener(new TextWatcher() {
            boolean ignoreChange = false;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(String.valueOf(ProfileFragment.this), "CharSequence on text changed: " + charSequence);

                if (!ignoreChange) {
                    String getInput = charSequence.toString();
                    Log.d(String.valueOf(ProfileFragment.this), "On text changed: " + getInput);

                    getInput = getInput.replace(",", "");
                    int length = getInput.length();
                    if (length > 1 && Character.compare(getInput.charAt(length - 1), 'đ') == 0) {
                        getInput = getInput.substring(0, length - 1);
                        String formatted = CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput)) + "đ";
                        Log.d(String.valueOf(ProfileFragment.this), "formatted: " + formatted);

                        ignoreChange = true;
                        textInputMoney.getEditText().setText(formatted);
                        length = textInputMoney.getEditText().getText().length();
                        textInputMoney.getEditText().setSelection(length - 1);
                        ignoreChange = false;
                    } else if (length == 1 && Character.compare(getInput.charAt(length - 1), 'đ') == 0) {
                        ignoreChange = true;
                        textInputMoney.getEditText().setText("");
                        ignoreChange = false;
                    } else {
                        ignoreChange = true;
                        textInputMoney.getEditText().setText(CommonUtils.getReadableCostFromDouble(Double.parseDouble(getInput)) + "đ");
                        textInputMoney.getEditText().setSelection(textInputMoney.getEditText().length() - 1);
                        ignoreChange = false;
                    }


                }

                textInputMoney.getEditText().setFilters(new InputFilter[]{filter});

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRefreshOnResume = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shouldRefreshOnResume) {
            currentUser = CommonUtils.getCurrentUser(getActivity());
            txvUsername.setText(currentUser.getUserName());
            txvBalance.setText(CommonUtils.getReadableCostFromDouble(currentUser.getBudget()));
            shouldRefreshOnResume = false;
        }
    }
}
