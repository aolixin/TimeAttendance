package cn.mr.clock.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.awt.image.BufferedImage;
import javax.naming.ConfigurationException;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import cn.mr.clock.session.Session;

/**
 * @author ??????
 *	????????????????
 */
public class FaceEngineService {
    private static String appId = null;
    private static String sdkKey = null;
    private static FaceEngine faceEngine = null;
    private static String ENGINE_PATH ="TimeAttendance/ArcSoft_ArcFace";
    private static final String CONFIG_FILE="TimeAttendance/src/main/resources/ArcFace.properties";
    static {
    	Properties pro = new Properties();
    	File config =new File(CONFIG_FILE);
    	try {
    		if(!config.exists()) {
    			throw new FileNotFoundException("??????: "+config.getAbsolutePath());
    		}
    		pro.load(new FileInputStream(config));
    		appId=pro.getProperty("APP_ID");
    		sdkKey=pro.getProperty("SDK_KEY");
    		if(appId==null||sdkKey==null)
    		{
    			throw new ConfigurationException("ArcFace.properties??????????");
    		}
    	}catch (FileNotFoundException e) {
    		e.printStackTrace();		
    	}catch (ConfigurationException e) {
    		e.printStackTrace();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	File path=new File(ENGINE_PATH);//?????????
    	faceEngine =new FaceEngine(path.getAbsolutePath());//???????????
    	int errcode=faceEngine.activeOnline(appId, sdkKey);
    	if(errcode!=ErrorInfo.MOK.getValue()
    			&&errcode!=ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
    		//System.out.println("???äí?????,???ü•??????????,??????????????");
    	}
    	
    	//????????
    	EngineConfiguration engineConfiguration = new EngineConfiguration();//???????? 
    	engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);//?????????
    	engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);//??????§ß??
    	engineConfiguration.setDetectFaceMaxNum(1);//?????????
    	engineConfiguration.setDetectFaceScaleVal(16);//????????????????
    	FunctionConfiguration functionConfiguration = new FunctionConfiguration();
    	functionConfiguration.setSupportAge(true);//??????????
    	functionConfiguration.setSupportFaceRecognition(true);//??????????
    	functionConfiguration.setSupportFaceDetect(true);
    	functionConfiguration.setSupportFace3dAngle(true);
    	functionConfiguration.setSupportAge(true);
    	functionConfiguration.setSupportGender(true);
    	engineConfiguration.setFunctionConfiguration(functionConfiguration);//???????????????
    	errcode =faceEngine.init(engineConfiguration);
    	if(errcode!=ErrorInfo.MOK.getValue()) {
    		//System.out.println("???????????");
    	}
    }
    
    
   /**
    * ????????????
 * @param img
 * @return
 */
public static FaceFeature getFacefeature(BufferedImage img) {
    		if(img==null)throw new NullPointerException("????????null");
    		BufferedImage face =new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_BGR);
    		face.setData(img.getData());//?????????????????
    		ImageInfo imageInfo =ImageFactory.bufferedImage2ImageInfo(face);//??????????
    		
    		List<FaceInfo>faceInfoList = new ArrayList<FaceInfo>();//????????§Ò?
    		
    		faceEngine.detectFaces(imageInfo.getImageData(),imageInfo.getWidth(),imageInfo.getHeight()
    				,imageInfo.getImageFormat(),faceInfoList);
    		
    		if(faceInfoList.isEmpty()) {
    			return null;
    		}
    		FaceFeature faceFeature = new FaceFeature();
    		//??????????????????????
    		faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(),imageInfo.getHeight(),
    				imageInfo.getImageFormat(),faceInfoList.get(0),faceFeature);
    		return faceFeature;
    	}   
   
   

   /**
 * ????????????????
 * ???imaage_map????????????????feature_map
 */
public static void loadAllFaceFeature() {
	   Set<String>keys =Session.IMAGE_MAP.keySet();
	   for(String stu_id:keys) {
		   BufferedImage image =Session.IMAGE_MAP.get(stu_id);
		   FaceFeature faceFeature = getFacefeature(image);
		   Session.FACE_FEATURE_MAP.put(stu_id, faceFeature);
	   }
   }
   
   
   /**
    * ???target?????session.feature_map??
 * @param targetfaceFeature
 * @return
 */
public static String detecFace(FaceFeature targetfaceFeature) {
	//??????????,?????
	if(targetfaceFeature==null) {return null;}
	//???????????
   Set<String>keys=Session.FACE_FEATURE_MAP.keySet();
   float score=0;
   String resultId=null;
   for(String id:keys) {
	   //???????
	   FaceFeature sourceFaceFeature = Session.FACE_FEATURE_MAP.get(id);
	   //??????????
	   FaceSimilar faceSimilar = new FaceSimilar();
	   //???????
	   faceEngine.compareFaceFeature(targetfaceFeature, sourceFaceFeature, faceSimilar);
	   if(faceSimilar.getScore()>score) {
		   score = faceSimilar.getScore();//???????????
		   resultId=id;   //????????????????
	   }
   }
   //???????????0.9????
   if(score>0)return resultId;
   return null;
}
   
   
   /**
 * ??????
 */
public static void dispost() {
	   faceEngine.unInit();
   }
}