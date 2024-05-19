package com.example.temperatureandhumiditytesting.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.temperatureandhumiditytesting.R;
import com.example.temperatureandhumiditytesting.activity.common.CommonWebActivity;
import com.example.temperatureandhumiditytesting.base.App;

public class XieYiDialogUtils extends Dialog {
    public XieYiDialogUtils(Context context) {
        super(context);
    }

    public XieYiDialogUtils(Context context, int theme) {
        super(context, theme);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认的titlebar
//        setContentView(R.layout.my_dialog);
//    }


    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private boolean mCancelable = true;//默认 true  可以取消
        private boolean mCanceledOnTouchOutside = true;//默认 true  可以取消

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * @param context
         * @param cancelable              是否可以取消
         * @param mCanceledOnTouchOutside 点击外部是否取消
         * @param message                 内容
         * @param positiveButtonText      确认按钮的文字
         * @param listener                确认的监听
         * @param negativeButtonText      取消按钮的文字
         * @param listener2               取消的监听
         */
        public Builder(Context context, boolean cancelable, boolean mCanceledOnTouchOutside, String message, String positiveButtonText,
                       OnClickListener listener, String negativeButtonText,
                       OnClickListener listener2) {
            this.message = message;
            this.mCancelable = cancelable;
            this.mCanceledOnTouchOutside = mCanceledOnTouchOutside;
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener2;
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        @SuppressLint("MissingInflatedId")
        public XieYiDialogUtils create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final XieYiDialogUtils dialog = new XieYiDialogUtils(context, R.style.MyDialog);
            View layout = inflater.inflate(R.layout.xieyi_my_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //-----------------------------------------------------
            TextView tv_tongyi = (TextView) layout.findViewById(R.id.tv_tongyi);
            tv_tongyi.setHighlightColor(context.getResources().getColor(android.R.color.transparent));
            SpannableString spanableInfo = new SpannableString("请你务必审慎阅读、充分理解“服务协议”和“隐私政策”各条款，包括但不限于：" +
                    "为了向你提供内容分享等服务，我们需要收集你的设备信息、操作日志等个人信息。你可阅读《服务协议》和《隐私政策》了解" +
                    "详细信息。如你同意，请点击“同意”开始接受我们的服务");
            spanableInfo.setSpan(new Clickable(clickListener1), 78, 83, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanableInfo.setSpan(new Clickable(clickListener2), 85, 90, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_tongyi.setText(spanableInfo);
            tv_tongyi.setMovementMethod(LinkMovementMethod.getInstance());
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);


            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_dialog_ok))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_dialog_ok))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_dialog_ok).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.btn_dialog_cancel))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.btn_dialog_cancel))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.btn_dialog_cancel).setVisibility(
                        View.GONE);
            }
            // set the content message
            if (message != null) {
              //  ((TextView) layout.findViewById(R.id.dialog_message)).setText(message);
            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.dialog_content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.dialog_content)).addView(
                        contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }

    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
    private static View.OnClickListener clickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(App.context, CommonWebActivity.class);
            intent.putExtra("url", "https://www.baidu.com/");
            intent.putExtra("type", "yonghuxieyi");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.context.startActivity(intent);

        }
    };
    private static View.OnClickListener clickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(App.context, CommonWebActivity.class);
            intent.putExtra("url","https://www.baidu.com/");
            intent.putExtra("type", "yinsizhengce");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.context.startActivity(intent);

        }
    };
    static class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /**
         * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(App.context.getResources().getColor(R.color.text4));
        }
    }
}
