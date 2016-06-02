package com.mingle.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mingle.myapplication.model.SharedPreferenceUtil;
import com.mingle.myapplication.severcall.Servercall;

/**
 * Created by multimedia on 2016-06-01.
 */
public class MessageDialog extends android.app.DialogFragment{

    public EditText editContext;

    public MessageDialog() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_message_dialog, null);
        editContext=(EditText)view.findViewById(R.id.context);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Servercall servercall = new Servercall();
                                //SharedPreferenceUtil.putSharedPreference(getActivity(), "UserNickname", editContext.getText().toString()); //닉네임값을 저장.
                                String userId = SharedPreferenceUtil.getSharedPreference(getActivity(), "UserNickname", "");
                                String userContext = editContext.getText().toString();
                                String sectorId;
                                if(SharedPreferenceUtil.getSharedPreference(getActivity(), "SectorId", "").equals("exhibition")) {
                                    sectorId = "exhibition";
                                }
                                else if(SharedPreferenceUtil.getSharedPreference(getActivity(), "SectorId", "").equals("cinema")) {
                                    sectorId = "cinema";
                                }
                                else if(SharedPreferenceUtil.getSharedPreference(getActivity(), "SectorId", "").equals("library")) {
                                    sectorId = "library";
                                }
                                else {
                                    sectorId = "Custom";
                                }
                                servercall.sendMessagetoServer(getActivity(), userId, userContext, sectorId); //불편사항 서버에 보내기.
                            }
                        }).setNegativeButton("취소", null);
        return builder.create();
    }
}
