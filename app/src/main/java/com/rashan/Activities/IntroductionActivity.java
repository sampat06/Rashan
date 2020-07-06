package com.rashan.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rashan.R;
import com.rashan.databinding.ActivityIntroductionBinding;

public class IntroductionActivity extends AppCompatActivity {

    ActivityIntroductionBinding binding;
    ImageView[] indicators;

    int lastLeftValue = 0;


    SectionsPagerAdapter mSectionsPagerAdapter;

    static final String TAG = "PagerActivity";

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_introduction);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());



        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                binding.container.setCurrentItem(page, true);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                binding.container.setCurrentItem(page, true);
            }
        });



        binding.introBtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent toMain = new Intent(IntroductionActivity.this, LoginActivity.class);
                startActivity(toMain);
                //finish();
            }
        });

        binding.introFinalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(IntroductionActivity.this, HomeActivity.class);
                startActivity(toMain);
                finish();

            }
        });




        indicators = new ImageView[]{binding.introIndicator0, binding.introIndicator1, binding.introIndicator2,binding.introIndicator3,binding.introIndicator4};

        // Set up the ViewPager with the sections adapter.
        binding.container.setAdapter(mSectionsPagerAdapter);

        binding.container.setCurrentItem(page);
        updateIndicators(page);

/*

        final int color1 = ContextCompat.getColor(this, R.color.redend);
        final int color2 = ContextCompat.getColor(this, R.color.redend);
        final int color3 = ContextCompat.getColor(this, R.color.redend);
*/

        //  final int[] colorList = new int[]{color1, color2, color3};

        final ArgbEvaluator evaluator = new ArgbEvaluator();

        binding.container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                /*
                color update
                 */
               /* int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
*/
            }

            @Override
            public void onPageSelected(int position) {

                page = position;

                updateIndicators(page);

                switch (position) {
                    case 0:
                        // mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        // mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        // mViewPager.setBackgroundColor(color3);
                        break;

                    case 3:
                        // mViewPager.setBackgroundColor(color3);
                        break;
                    case 4:
                        // mViewPager.setBackgroundColor(color3);
                        break;
                }


                binding.introFinalNext.setVisibility(position == 4 ? View.VISIBLE : View.GONE);
                binding.introBtnSkip.setVisibility(position == 4 ? View.GONE : View.VISIBLE);
                binding.back.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                binding.next.setVisibility(position == 4 ? View.GONE : View.VISIBLE);

                Animation animation= AnimationUtils.loadAnimation(IntroductionActivity.this,R.anim.in_from_left);
                binding.container.setAnimation(animation);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }


    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }


    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ImageView img;
        TextView sectionlabel;
        Typeface typeface;

        int[] bgs = new int[]{R.drawable.shop, R.drawable.groceries, R.drawable.groceries_basket,R.drawable.groceries_mobile,R.drawable.discount};

        String[] labelscustomer = new String[]{"You can Enrolled Course and enhance your Skills.","Enjoy Video Lecture any where any time","After take lecture you can give test and get Report card"
                ,"You can Search any of University , College & Institute","You can get Latest Job updates and News"};
        String[] labelstext = new String[]{"Enrolled Course","Take Video Course","Give Test","Find College and Institute","Latest Job News"};

        // String[] labelsbusiness = new String[]{"Get Client Easier.","Boost Your Business.","Improve Your Client Management."};


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView text = (TextView) rootView.findViewById(R.id.text);







           /* String a = getArguments().toString();

            if (a.equalsIgnoreCase("1")){

                textView.setText("Get your best look.");
            }

            if (a.equalsIgnoreCase("2")){

                textView.setText("Save time and book from home.");
            }

            if (a.equalsIgnoreCase("3")){

                textView.setText("Get Quality service from best salon in your area.");
            }*/

            // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            //sectionlabel=rootView.findViewById(R.id.section_label);


            textView.setText(labelscustomer[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            text.setText(labelstext[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);


            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
            }
            return null;
        }

    }
}
