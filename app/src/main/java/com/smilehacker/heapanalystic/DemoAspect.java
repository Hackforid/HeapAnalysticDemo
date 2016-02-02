package com.smilehacker.heapanalystic;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by kleist on 16/2/1.
 */
@Aspect
public class DemoAspect {
    static final String TAG = DemoAspect.class.getSimpleName();

    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void logForOnClick() {}

    @Before("logForOnClick()")
    public void logClick(JoinPoint joinPoint) {
        Log.d(TAG, joinPoint.toShortString());
        View view = (View) joinPoint.getArgs()[0];
        View child = view;
        ViewParent parent = view.getParent();
        //String path = view.getTag(R.string.tag) == null ? "" : (String) view.getTag(R.string.tag);
        String path = "";
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                path = String.format("%s[%d].%s", child.getClass().getSimpleName(), ((ViewGroup) parent).indexOfChild(child), path);
            } else {
                path = parent.getClass().getSimpleName() + "." + path;
                break;
            }
            child = (View) parent;
            parent = parent.getParent();
        }
        Log.d(TAG, "path = " + path);
    }


}
