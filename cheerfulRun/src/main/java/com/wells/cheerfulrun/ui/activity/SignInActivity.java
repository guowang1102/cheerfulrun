package com.wells.cheerfulrun.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wells.cheerfulrun.app.Config;
import com.wells.cheerfulrun.R;
import com.wells.cheerfulrun.utils.PrefsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.OtherLoginListener;

/**
 * Created by wei on 16/4/17.
 */
public class SignInActivity extends Activity  {

    PrefsUtil prefsUtil = PrefsUtil.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Bmob.initialize(this, Config.BMOB_APPID);
    }

    public void doLogin(View view) {
        switch (view.getId()){
            case R.id.qq:
                qqAuthorize();
                break;
            default:
                break;
        }
    }

    public static Tencent mTencent;
    public LoginListener loginListener = new LoginListener();

    private void qqAuthorize(){

        String openid = prefsUtil.getPrefsStrValue(Config.KEY_QQ_OPENID);
        String access_token = prefsUtil.getPrefsStrValue(Config.KEY_QQ_TOKEN);
        String expires_in = prefsUtil.getPrefsStrValue(Config.KEY_QQ_EXPIRES);

        if(mTencent==null){
            mTencent = Tencent.createInstance(Config.QQ_APP_ID,getApplicationContext());
        }
//        mTencent.logout(this);
        if(!openid.isEmpty()&&!access_token.isEmpty()&&!expires_in.isEmpty()){
            mTencent.setOpenId(openid);
            mTencent.setAccessToken(access_token, expires_in);
        }

        mTencent.login(this, "all",loginListener);

    }

    public class LoginListener implements IUiListener{

        @Override
        public void onComplete(Object obj) {

            Log.v("--","onComplete"+obj.toString());
            toast("onComplete："+obj.toString());
            if(obj!=null){
                JSONObject jsonObject = (JSONObject) obj;
                try {
                    String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                    String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                    String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);

                    prefsUtil.setPrefsStrValue(Config.KEY_QQ_TOKEN,token);
                    prefsUtil.setPrefsStrValue(Config.KEY_QQ_OPENID,openId);
                    String dealExpires = String.valueOf(System.currentTimeMillis() + Long.parseLong(expires) * 1000);  //需处理
                    prefsUtil.setPrefsStrValue(Config.KEY_QQ_EXPIRES,dealExpires);

                    BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ,token, expires,openId);
                    loginWithAuth(authInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(UiError uiError) {
            toast("QQ授权出错："+uiError.errorCode+"--"+uiError.errorDetail);
            Log.v("--","onComplete"+uiError.errorCode+"--"+uiError.errorDetail);
        }

        @Override
        public void onCancel() {
            toast("取消qq授权");
            Log.v("--","onCancel");
        }
    }

    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(SignInActivity.this, authInfo, new OtherLoginListener() {

            @Override
            public void onSuccess(JSONObject userAuth) {
                Log.v("info","onSuccess");
                // TODO Auto-generated method stub
                Log.i("smile",authInfo.getSnsType()+"登陆成功返回:"+userAuth);
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                intent.putExtra("json", userAuth.toString());
                intent.putExtra("from", authInfo.getSnsType());
                startActivity(intent);
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                toast("第三方登陆失败："+msg);
                Log.v("info","onFailure");
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(SignInActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                mTencent.handleLoginData(data, loginListener);

            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
