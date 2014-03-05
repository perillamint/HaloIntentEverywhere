package org.maneulyori.halointenteverywhere;

import static de.robv.android.xposed.XposedHelpers.*;

import android.content.Intent;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedHooker implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!(lpparam.packageName.equals("com.twitter.android") || lpparam.packageName
				.equals("com.kakao.talk")))
			return;

		XposedBridge.log("TRYING TO HOOK TARGET");

		XC_MethodHook flagger = new XC_MethodHook() {
			@Override
			protected void beforeHookedMethod(MethodHookParam param)
					throws Throwable {
				// this will be called before the clock was updated by the
				// original method
			}

			@Override
			protected void afterHookedMethod(MethodHookParam param)
					throws Throwable {
				Intent intent = (Intent) param.thisObject;
				intent.addFlags(0x00002000);
			}
		};

		XposedBridge.hookAllConstructors(android.content.Intent.class, flagger);
	}
}
