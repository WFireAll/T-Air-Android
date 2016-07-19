package com.silenoff.frame.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.view.View;

import com.silenoff.frame.contentview.ContentView;
import com.silenoff.frame.contentview.ContentWidget;



public class InjectUtils {
	
	 public static void injectObject(Object object, Activity activity) {

		 Class<?> classType = object.getClass();

		 if (classType.isAnnotationPresent(ContentView.class)) 
		 {
			 ContentView annotation = classType.getAnnotation(ContentView.class);
			 try {
					 Method method = classType.getMethod("setContentView", int.class);
					 method.setAccessible(true);
					 int resId = annotation.value();
					 method.invoke(object, resId);
				 } catch (NoSuchMethodException e) {
				 e.printStackTrace();
				 } catch (IllegalArgumentException e) {
				 e.printStackTrace();
				 } catch (IllegalAccessException e) {
				 e.printStackTrace();
				 } catch (InvocationTargetException e) {
				 e.printStackTrace();
				 }
	     }
		 
		 Field[] fields = classType.getDeclaredFields();

		 if (null != fields && fields.length > 0) 
		 {
			 for (Field field : fields) 
			 {
				 if (field.isAnnotationPresent(ContentWidget.class)) 
				 {
					 ContentWidget annotation = field.getAnnotation(ContentWidget.class);
					 int viewId = annotation.value();
					 View view = activity.findViewById(viewId);
					 if (null != view) 
					 {
						 try{
							 field.setAccessible(true);
							 field.set(object, view);
						 } catch (IllegalArgumentException e) {
						 e.printStackTrace();
						 } catch (IllegalAccessException e) {
						 e.printStackTrace();
					     }
			        }
		        }


		     }


	    } 
		 
		 
		 
   }

}
