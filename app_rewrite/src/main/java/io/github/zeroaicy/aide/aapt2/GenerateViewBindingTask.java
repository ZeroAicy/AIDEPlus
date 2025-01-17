package io.github.zeroaicy.aide.aapt2;
import android.app.Application;
import com.aide.ui.services.AssetInstallationService;
import dalvik.system.DexClassLoader;
import io.github.zeroaicy.util.Log;
import io.github.zeroaicy.util.reflect.ReflectPie;
import java.io.File;

public class GenerateViewBindingTask{
	
	
	private static ClassLoader viewbindingClassLoader;
	
	public static void run(String mainProjectResPath, String mainProjectGenDir, String mainProjectPackageName, boolean isAndroidx) throws Exception{
		if( viewbindingClassLoader == null ){
			String viewbindingZipPath = getViewbindingZipPath();
			viewbindingClassLoader = new DexClassLoader(viewbindingZipPath, null, null, Application.class.getClassLoader());
		}
		if( viewbindingClassLoader == null  ){
			throw new NullPointerException("viewbindingClassLoader为null");
		}
		
		ReflectPie.on("ZY.ViewBinding.Utils", viewbindingClassLoader).call("BindingTask", new Object[]{mainProjectResPath, mainProjectGenDir, mainProjectPackageName, isAndroidx});
		if( false ){
			Log.d("主项目res目录", mainProjectResPath);
			Log.d("主项目gen目录", mainProjectGenDir);
			Log.d("主项目包名", mainProjectPackageName);
			Log.d("isAndroidx", isAndroidx);
		}
    }

	private static String getViewbindingZipPath(){
		String viewbindingZipPath = AssetInstallationService.DW("viewbinding.zip", false);
		
		File viewbindingZipFile = new File(viewbindingZipPath);
		if(!viewbindingZipFile.canExecute()){
			viewbindingZipFile.setReadable(true, false);
			viewbindingZipFile.setExecutable(true, false);
		}
		return viewbindingZipPath;
	}
}
