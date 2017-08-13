package keller.com.second_hand_car.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import keller.com.second_hand_car.activity.MainActivity;

/*
 * @创建者     Administrator
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class ToastUtils {
    /**
     * 可以在子线程中弹出toast
     *
     * @param context
     * @param text
     */
    public static void showToastSafe(final Context context, final String text) {
        ThreadUtils.runInUIThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* 显示Dialog的method */
    public static void showDialog(final Context context, String mess)
    {
        new AlertDialog.Builder(context).setTitle("恭喜预定成功！")
                .setMessage(mess)
                .setNegativeButton("稍后我们将电话联系您！",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
//                        ((Activity)context).finish();
                    }
                })
                .show();
    }
}
