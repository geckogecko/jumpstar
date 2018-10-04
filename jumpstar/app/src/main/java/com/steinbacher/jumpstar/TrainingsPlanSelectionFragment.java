package com.steinbacher.jumpstar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TrainingsPlanSelectionFragment extends Fragment {
    private static final String TAG = "TrainingsPlanSelectionF";

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainings_plan_selection, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        TrainingsplanPageAdapter pageAdapter = new TrainingsplanPageAdapter(getFragmentManager());
        ViewPager viewPager = mView.findViewById(R.id.pager);
        viewPager.setAdapter(pageAdapter);
    }

    public class TrainingsplanPageAdapter extends FragmentStatePagerAdapter {

        public TrainingsplanPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: JumpstarPlansFragmentPage fragmentJumpstar = new JumpstarPlansFragmentPage();
                        return fragmentJumpstar;
                case 1: OwnPlansFragmentPage fragmentOwn = new OwnPlansFragmentPage();
                        return fragmentOwn;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "1";
                case 1: return "2";
                default: return "";
            }
        }
    }
}
