package com.atom.imoocmusic.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.atom.imoocmusic.R;
import com.atom.imoocmusic.views.PlayMusicView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class PlayMusicActivity extends BaseActivity {

    private ImageView mIvBg;
    private PlayMusicView mPlayMusicView;
    private String imgUrl = "https://y.gtimg.cn/music/photo_new/T002R300x300M000003w7iL42J7TjH.jpg?max_age=2592000";
    private String 出山 = "http://m701.music.126.net/20190427162931/b67ca8e2f81ff95233dbc5edc9fb03a1/jdyyaac/5558/0052/550c/b26bf204b4781483b72cbfcc9d09dadc.m4a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // 隐藏 statusBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        Glide.with(this)
                .load(imgUrl)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 10)))
                .into(mIvBg);

        mPlayMusicView = fd(R.id.play_music_view);
        mPlayMusicView.setMusicIcon(imgUrl);
        // 进入执行播放音乐
        mPlayMusicView.playMusic(出山);
    }

    private void initView() {
        mIvBg = fd(R.id.iv_bg);
    }

    public void onBackClick(View view) {
        onBackPressed();
    }
}
