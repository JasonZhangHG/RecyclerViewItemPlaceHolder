package cool.android.placeholder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rlv_main_activity) RecyclerView mRecyclerView;
    @BindView(R.id.iv_animation_main_activity) ImageView mAnimationImage;
    private GridLayoutManager mGridLayoutManager;
    private ShowImageAdapter mShowImageAdapter;
    private int imagePosition;
    private String coverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mShowImageAdapter = new ShowImageAdapter();
        mShowImageAdapter.setDataSilently(ImageUrlConfig.getUrls());
        mShowImageAdapter.setOnItemClickListener(mAdapterItemListener);
        mRecyclerView.setAdapter(mShowImageAdapter);
    }

    @OnClick(R.id.iv_animation_main_activity)
    public void recoverAnimation() {
        mAnimationImage.post(new Runnable() {
            @Override
            public void run() {
                ShowImageAdapter.ShowImageAdapterHolder holder = (ShowImageAdapter.ShowImageAdapterHolder) mRecyclerView.findViewHolderForAdapterPosition(imagePosition);
                if (holder != null && holder.mItemRelativeLayout != null && holder instanceof ShowImageAdapter.ShowImageAdapterHolder) {
                    float x = holder.mItemRelativeLayout.getX() + holder.mItemRelativeLayout.getWidth() / 2;
                    float y = holder.mItemRelativeLayout.getY() + holder.mItemRelativeLayout.getHeight() / 2;
                    ScaleAnimation animation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, x, y);
                    animation.setDuration(300);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mAnimationImage.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    mAnimationImage.startAnimation(animation);
                } else {
                    mAnimationImage.setVisibility(View.GONE);
                }
            }
        });
    }

    private AdapterView.OnItemClickListener mAdapterItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
            onShowImageAdapterItemClick(mShowImageAdapter.getData().get(position).getCoverUrl(), position, view);
        }
    };

    public void onShowImageAdapterItemClick(String coverUrl, final int position, View view) {
        if (mAnimationImage == null) {return;}
        this.imagePosition = position;
        this.coverUrl = coverUrl;
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, getString(R.string.image));
        Intent intent = new Intent(this, ShowImageActivity.class);
        intent.putExtra("imageURL", coverUrl);
        ActivityCompat.startActivity(this, intent, compat.toBundle());


//
//        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
//
//        Intent intent = new Intent(this, ShowImageActivity.class);
//        intent.putExtra("imageURL", coverUrl);
//        ActivityCompat.startActivity(this, intent, compat.toBundle());

//
//        mAnimationImage.setVisibility(View.VISIBLE);
//        Glide.with(MainActivity.this)
//                .load(coverUrl)
//                .fitCenter()
//                .dontAnimate()
//                .into(mAnimationImage);
//        mAnimationImage.post(new Runnable() {
//            @Override
//            public void run() {
//                ShowImageAdapter.ShowImageAdapterHolder holder = (ShowImageAdapter.ShowImageAdapterHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
//                if (holder != null && holder.mItemRelativeLayout != null && holder instanceof ShowImageAdapter.ShowImageAdapterHolder) {
//                    float x = holder.mItemRelativeLayout.getX() + holder.mItemRelativeLayout.getWidth() / 2;
//                    float y = holder.mItemRelativeLayout.getY() + holder.mItemRelativeLayout.getHeight() / 2;
//                    ScaleAnimation animation = new ScaleAnimation(0.2f, 1.0f, 0.0f, 1.0f, x, y);
//                    animation.setDuration(300);
//                    animation.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            mAnimationImage.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                    mAnimationImage.startAnimation(animation);
//                }
//            }
//        });
    }
}
