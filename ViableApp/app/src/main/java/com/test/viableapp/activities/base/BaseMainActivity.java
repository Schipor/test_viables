package com.test.viableapp.activities.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.viableapp.R;
import com.test.viableapp.fragments.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class BaseMainActivity extends AppCompatActivity implements BaseFragment.BaseFragmentCallback {

    @BindView(R.id.action_back)
    ImageView actionBack;
    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.activity_base_fragment_container)
    FrameLayout fragmentContainer;

    private FragmentManager mFragmentManager;
    private Dialog mOverlayDialog;

    public interface CustomDialogCallback {
        void onCancel();

        void onAction();
    }

    public abstract int getLayoutResource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();


    }

    @OnClick(R.id.action_back)
    public void onActionBackClicked() {
        //created custom back pressed to handle the fragment stack if required
        onBackPressed();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment inflatedFragment = mFragmentManager.findFragmentById(fragmentContainer.getId());

        if (fragment == null) return;

        if (inflatedFragment != null &&
                inflatedFragment.getClass().getSimpleName().equals(fragment.getClass().getSimpleName())) {
            //prevent adding duplicate fragment
            return;
        }

        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(fragmentContainer.getId(), fragment, fragment.getClass().getSimpleName());
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        if (isFinishing() || isDestroyed()) {
            return;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void showBackButton(boolean show) {
        actionBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setCustomTitle(String title) {
        mainTitle.setText(title);
    }

    @Override
    public void showLoading() {
        try {
            if (mOverlayDialog != null && mOverlayDialog.isShowing()) {
                return;
            }

            mOverlayDialog = new Dialog(this);
            mOverlayDialog.setContentView(R.layout.loading_dialog_layout);
            mOverlayDialog.setCancelable(false);
            mOverlayDialog.setCanceledOnTouchOutside(false);
            if (mOverlayDialog.getWindow() != null)
                mOverlayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mOverlayDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            View divider = mOverlayDialog.findViewById(this.getResources().getIdentifier("android:id/titleDivider", null, null));
            if (divider != null)
                divider.setBackgroundColor(ContextCompat.getColor(this, R.color.KColorTransparent));
            mOverlayDialog.show();

        } catch (Throwable t) {
            Log.e("Exception", t.getMessage(), t);
        }
    }

    @Override
    public void dismissLoading() {
        if (mOverlayDialog != null) {
            try {
                mOverlayDialog.dismiss();
            } catch (Throwable t) {
                Log.e("Exception", t.getMessage(), t);
            }
        }
    }

    @Override
    public void showAlert(String title, String message, String okAction, String cancelAction, final CustomDialogCallback customDialogCallback) {
        AlertDialog dialog;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            if (title != null) {
                builder.setTitle(title);
            }
            builder.setMessage(message);
            builder.setPositiveButton(okAction, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (customDialogCallback != null) customDialogCallback.onAction();
                }
            });
            if (cancelAction != null) {
                builder.setNegativeButton(cancelAction, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (customDialogCallback != null) customDialogCallback.onCancel();
                    }
                });
            }
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
