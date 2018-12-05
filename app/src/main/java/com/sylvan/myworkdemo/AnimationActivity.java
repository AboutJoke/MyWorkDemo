package com.sylvan.myworkdemo;

import android.animation.*;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import com.sylvan.myworkdemo.utils.DimenUtils;

public class AnimationActivity extends AppCompatActivity {
    private View mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_animation_layout);
        mRootView=findViewById(R.id.rootView);
    }

    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            doDisappear();
            showGift2Diamond();
        }
    };

    /**
     * 展示接收到礼物的动画 ： 初始化礼物数据 + 礼物槽滑出
     * 动画分为 1。礼物槽动画 （礼物槽滑出 展示礼物 展示个数 退出）  2。金边钻石变化动画
     */
    private void showGiftGuideAni(String logo, String param1, String parm2, String giftImageUrl, String name) {
//        if (TextUtils.isEmpty(logo) || (TextUtils.isEmpty(param1) && TextUtils.isEmpty(giftImageUrl))
//                || TextUtils.isEmpty(parm2) || TextUtils.isEmpty(name)) {
//            return;
//        }

        LinearLayout firstContainer = (LinearLayout) mRootView.findViewById(R.id.firstContainer);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout giftItem = (RelativeLayout) inflater.inflate(R.layout.gift_item_layout, null);
        firstContainer.removeAllViews();
        firstContainer.addView(giftItem);
        giftItem.setVisibility(View.GONE);
        ImageView portrait = (ImageView) giftItem.findViewById(R.id.portrait);
        ImageView giftImage = (ImageView) giftItem.findViewById(R.id.giftImage);
        giftImage.setVisibility(View.GONE);
        TextView userNameTv = (TextView) giftItem.findViewById(R.id.userName);
        TextView giftNameTv = (TextView) giftItem.findViewById(R.id.giftName);
        View view = giftItem.findViewById(R.id.rl_offical_gift);
        view.setVisibility(View.VISIBLE);

        portrait.setImageResource(R.drawable.default_icon);
//        giftImage.displayImage(TextUtils.isEmpty(giftImageUrl) ? param1 : giftImageUrl, R.drawable.chat_gift);

        giftNameTv.setText("parm2");

        ImageView roundGiftImage = (ImageView) giftItem.findViewById(R.id.giftImage3);
        roundGiftImage.setImageResource(R.drawable.guide_gift_icon);
        roundGiftImage.setVisibility(View.VISIBLE);

        userNameTv.setText("1233");
        //portrait.setVirefiedType(msg.getVerifyType());

        float width = DimenUtils.dp2px(183);

        giftItem.setVisibility(View.VISIBLE);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(giftItem, View.TRANSLATION_X, -width, 0f);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(giftItem, View.ALPHA, 0.2f, 1f);
        //ObjectAnimator alphaHitAnimator = ObjectAnimator.ofFloat(hitTv, View.ALPHA, 0.1f, 1f);

        AnimatorSet slidInSet = new AnimatorSet();

        slidInSet.setDuration(200);
        //slidInSet.setInterpolator(new LinearInterpolator());
        slidInSet.playTogether(transAnimator, alphaAnimator);
        //slidInSet.setTarget(giftItem);

        slidInSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                showGiftImage(giftItem);
            }
        });
        slidInSet.start();

    }

    /**
     * 展示礼物动画
     *
     * @param giftItem
     */
    private void showGiftImage(final ViewGroup giftItem) {
        View giftImage = giftItem.findViewById(R.id.giftImage);
        giftImage.setVisibility(View.VISIBLE);

        float width = DimenUtils.dp2px(183);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(giftImage, View.ALPHA, 0.2f, 1f);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(giftImage, View.TRANSLATION_X, -width, 0f);
        AnimatorSet showGiftSet = new AnimatorSet();
        showGiftSet.setDuration(200);
        showGiftSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                doHit(giftItem);
            }
        });
        //showGiftSet.setInterpolator(new LinearInterpolator());
        showGiftSet.playTogether(alphaAnimator, transAnimator);
        //showGiftSet.setTarget(giftImage);
        showGiftSet.start();
    }

    /**
     * 礼物个数动画
     *
     * @param giftItem
     */
    private void doHit(final ViewGroup giftItem) {
        final TextView hitTv = (TextView) giftItem.findViewById(R.id.hitView);

        hitTv.setText(" x1 ");

        hitTv.setVisibility(View.VISIBLE);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(hitTv, View.ALPHA, 0.2f, 1f, 1f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(hitTv, View.SCALE_X, 5f, 0.2f, 1.5f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(hitTv, View.SCALE_Y, 5f, 0.2f, 1.5f, 1f);
        AnimatorSet doHitSet = new AnimatorSet();
        doHitSet.setDuration(500);
        doHitSet.setInterpolator(new DecelerateInterpolator());
        doHitSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //doDisappear();
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        //showGiftSet.setInterpolator(new LinearInterpolator());
        doHitSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
        //doHitSet.setTarget(hitTv);
        doHitSet.start();
    }

    /**
     * 隐藏礼物动画
     */
    private void doDisappear() {
        final ViewGroup giftContainer = (ViewGroup) mRootView.findViewById(R.id.firstContainer);
//        scanleGiftAni(giftContainer);
//        showGift2Diamond(giftContainer);

        ViewGroup target = (ViewGroup) giftContainer.findViewById(R.id.giftLayout);
        if (target == null) {
            return;
        }
        float height = DimenUtils.dp2px(50);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, View.ALPHA, 0.8f, 0f, 0);
        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0f, -DimenUtils.dp2px(25), -height);
        AnimatorSet disappearSet = new AnimatorSet();
        disappearSet.setDuration(3000);
        disappearSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        disappearSet.playTogether(alphaAnimator, transAnimator);
        disappearSet.start();
    }

    public void showGift2Diamond(){
        final ViewGroup giftContainer = (ViewGroup) mRootView.findViewById(R.id.firstContainer);
        View giftImg = (View) giftContainer.findViewById(R.id.giftImage3);
        if(giftImg == null) {
            return;
        }
        int[] location1 = new int[2];
        giftImg.getLocationInWindow(location1);

//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final RelativeLayout coinDiamondView = (RelativeLayout) inflater.inflate(R.layout.uplive_guide_coin_diamond_layout, null);
//        final ImageView coinGift=coinDiamondView.findViewById(R.id.guide_coin_gift);
//        final ImageView coinDiamond=coinDiamondView.findViewById(R.id.guide_coin_diamond);
        final ImageView coinGift= new ImageView(this);
        coinGift.setImageResource(R.drawable.guide_gift_icon);
        final ImageView coinDiamond =new ImageView(this);
        coinDiamond.setImageResource(R.drawable.k_diamond);
        coinDiamond.setAlpha(0f);

        ((ViewGroup) mRootView).addView(coinGift);
        ((ViewGroup) mRootView).addView(coinDiamond);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) coinGift.getLayoutParams();
        layoutParams1.leftMargin=location1[0];
        layoutParams1.topMargin=location1[1]- DimenUtils.dp2px(80);
        layoutParams1.width=DimenUtils.dp2px(34);
        layoutParams1.height=DimenUtils.dp2px(34);
        coinGift.setLayoutParams(layoutParams1);

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) coinDiamond.getLayoutParams();
        layoutParams2.leftMargin=location1[0]+DimenUtils.dp2px(60);
        layoutParams2.topMargin=location1[1]-DimenUtils.dp2px(40);
        layoutParams2.width=DimenUtils.dp2px(34);
        layoutParams2.height=DimenUtils.dp2px(34);
        coinDiamond.setLayoutParams(layoutParams2);

