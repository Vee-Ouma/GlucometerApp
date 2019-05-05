package com.example.glucometer.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.glucometer.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EntryDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntryDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDialogFragment extends DialogFragment {



    public static final int REQUEST_CODE = 0;
    public static final String GLUCOSE_TEXT_BUNDLE_KEY = "glucose_text";

    private EditText glucoseText;
    private EditText bolusText;
    private EditText carbsText;
    private EditText notesText;
    private TimePicker timePicker;
    private Button submitButton;
    private final Calendar calendar = Calendar.getInstance();

    private OnFragmentInteractionListener mListener;

    public EntryDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntryDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntryDialogFragment newInstance(String param1, String param2) {
        EntryDialogFragment fragment = new EntryDialogFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText datePicker = (EditText) view.findViewById(R.id.datePicker);
        InputFilter[] lengthFiltersThree = {new InputFilter.LengthFilter(3)};
        glucoseText = view.findViewById(R.id.glucose_text);
        glucoseText.setFilters(lengthFiltersThree);
        submitButton = view.findViewById(R.id.submit_button);
        bolusText = view.findViewById(R.id.edit_bolus);
        bolusText.setFilters(lengthFiltersThree);
        carbsText = view.findViewById(R.id.edit_carbs);
        carbsText.setFilters(lengthFiltersThree);
        notesText = view.findViewById(R.id.edit_notes);
        timePicker = view.findViewById(R.id.timePicker);
        datePicker.setText(new SimpleDateFormat("EEE, MMMM d").format(calendar.getInstance().getTime()));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", view.toString());
                onSubmitPressed(view);
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("time", dayOfMonth + "");
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                datePicker.setText(new SimpleDateFormat("EEE, MMMM d").format(calendar.getTime()));
            }

        };

        datePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry_dialog, container, false);
    }

    public void onSubmitPressed(View view) {
        if (mListener != null) {
            //Editable glucose_text = view.findViewById(R.id.glucose_text);
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            mListener.onSubmission(glucoseText.getText().toString(),
                    calendar.getTime(),
                    carbsText.getText().toString(),
                    bolusText.getText().toString(),
                    "",
                    notesText.getText().toString());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onSubmission(String glucose, Date date, String carbs, String bolus, String basal, String notes);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

}
