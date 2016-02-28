package com.bjwg.back.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bjwg.back.base.constant.StatusConstant;
import com.bjwg.back.base.constant.SystemConstant;
import com.bjwg.back.model.Sysconfig;
import com.bjwg.back.service.SysconfigService;


/**
 * @author Kim
 * @version 创建时间：2015-3-10 上午10:56:22
 * Version: 
 * 类说明： 图片上传处理
 */

public class UploadFile {
	
	protected static Logger logger = LogManager.getLogger();
	
	/**
	 * 单张图片上传     logo
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getFile(HttpServletRequest request,String fileNamePage,String root,String localPath) {
		String pathName = "";
		try {
			MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
			MultipartFile imgFile  =  multipartRequest.getFile(fileNamePage); 
			if(imgFile != null && !(imgFile.getOriginalFilename() ==null || "".equals( imgFile.getOriginalFilename()))) {  
				List fileTypes = new ArrayList();  
				fileTypes.add("jpg");  
				fileTypes.add("jpeg");  
				fileTypes.add("bmp");  
				fileTypes.add("gif");
				fileTypes.add("png");
				String fileName = imgFile.getOriginalFilename();
				if( !StringUtils.isNotEmpty( fileName )){
					return null;
				}
			    String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()); 
			    //对扩展名进行小写转换  
			    ext = ext.toLowerCase();
			    if(fileTypes.contains(ext)) {  //如果扩展名属于允许上传的类型，则创建文件  
			    	pathName = FileUtils.getRandomFileName() +"."+ ext;
			    	ServletContext sc = request.getSession().getServletContext();
        			String uploadPath = sc.getRealPath(localPath);
        			System.out.println("-----------uploadPath==="+uploadPath);
			        String path = uploadPath +"/"+ MyUtils.getYYYYMMDD(0);  
			        File localFile = new File(path,pathName); 
			        localFile.setWritable(true, false);//手动设置以下权限。
			        if(!localFile.exists()){  
			        	localFile.mkdirs();  
			        }
			        imgFile.transferTo(localFile);  //保存上传的文件  
			        System.out.println("----------root=="+root);
			        System.out.println("----------path=="+path);
			        System.out.println("----------pathName=="+pathName);
			        pathName = path.replace(root,"") + "/" + pathName;
			        
//			        -----------uploadPath===/mnt/tomcat_bjwg/webapps/BJWG_BACK/
//			        ----------root==
//			        ----------path==/mnt/tomcat_bjwg/webapps/BJWG_BACK//2015-09-23
//			        ----------pathName==2015092345263.jpg
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return pathName;  
	}
	*/
	

	/**
	 * 上传单个文件 本地服务器   
	 * @param request
	 * @param type 
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static Map<String, Object> uploadSingleFile(HttpServletRequest request, String type) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			if(StringUtils.isNotEmpty(type)){
				type = FileConstant.UPLOAD_HEAD_IMG;
			}
			
			//转型为MultipartHttpRequest(重点的所在)  
			MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;  
			
			//  获得第1张图片（根据前台的name名称得到上传的文件）   
			MultipartFile imgFile  =  multipartRequest.getFile("imgFile"); 
			
			String fileName = imgFile.getOriginalFilename();
			
			if(!StringUtils.isNotEmpty(fileName)){
				map.put("status",StatusConstant.Status.UPLOAD_NOFILE.getStatus());
				return map;
			}
			
			/*下面调用的方法，主要是用来检测上传的文件是否属于允许上传的类型范围内，及根据传入的路径名 
			 *自动创建文件夹和文件名，返回的File文件我们可以用来做其它的使用，如得到保存后的文件名路径等 
			 *这里我就先不做多的介绍。 
			 */  
			long size = imgFile.getSize();
			
			SysconfigService sysconfigService = (SysconfigService)SpringFactory.getBean("sysconfigService");
			
			List<String> codeList = new ArrayList<String>(Arrays.asList(SystemConstant.UPLOAD_FILE_LIMIT_TYPE,SystemConstant.UPLOAD_FILE_MAX_SIZE,SystemConstant.PROJECT_IMG_UPLOAD_ROOT_PATH,SystemConstant.PROJECT_IMG_ACCESS_URL));
			List<Sysconfig> list = sysconfigService.queryList(codeList);
			
