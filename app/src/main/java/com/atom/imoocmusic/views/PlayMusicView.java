package com.atom.imoocmusic.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atom.imoocmusic.R;
import com.atom.imoocmusic.helps.MediaPlayHelp;
import com.bumptech.glide.Glide;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private boolean isPlay;
    private String mPath;
    private MediaPlayHelp mMediaPlayHelp;
    private View mView;
    private ImageView mIvIcon, mIvNeedle, mIvPlay;

    private Animation mPlayMusicAnim, mPlayNeedleAnim, mStopNeedleAnim;

    public PlayMusicView(Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 通过 Log.d 定位到执行的是这个重载，现在还不知道咋回事儿
         */
        init(context);
    }

    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init (Context context) {
        mContext = context;

        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);

        mIvIcon = mView.findViewById(R.id.iv_icon);
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mIvPlay = mView.findViewById(R.id.iv_play);

        mIvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger();
            }
        });

        /**
         * 1、定义所需动画
         *     光盘动画
         *     指针进入光盘动画
         *     指针离开光盘动画
         * 2、startAnimation
         */
        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext, R.anim.stop_needle_anim);

        addView(mView);

        mMediaPlayHelp = MediaPlayHelp.getInstance(mContext);
    }

    /** 切换播放状态 */
    private void trigger() {
        if (isPlay) {
            stopMusic();
        } else {
            playMusic(mPath);
        }
    }

    /** 播放音乐 */
    public void playMusic(String path) {
        mPath = path;
        isPlay = true;
        mIvPlay.setVisibility(View.GONE);
        mIvIcon.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        /**
         * 1、判断点击的音乐是否是已经在播放的音乐
         * 2、如果是，执行start方法播放
         * 3、如果不是，执行setPath方法
         */
        if (mMediaPlayHelp.getPath() != null && mMediaPlayHelp.getPath().equals(path)) {
            mMediaPlayHelp.start();       // 继续当前音乐播放
        } else {
            mMediaPlayHelp.setPath(path); // 播放新的音乐
            // 加载回调
            mMediaPlayHelp.setOnMedaiPlayHelpListener(new MediaPlayHelp.OnMedaiPlayHelpListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayHelp.start();
                }
            });
        }
    }

    /** 停止播放 */
    public void stopMusic() {
        isPlay = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mIvIcon.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        mMediaPlayHelp.pause();
    }

    /**
     * 设置光盘中显示的音乐封面图片
     */
    public void setMusicIcon(String icon) {
        Glide.with(mContext)
                .load(icon)
                .into(mIvIcon);
    }
}