//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) coinDiamondView.getLayoutParams();
//        layoutParams.leftMargin = location1[0];
////        layoutParams.leftMargin = location1[0]-DimenUtils.dp2px(30);
//        layoutParams.topMargin = location1[1]- DimenUtils.dp2px(80);
////        layoutParams.topMargin = location1[1]-DimenUtils.dp2px(55);
//
//        layoutParams.width = DimenUtils.dp2px(170);
//        layoutParams.height = DimenUtils.dp2px(170);
//        Log.d("showGift2Diamond", "[lovation0]"+location1[0]+"[location2]"+location1[1]+"//"+coinDiamondView.getWidth()+"/"+coinDiamondView.getHeight());
//        coinDiamondView.setLayoutParams(layoutParams);

        int [] location2 =new int[2];
        coinDiamond.getLocationInWindow(location2);

        final ViewGroup target = (ViewGroup) giftContainer.findViewById(R.id.giftLayout);
        if (target == null) {
            return;
        }
        final float height = DimenUtils.dp2px(50);
//        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, View.ALPHA, 0.8f, 0f, 0);
//        ObjectAnimator transAnimator = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0f, -DimenUtils.dp2px(25), -height);
//        AnimatorSet disappearSet = new AnimatorSet();
//        disappearSet.setDuration(400);
//        disappearSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//            }
//        });
//        disappearSet.playTogether(alphaAnimator, transAnimator);
//        disappearSet.start();

        View kconinNum = mRootView.findViewById(R.id.kcoin_num);
        if(kconinNum == null) {
            return;
        }
        int[] location = new int[2];
        kconinNum.getLocationInWindow(location);

        ObjectAnimator transAnimatorX = ObjectAnimator.ofFloat(coinDiamond, View.TRANSLATION_X, 0f, location[0] - location1[0]);
        ObjectAnimator transAnimatorY = ObjectAnimator.ofFloat(coinDiamond, View.TRANSLATION_Y, -DimenUtils.dp2px(34), location[1] - location1[1] - DimenUtils.dp2px(34));

        ObjectAnimator animator1=ObjectAnimator.ofFloat(coinDiamond,View.SCALE_X,1f,0f);
        ObjectAnimator animator2=ObjectAnimator.ofFloat(coinDiamond,View.SCALE_Y,1f,0f);

        final AnimatorSet disappearSet = new AnimatorSet();
        disappearSet.setDuration(500);
        disappearSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                giftContainer.removeAllViews();
                ((ViewGroup) mRootView).removeView(coinDiamond);
                showCoinChage();
            }
        });
        disappearSet.playTogether(transAnimatorX, transAnimatorY,animator1,animator2);

        ValueAnimator animator=ValueAnimator.ofFloat(1f,10f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                coinGift.setTranslationX(DimenUtils.dp2px(6 * value));
                coinGift.setTranslationY(DimenUtils.dp2px(3 * value));
                coinGift.setScaleX((float) (0.4 * value));
                coinGift.setScaleY((float) (0.4 * value));
                target.setTranslationY(-(5*value));
                target.setAlpha(10-value);

                if (value == 10f) {
                    coinGift.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(500).start();
                    coinDiamond.animate().alpha(1f).scaleX(3f).scaleY(3f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            coinDiamond.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    disappearSet.start();
//                                    ((ViewGroup) mRootView).removeView(coinDiamondView);
//                                    scanleGiftAni(giftContainer);
                                }
                            }, 500);
                        }
                    }).start();
                }
            }
        });

        animator.start();
        //先开启webp
        // TODO: 18-11-20
