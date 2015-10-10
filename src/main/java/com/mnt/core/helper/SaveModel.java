package com.mnt.core.helper;

import static play.data.Form.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;

import com.mnt.core.domain.FileAttachmentMeta;
import com.mnt.core.ui.annotation.UIFields;

public abstract class SaveModel<model extends Model> {
	
	public static String imageRootDir;
	static {
		Properties prop = new Properties();
		String filename = "app.properties";
		InputStream input = SaveModel.class.getClassLoader().getResourceAsStream(filename);
		try {
			prop.load(input);
			imageRootDir = prop.getProperty("imageRootDir");
		} catch (IOException e) {
			e.printStackTrace();
			imageRootDir = "/time-images";
		}
		
	}
	
	protected Class<model> ctx; 
	
	
	
	
	public SaveModel(Class<model> ctx){
		this.ctx=ctx;
	}
	
	public SaveModel() {
	}

	public final <T extends Model>Object doSave(boolean isUpdate,HttpServletRequest request) throws Exception{
		
		Map<String,String[]> parameterMap = request.getParameterMap();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		for(String key : multipartRequest.getFileMap().keySet()){
			List<MultipartFile> files = multipartRequest.getFiles(key);
			List<FileAttachmentMeta> metaFiles = new ArrayList<FileAttachmentMeta>();
			for(MultipartFile file : files) {
				metaFiles.add(new FileAttachmentMeta(file.getOriginalFilename(), file.getSize(),
						file.getContentType()));
			}
			
			parameterMap.put(key, new String[]{Json.toJson(metaFiles).toString()});
		}
		
		Form<? extends Model> form = form(ctx).bindFromRequest(parameterMap);
		preSave(form);
		
		if(!isUpdate){
			form.get().save();
		}else{
			form.get().update();
		}
		Object id = form.get().getClass().getField("id").get(form.get());
		handleFilePart(request,id);
		postSave();
		return id;
	}
	
	
	
	private void handleFilePart(HttpServletRequest request, Object id) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		for(String key : multipartRequest.getFileMap().keySet()){
			List<MultipartFile> files = multipartRequest.getFiles(key);
			for(MultipartFile file : files) {
				;
				file.getContentType();
				;
				OutputStream outputStream = null;
				String filePath = imageRootDir + File.separator +ctx.getSimpleName()+ File.separator + id + File.separator + "flexi";
				new File(filePath).mkdirs();
				outputStream = 
	                    new FileOutputStream(new File(filePath + 
	                    		File.separator + file.getOriginalFilename()));
				outputStream.write(file.getBytes());
				outputStream.close();
			}
		};
		
	}

	protected void preSave(Form<? extends Model> form) throws Exception{
		for(Field f :ctx.getFields()){
			if(f.isAnnotationPresent(UIFields.class)){
				if(((UIFields)f.getAnnotation(UIFields.class)).autocomplete()){
					String _idAsStr = form.data().get(f.getName()+"_id");
					if(_idAsStr!=null && _idAsStr.length()>0){
						Object o =f.getType().getDeclaredMethod("findById", Long.class).invoke(null, Long.valueOf(_idAsStr));
						form.get().getClass().getField(f.getName()).set(form.get(), o);
					}
				}
				
				
				
				if(((UIFields)f.getAnnotation(UIFields.class)).searchable() || ((UIFields)f.getAnnotation(UIFields.class)).multiselect()){
					String _idAsStr = form.data().get(f.getName()+"_ids");
					List<Long> _idAsLong = new ArrayList<Long>();
					for(String _id:_idAsStr.split(",")){
						if(_id.length()>0)
							_idAsLong.add(Long.valueOf(_id));
					}
					if(!_idAsLong.isEmpty()){
						ParameterizedType listType = (ParameterizedType)f.getGenericType();
						 Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
						Object o =listClass.getDeclaredMethod("findByIds", List.class).invoke(null, _idAsLong);
						form.get().getClass().getField(f.getName()).set(form.get(), o);
					}
				}
				
			}
		}
	}
	
	protected void postSave(){}
}
