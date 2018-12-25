package cool.android.placeholder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_show) ImageView mImageViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("imageURL");
        Glide.with(ShowImageActivity.this)
                .load(url)
                .fitCenter()
                .dontAnimate()
                .into(mImageViewShow);
    }

}
