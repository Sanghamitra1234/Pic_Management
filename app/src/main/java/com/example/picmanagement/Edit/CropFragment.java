package com.example.picmanagement.Edit;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.picmanagement.R;
import com.isseiaoki.simplecropview.CropImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CropFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CropFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    static String photoPath="";
    private String mParam2;

    public CropFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CropFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CropFragment newInstance(String param1, String param2) {
        CropFragment fragment = new CropFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            photoPath = bundle.getString("PHOTO_OPEN_URI");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_crop, container, false);
//        CropImageView mCropView = (CropImageView) view.findViewById(R.id.cropImageView);
//
//        mCropView.load(Uri.parse(photoPath)).execute(mLoadCallback);
        return view;
    }
}