//        final ObjectAnimator transAnimator1 = ObjectAnimator.ofFloat(coinGift,View.TRANSLATION_X,0f,DimenUtils.dp2px(60));
//        final ObjectAnimator transAnimator2 = ObjectAnimator.ofFloat(coinGift,View.TRANSLATION_Y,0f,DimenUtils.dp2px(30));
//        ObjectAnimator scaleAnimator1 =ObjectAnimator.ofFloat(coinGift,View.SCALE_X,1f,4f);
//        ObjectAnimator scaleAnimator2 =ObjectAnimator.ofFloat(coinGift,View.SCALE_Y,1f,4f);
//
//        Log.d("showGift2Diamond", "[lovation0]"+location1[0]+"[location2]"+location1[1]+"//"+location2[0]+"/"+location2[1]);
//
//        AnimatorSet disappearSet = new AnimatorSet();
//        disappearSet.setDuration(3000);
//        disappearSet.playTogether(scaleAnimator1,scaleAnimator2,transAnimator1,transAnimator2);
//        disappearSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                coinGift.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(3000).start();
//
//               coinDiamond.animate().alpha(1f).scaleX(3f).scaleY(3f).setDuration(3000).setListener(new AnimatorListenerAdapter() {
//                   @Override
//                   public void onAnimationEnd(Animator animation) {
//                       super.onAnimationEnd(animation);
//                       ((ViewGroup) mRootView).removeView(coinDiamondView);
//                       coinDiamond.postDelayed(new Runnable() {
//                           @Override
//                           public void run() {
//                               scanleGiftAni(giftContainer);
//                           }
//                       },200);
//                   }
//               }).start();
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                super.onAnimationStart(animation);
//                doDisappear();
//            }
//        });
//        disappearSet.start();

    }

    /**
     * 将礼物图标移到金币数量位置
     */
    private void scanleGiftAni(final ViewGroup giftContainer) {

        View kconinNum = mRootView.findViewById(R.id.kcoin_num);
        if(kconinNum == null) {
            return;
        }
        int[] location = new int[2];
        kconinNum.getLocationInWindow(location);

        View giftImg = (View) giftContainer.findViewById(R.id.giftImage3);
        if(giftImg == null) {
            return;
        }
        int[] location1 = new int[2];
        giftImg.getLocationInWindow(location1);

        final ImageView roundGiftimg = new ImageView(this);
        ((ViewGroup) mRootView).addView(roundGiftimg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) roundGiftimg.getLayoutParams();
        layoutParams.setMarginStart(location1[0]);
        layoutParams.topMargin = location1[1];
        layoutParams.width = giftImg.getMeasuredWidth();
        layoutParams.height = giftImg.getMeasuredHeight();
        roundGiftimg.setLayoutParams(layoutParams);

        roundGiftimg.setImageResource(R.drawable.k_diamond);

        ObjectAnimator transAnimatorX = ObjectAnimator.ofFloat(roundGiftimg, View.TRANSLATION_X, 0f, location[0] - location1[0]);
        ObjectAnimator transAnimatorY = ObjectAnimator.ofFloat(roundGiftimg, View.TRANSLATION_Y, -DimenUtils.dp2px(34), location[1] - location1[1] - DimenUtils.dp2px(34));
        AnimatorSet disappearSet = new AnimatorSet();
        disappearSet.setDuration(500);
        disappearSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                giftContainer.removeAllViews();
                ((ViewGroup) mRootView).removeView(roundGiftimg);
                showCoinChage();
            }
        });
        disappearSet.playTogether(transAnimatorX, transAnimatorY);
        disappearSet.start();
    }

    //展示金币变化动画
    private void showCoinChage() {
//        LeaderBoardRankView leaderBoardView = mRootView.findViewById(R.id.view_leader_board);
//        if (leaderBoardView == null) {
//            return;
//        }
//        final View bfCoinViewgroup = leaderBoardView.getKCoinAndPraiseContainer();
//        final TextView bfPrasieText = leaderBoardView.getPraiseViewCount();
//        final TextView bfCoinText = leaderBoardView.getKCoinCountView();
//        final View bfCoinAndImg = leaderBoardView.getKCoinContainer();
//        if(bfCoinViewgroup == null) {
//            return;
//        }
        View bfCoinViewgroup = mRootView.findViewById(R.id.kcoin_and_praise_container);
        TextView bfPrasieText = mRootView.findViewById(R.id.chat_praise_top_count);
        TextView bfCoinText = mRootView.findViewById(R.id.kcoin_num);
        ImageView bfDialomd = mRootView.findViewById(R.id.kcoin_diamond);
        final View bfCoinAndImg = mRootView.findViewById(R.id.kcoin_container);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout coinAniRootView = (RelativeLayout) inflater.inflate(R.layout.uplive_first_duide_conin_layout, null);
        View coinViewgroup = coinAniRootView.findViewById(R.id.coin_ani_container);
        View coninBg = coinAniRootView.findViewById(R.id.coin_ani_bg);
        View coinDiaomond = coinAniRootView.findViewById(R.id.coin_ani_img);

        TextView prasieText = (TextView) coinAniRootView.findViewById(R.id.coin_ani_praise);
        prasieText.setText(bfPrasieText.getText().toString() == null ? "0" : bfPrasieText.getText().toString());
//        final TextView coinText = (TextView) coinAniRootView.findViewById(R.id.coin_ani_num);
//        coinText.setText(bfCoinText.getText().toString() == null ? "0" : bfCoinText.getText().toString());

        ((ViewGroup) mRootView).addView(coinAniRootView);

        int[] location = new int[2];
        bfCoinViewgroup.getLocationInWindow(location);
//        bfCoinAndImg.setVisibility(View.INVISIBLE);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) coinAniRootView.getLayoutParams();
        layoutParams.setMarginStart(location[0] - DimenUtils.dp2px(100));
        layoutParams.topMargin = location[1] - DimenUtils.getStatusBarHeight2() - DimenUtils.dp2px(155);
        layoutParams.width = bfCoinViewgroup.getMeasuredWidth() + DimenUtils.dp2px(200);
        layoutParams.height = bfCoinViewgroup.getMeasuredHeight() + DimenUtils.dp2px(200);
        coinAniRootView.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) coninBg.getLayoutParams();
        layoutParams1.width = bfCoinViewgroup.getMeasuredWidth();
        layoutParams1.height = bfCoinViewgroup.getMeasuredHeight();
        coninBg.setLayoutParams(layoutParams1);

        int[] location1 = new int[2];
        bfDialomd.getLocationInWindow(location1);

        ImageView coinDiamond= new ImageView(this);
        coinDiamond.setImageResource(R.drawable.k_diamond);
        ((ViewGroup) mRootView).addView(coinDiamond);

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) coinDiamond.getLayoutParams();
        layoutParams2.width=DimenUtils.dp2px(11);
        layoutParams2.height=DimenUtils.dp2px(11);
        layoutParams2.leftMargin=location1[0];
        layoutParams2.topMargin=location1[1] - DimenUtils.dp2px(80);
        coinDiamond.setLayoutParams(layoutParams2);

        ObjectAnimator animator0 = ObjectAnimator.ofFloat(coinDiamond,View.SCALE_X,1f,3f,1f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(coinDiamond,View.SCALE_Y,1f,3f,1f);

        ObjectAnimator alphaAnimator0 = ObjectAnimator.ofFloat(coninBg, View.ALPHA, 0, 1, 0);
        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(bfCoinViewgroup, View.ALPHA, 1, 0f, 1);

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(coninBg, View.SCALE_X, 1, 1.5f, 2f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(coninBg, View.SCALE_Y, 1, 1.5f, 3f);

        ObjectAnimator scaleXAnimator1 = ObjectAnimator.ofFloat(coinViewgroup, View.SCALE_X, 1, 1.5f, 1f);
        ObjectAnimator scaleYAnimator1 = ObjectAnimator.ofFloat(coinViewgroup, View.SCALE_Y, 1, 1.5f, 1f);

        final AnimatorSet disappearSet = new AnimatorSet();
        disappearSet.setDuration(1000);
        disappearSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                if (mCallBack != null) {
//                    mCallBack.updateGiftData(giftMsg);
//                }
                ((ViewGroup) mRootView).removeView(coinAniRootView);
//                coinAniRootView = null;
//                bfCoinAndImg.setVisibility(View.VISIBLE);
            }
        });
        disappearSet.playTogether(animator0,animator1,scaleXAnimator1, scaleYAnimator1, alphaAnimator0, alphaAnimator1, scaleXAnimator, scaleYAnimator);
        disappearSet.start();

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String coun = coinText.getText().toString();
//                    coinText.setText(Integer.parseInt(coun) + 10 + "");
//                } catch (Exception e) {
//
//                }
//            }
//        }, 500);
    }

    public void StartAnim(View view) {
//        showCoinChage();
        showGiftGuideAni("","","","","");
//        showCoinChage();
    }
}
