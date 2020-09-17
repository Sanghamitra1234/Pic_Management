package com.example.picmanagement.Edit;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.picmanagement.Photo.PhotoActivity;
import com.example.picmanagement.Photo.PhotoAdapter;
import com.example.picmanagement.Photos;
import com.example.picmanagement.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    static String photoPath="";
    static int position=0;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            position=bundle.getInt("PHOTO_URI_POSITION");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imageView=(ImageView)view.findViewById(R.id.editPhotoId);
        ArrayList<Photos> imgList=PhotoActivity.getPhotoArray();

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {

                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                if(position<imgList.size() && position>0){
                                    photoPath= String.valueOf(imgList.get(position+1).uri);
                                    position=position+1;
                                    Glide.with(view.getContext())
                                            .load(photoPath)
                                            .skipMemoryCache(true)
                                            .into(imageView);
                                }else{
                                    Toast.makeText(getContext(),"No more pictures !! ",Toast.LENGTH_SHORT).show();
                                }
                                Log.i("swipe", "Right to Left");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                if(position<imgList.size() && position>0){
                                    photoPath= String.valueOf(imgList.get(position-1).uri);
                                    position=position-1;
                                    Glide.with(view.getContext())
                                            .load(photoPath)
                                            .skipMemoryCache(true)
                                            .into(imageView);
                                }else{
                                    Toast.makeText(getContext(),"No more pictures !! ",Toast.LENGTH_SHORT).show();
                                }
                                Log.i("swipe", "Left to Right");
                               // titles.showDetails(getPosition() - 1);
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gesture.onTouchEvent(event);
                return true;
            }
        });
        Glide.with(view.getContext())
                .load(photoPath)
                .skipMemoryCache(true)
                .into(imageView);

        return view;
    }

}