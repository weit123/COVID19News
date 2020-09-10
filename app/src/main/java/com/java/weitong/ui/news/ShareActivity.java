package com.java.weitong.ui.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.java.weitong.R;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoSourceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.common.UiError;
import com.sina.weibo.sdk.openapi.IWBAPI;
import com.sina.weibo.sdk.openapi.WBAPIFactory;
import com.sina.weibo.sdk.share.WbShareCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ShareActivity extends Activity implements View.OnClickListener, WbShareCallback {

    private CheckBox mShareText;

    private CheckBox mShareUrl;

    private RadioButton mShareClientOnly;

    private RadioButton mShareClientH5;

    private Button mCommitBtn;

    //在微博开发平台为应用申请的App Key
    private static final String APP_KY = "890738041";
    //在微博开放平台设置的授权回调页
    private static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    //在微博开放平台为应用申请的高级权限
    private static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    private IWBAPI mWBAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSdk();
        startAuth();
        Log.e("!!!", "============================================================================================================================\n");


        AuthInfo authInfo = new AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
        mWBAPI.setLoggerEnable(true);

        doWeiboShare();
    }


    @Override
    public void onClick(View v) {
//        if (v == mCommitBtn) {
//            doWeiboShare();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mWBAPI.authorizeCallback(requestCode, resultCode, data);
        mWBAPI.doResultIntent(data, this);
    }

    private void doWeiboShare() {
        WeiboMultiMessage message = new WeiboMultiMessage();

        TextObject textObject = new TextObject();
        String text;
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        textObject.text = text;
        message.textObject = textObject;
        mWBAPI.shareMessage(message, false);
    }

    @Override
    public void onComplete() {
        Toast.makeText(ShareActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError error) {
        Toast.makeText(ShareActivity.this, "分享失败:" + error.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(ShareActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
    }

    //init sdk
    private void initSdk() {
        AuthInfo authInfo = new AuthInfo(this, APP_KY, REDIRECT_URL, SCOPE);
        mWBAPI = WBAPIFactory.createWBAPI(this);
        mWBAPI.registerApp(this, authInfo);
    }

    private void startAuth() {
        //auth
        mWBAPI.authorize(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                Toast.makeText(ShareActivity.this, "微博授权成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError error) {
                Toast.makeText(ShareActivity.this, "微博授权出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ShareActivity.this, "微博授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startClientAuth() {
        mWBAPI.authorizeClient(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                Toast.makeText(ShareActivity.this, "微博授权成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError error) {
                Toast.makeText(ShareActivity.this, "微博授权出错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ShareActivity.this, "微博授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startWebAuth() {
        mWBAPI.authorizeWeb(new WbAuthListener() {
            @Override
            public void onComplete(Oauth2AccessToken token) {
                Toast.makeText(ShareActivity.this, "微博授权成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(UiError error) {
                Toast.makeText(ShareActivity.this, "微博授权出错:" + error.errorDetail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(ShareActivity.this, "微博授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