			if(MyUtils.isListEmpty(list) || list.size() != codeList.size()){
				map.put("status", StatusConstant.Status.FILE_UPLOAD_CONFIG_NOEXIST.getStatus());
				return map;
			}
			
			//获取路径
			String uploadPath = null;
			//文件最大MB
			String maxSize = null;
			//文件类型
			List<String> suffixList = null;
			//文件访问根路径
			String accessUrl = null;
			
			for (Sysconfig sc : list) {
				if(SystemConstant.UPLOAD_FILE_LIMIT_TYPE.equals(sc.getCode())){
					suffixList = new ArrayList<String>(Arrays.asList(sc.getValue().split(",")));
				}else if(SystemConstant.UPLOAD_FILE_MAX_SIZE.equals(sc.getCode())){
					maxSize = sc.getValue();
				}else if(SystemConstant.PROJECT_IMG_ACCESS_URL.equals(sc.getCode())){
					accessUrl = sc.getValue();
				}else{
					uploadPath = sc.getValue();
				}
			}
			
			if(StringUtils.isEmpty(uploadPath)){
				map.put("status", StatusConstant.Status.UPLOAD_DIR.getStatus());
				return map;
			}
			//定义上载文件的最大字节(默认10MB)
			if(size > (1024 * 1024 * Long.valueOf(maxSize))){
				map.put("status", StatusConstant.Status.UPLOAD_SIZE.getStatus());
				map.put("maxSize", maxSize);
				return map;
			}
			
			//获取上传文件类型的扩展名,先得到.的位置，再截取从.的下一个位置到文件的最后，最后得到扩展名  
			String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()); 
			
			//对扩展名进行小写转换  
			ext = ext.toLowerCase();
			
			//如果扩展名属于允许上传的类型，则创建文件
			if(suffixList.contains(ext)){  
				
				String pathName = fileName.toLowerCase();
				
				InputStream inputStream = imgFile.getInputStream();
				
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				
				int des = 0;
				
				
				String fileAccessPath = "/" + MyUtils.getYYYYMMDD(0)+"/";
					
				if(FileConstant.UPLOAD_HEAD_IMG.equals(type)){
					
					fileAccessPath += ToolKit.getInstance().getSingleConfig(type);
				}
				
				uploadPath = uploadPath + fileAccessPath;
				
//				if("yes".equals(ToolKit.getInstance().getSingleConfig("test"))){
					
//					fileAccessPath = "/resources/"+fileAccessPath+"/";
//				}
				
				fileAccessPath = accessUrl + fileAccessPath +"/";
				
				String newFileName = System.currentTimeMillis() + MyUtils.random(4) + "" +suffix;
				
				System.out.println("文件上传路径："+uploadPath + "，文件名称："+newFileName);
				
				File localFile = new File(uploadPath,newFileName);
				
//				FileUtils.makeDir(localFile);
				
				//手动设置以下权限。
				localFile.setWritable(true, false);
				
		        if(!localFile.exists()){
		        	
		        	localFile.mkdirs();  
		        }
		        
		        //保存上传的文件  
			    imgFile.transferTo(localFile); 
			    
			    map.put("status", StatusConstant.Status.SUCCESS.getStatus());
			    map.put("filePath", fileAccessPath + newFileName);
			    map.put("sourceName", fileName);
			    map.put("newFileName", newFileName);
			    map.put("fileSize", size);
			    map.put("suffix", suffix);
				return map;
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件出错",e);
			map.put("status", StatusConstant.Status.UPLOAD_UNKNOWN.getStatus());
		}  
		return map;
	}

	/**
	 * 删除图片
	 * @param request
	 * @param fileNamePage
	 * @return
	 */
	public static void deleteFile(String fileName) {
		FileUtils.delete(fileName);
	}
	
	/**
	 * 下载
	 * @param path
	 * @param request
	 * @param response
	 */
	public static void download(String path,HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
        try {
        	pw = response.getWriter();
        	request.setCharacterEncoding("UTF-8");
        	// path是指欲下载的文件的路径。
            File file = new File(path);
         // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            //String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            if(!file.exists()){
            	pw.print(file.getAbsolutePath()+"文件不存在!");
            	return;
            }
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
//            response.flushBuffer();//加上这句出现下载文件名为.do
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"),"iso8859-1"));
//            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream out = response.getOutputStream();
            OutputStream toClient = new BufferedOutputStream(out);
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            out.flush();
            out.close();
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
