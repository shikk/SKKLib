package com.shikk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shikk.data.Constant;

public class PhoneInfoGenerator {

	public static String IMSI= "";
	public static String phoneNum ="";
	public static String IMEI="";
	public static String IP = "";
	public static String MAC = "";
	public static String proxyIP="";
	public static int proxyPort=0;
	public static String netWorkOper = "";
	public static String netWorkOperName = "";
	public static String simOper = "";
	public static String simSerialNo = "";
	public static DeviceModelData modelData;
	public static String deviceModel="";
	public static String osVersion="";
	public static String cpuArm1 = "";
	public static String cpuArm2 = "";
	private static ArrayList<DeviceModelData> deviceList;
	public static String brand;
	public static String manufacture;
	public static String device;
	public static String board;
	public static String display;
	public static String hardware;
	public static int softwareversion;
	public static String product;
	public static String buildId;
	public static String serial;
	public static long time;
	public static String androidId;
	public static String hotword;
	
	public static void generateIMEI(){
		StringBuilder imeiString= new StringBuilder();//
		Random rd = new Random();
		String imei2[] = {"35","86","01"}; // 前两位
		int probb = rd.nextInt(10);
		if (probb<3) {
			imeiString.append(imei2[0]);
		}else if (probb<8) {
			imeiString.append(imei2[1]);
		}else {
			imeiString.append(imei2[2]);
		}
		//4位
		int tmp = rd.nextInt(10000);
		DecimalFormat decfm = new DecimalFormat("0000");
		imeiString.append(decfm.format(tmp));
		//两位 02，03，04，05
		imeiString.append("0"+(rd.nextInt(4)+2));
		int nextInt = rd.nextInt(1000000);
		DecimalFormat df = new DecimalFormat("000000"); // 六位数：产品序列号，任意随机
//		System.out.println(df.format(nextInt));
		imeiString.append(df.format(nextInt));
        char[] imeiChar=imeiString.toString().toCharArray();  
        int resultInt = getLUHNResalt(imeiChar);  
        System.out.println("imei:"+imeiString+resultInt);
        IMEI =  imeiString.append(resultInt).toString(); // 前八位+中间产品序列号六位+效验位 = IMEI
	}
	private static int getLUHNResalt(char[] imeiChar) {
		int resultInt=0;   // 最后一位效验位，根据以下规则效验产生
        for (int i = 0; i < imeiChar.length; i++) {  
            int a=(int)imeiChar[i];  
            i++;  
            int temp = 0;
            if (i < imeiChar.length) {
            	 temp=(int)(imeiChar[i])*2; 
			}
            final int b=temp<10?temp:temp-9;  
            resultInt+=a+b;  
        }  
        resultInt%=10;  
        resultInt=resultInt==0?0:10-resultInt;
		return resultInt;
	}
	public static void generateIMSIAndNum(){
		String MCC = "460";
		Random rd = new Random();
		int nextInt = rd.nextInt(101);
		String MNC = "";
		if (nextInt < 97) {
			DecimalFormat df = new DecimalFormat("00");
			MNC = df.format(rd.nextInt(4));
		}else{
			MNC = "07";
		}
		if (MNC.equals("01")) { // 联通
			String haoDuan[] = {"13","13","13","13","15","15","18"};
			String S[] = {"0","0","1","2","5","6","6"};
			String A[] = {"0","1","9","2","4","3","6"};
			int ind = rd.nextInt(S.length);
			DecimalFormat df = new DecimalFormat("0000");
			String H1230 = df.format(rd.nextInt(10000));
			DecimalFormat df2 = new DecimalFormat("00000");
			String XXXXX = df2.format(rd.nextInt(100000));
			IMSI = MCC+MNC+H1230+A[ind]+XXXXX;
			String H0123 = H1230.substring(3, 4)+H1230.substring(0,3);
			phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0123+df.format(rd.nextInt(10000));
			
			netWorkOper = simOper = MCC+MNC;
			netWorkOperName="中国联通";
			// 年份
			int y = rd.nextInt(7)+8;
			DecimalFormat df3 = new DecimalFormat("00");
			String YY = df3.format(y);
			DecimalFormat df4 = new DecimalFormat("00000000");
			String xxxxx = df4.format(rd.nextInt(100000000));
			String[] sheng = {"10","11","13","17","18","19","30","31","34","36","38","50","51","59","70","71","74","75","76","79","81","83","84","85","86","87","88","89","90","91","97"};
			simSerialNo = "8986"+MNC+YY+"8"+sheng[rd.nextInt(sheng.length)]+xxxxx;
			int luhnResalt = getLUHNResalt(simSerialNo.toCharArray());
			simSerialNo = simSerialNo+luhnResalt;
		}else if (MNC.equals("03")) { // 电信
			String haoDuan[] = {"133","153","180","181","189"};
			String S[] =       {"0"  ,"1"  ,"2"  ,"5"  ,"6"};
			String A[] =       {"1"  ,"9"  ,"2"  ,"4"  ,"3"};
			int ind = rd.nextInt(S.length);
			DecimalFormat df = new DecimalFormat("0000");
			String H1230 = df.format(rd.nextInt(10000));
			DecimalFormat df2 = new DecimalFormat("00000");
			String XXXXX = df2.format(rd.nextInt(100000));
			IMSI = MCC+MNC+H1230+A[ind]+XXXXX;
			String H0123 = H1230.substring(3, 4)+H1230.substring(0,3);
			phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0123+df.format(rd.nextInt(10000));
			
			netWorkOper = simOper = MCC+MNC;
			netWorkOperName="中国电信";
			// 年份
			int y = rd.nextInt(7)+8;
			DecimalFormat df3 = new DecimalFormat("00");
			String YY = df3.format(y);
			DecimalFormat df4 = new DecimalFormat("00000000");
			String xxxxx = df4.format(rd.nextInt(100000000));
			DecimalFormat df5 = new DecimalFormat("000");
			String SSS = df5.format(rd.nextInt(1000));
			simSerialNo = "8986"+MNC+"0"+YY+SSS+xxxxx;
		}else{  // 移动
			String haoDuan[] = {"13","13","13","13","13","13","15","15","15","15","15","15","18","18"};
			String S[] =       {"5" ,"6" ,"7" ,"8" ,"9" ,"4" ,"8" ,"9" ,"0" ,"1" ,"2" ,"7" ,"8" ,"7"};
			String M[] =       {"5" ,"6" ,"7" ,"8" ,"9" ,"4" ,"1" ,"0" ,"2" ,"3" ,"C" ,"A" ,"B" ,"E"};
			String A[] =       {"0" ,"1" ,"9" ,"2" ,"3" ,"0" ,"8" ,"9" ,"3" ,"1" ,"2" ,"7" ,"8" ,"7"};
			String mnc[] =     {"00","00","00","00","00","02","02","02","02","02","02","07","07","02"};
			int ind = rd.nextInt(haoDuan.length);
			if (ind < 5){
				int flag = rd.nextInt(2);
				if (flag == 0) { // 13S0H1H2H3ABCD  46000H1H2H3SXXXXXX S=5,6,7,8,9 XXXXXX为MSISDN号码中ABCD扰码得到
					String H0 = "0";
					DecimalFormat df = new DecimalFormat("000");
					String H123 = df.format(rd.nextInt(1000));
					DecimalFormat df2 = new DecimalFormat("00000");
					String XXXXX = df2.format(rd.nextInt(100000));
					IMSI = MCC+mnc[ind]+H0+H123+S[ind]+XXXXX;
					phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0+H123+df.format(rd.nextInt(10000));
				}else{           // 13SH0H1H2H3ABCD 46000H1H2H3TH0XXXXX S=5、6、7、8、9;T=S-5;XXXXX为MSISDN号码
					String H0 = String.valueOf(rd.nextInt(10));
					DecimalFormat df = new DecimalFormat("000");
					String H123 = df.format(rd.nextInt(1000));
					DecimalFormat df2 = new DecimalFormat("00000");
					String XXXXX = df2.format(rd.nextInt(100000));
					IMSI = MCC+mnc[ind]+H123+(Integer.parseInt(S[ind])-5)+H0+XXXXX;
					phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0+H123+df.format(rd.nextInt(10000));
				}
			}else if(ind == 5){  // 134xxxxx
				String H0 = String.valueOf(rd.nextInt(9));
				DecimalFormat df = new DecimalFormat("000");
				String H123 = df.format(rd.nextInt(1000));
				DecimalFormat df2 = new DecimalFormat("00000");
				String XXXXX = df2.format(rd.nextInt(100000));
				IMSI = MCC+mnc[ind]+A[ind]+H0+H123+XXXXX;
				phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0+H123+df.format(rd.nextInt(10000));

			}else{
				DecimalFormat df = new DecimalFormat("0000");
				String H0123 = df.format(rd.nextInt(10000));
				DecimalFormat df2 = new DecimalFormat("00000");
				String XXXXX = df2.format(rd.nextInt(100000));
				IMSI = MCC+mnc[ind]+A[ind]+H0123+XXXXX;
				phoneNum = "+86"+haoDuan[ind] + S[ind]+ H0123+df.format(rd.nextInt(10000));
			}
			
			netWorkOper = simOper = MCC+MNC;
			netWorkOperName="中国移动";
			// 年份
			int y = rd.nextInt(7)+8;
			DecimalFormat df3 = new DecimalFormat("00");
			String YY = df3.format(y);
			DecimalFormat df4 = new DecimalFormat("000000");
			String xxxxx = df4.format(rd.nextInt(1000000));
			int ss = rd.nextInt(31)+1;
			String SS = df3.format(ss);
			int pos = rd.nextInt(11);
			String posStr =""; 
			if (pos == 10) {
				posStr = "A";
			}else{
				posStr = ""+pos;
			}
			simSerialNo = "8986"+MNC+M[ind]+phoneNum.charAt(6)+SS+YY+posStr+xxxxx;
			int luhnResalt = getLUHNResalt(simSerialNo.toCharArray());
			simSerialNo = simSerialNo+luhnResalt;
		}
		System.out.println("imsi:"+IMSI);
		System.out.println("phno:"+phoneNum);
	}
	
	
	public static void generateIpAndMac(){
		String ipString = "";
		Random rd = new Random();
		int ip3 = rd.nextInt(2);
		int ip4 = rd.nextInt(30);
		boolean flag = rd.nextBoolean();
		if (flag) {
			ipString = "192.168."+ip3+"."+(ip4+0);
		}else{
			ipString = "192.168."+ip3+"."+(ip4+100);
		}
		IP = ipString;
		MAC = "";
		for (int j = 0; j < 6; j++) {
			int dd = rd.nextInt(256);
			if (dd<16) {
				dd+=16;
			}
			if (j == 5) {
				MAC = MAC + Integer.toHexString(dd);
			}else{
				MAC = MAC + Integer.toHexString(dd)+":";
			}
		}
		
	}
	
	public static void generateProxyIpAndPort2() {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {
			// 创建url 电信http://api.iphai.com/getapi.ashx?ddh=1354138896&num=1&yys=2&am=0&guolv=y&mt=0&fm=text
			//联通3 移动4 铁通5 http://api.iphai.com/getapi.ashx?ddh=1354138896&num=1&yys=3&am=0&guolv=y&mt=0&fm=text
			Random random = new Random();
			int yys= random.nextInt(4)+2;
			URL realurl = new URL("http://api.iphai.com/getapi.ashx?ddh="+Constant.ipVpnOrderNo + "&num=1&yys="+yys+"&am=0&guolv=y&mt=0&fm=text");
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
//			System.out.println(result);
			String[] split = result.split(":");
			proxyIP = split[0];
			proxyPort = Integer.parseInt(split[1]);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		

	}
	public static void generateProxyIpAndPort3() {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {
			// 创建url http://tp.daili666.com/ip/?tid=796817135827529&num=1&ports=1998,80,8080&filter=on&delay=2
			//http://tp.daili666.com/ip/?tid=796817135827529&num=1&exclude_ports=3128&filter=on
			URL realurl = new URL("http://tp.daili666.com/ip/?tid="+Constant.ipVpnOrderNo1 + "&num=1&exclude_ports=3128&filter=on&delay=2");
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
			String[] split = result.split(":");
			proxyIP = split[0];
			proxyPort = Integer.parseInt(split[1]);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
//		
		
	}
	
	public static void generateProxyIpAndPort(){
		try {
			 Document doc = Jsoup.connect("http://www.xici.net.co/nt/").timeout(5000).get(); 
			 String title = doc.title(); 
//			 System.out.println(title);
			 ArrayList<String> ipList = new ArrayList<>();
			 ArrayList<String> portList = new ArrayList<>();
			 Elements select = doc.select("tr");
			 for (Element element : select) {
				 Element childIp = element.child(1);
				 Element childPort = element.child(2);
				 Element childSpeed = element.child(7);
				 Elements children = childSpeed.children();
				 if (children.size()>0) {
					Element element2 = children.get(0);
					Element childSpeedBar = element2.child(0);
					String className = childSpeedBar.className();
					if (className.contains("medium") || className.contains("fast")) {
						ipList.add(childIp.text());
//						System.out.println(childIp.text());
						portList.add(childPort.text());
//						System.out.println(childPort.text());
					}
				}
			}
			 int size = ipList.size();
//			 System.out.println("ip size"+ size);
			 int randomInd = new Random().nextInt(size);
			 proxyIP = ipList.get(randomInd);
			 proxyPort = Integer.parseInt(portList.get(randomInd));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int twoSpliteGetDevice(ArrayList<DeviceModelData> list,int value){
		 int index = -1;  
	        int low = 0;  
	        int high = list.size()-1;  
	        while(low<=high) {  
	            int mid = low + (high-low)/2;  
	            
	            if(value >= list.get(mid).getUserCount1() && value < list.get(mid).getUserCount2()) {  
	                index = mid;  
	                break;  
	            } else if( value < list.get(mid).getUserCount1() ) {  
	                high = mid-1;  
	            } else {  
	                low = mid+1;  
	            }  
	        }  
	        return index; 
	}
	
	
	private static void generateModel(){
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		try {
			if (deviceList == null) {
				reader1 = new BufferedReader(new FileReader(new File(Constant.JavaProjPath+File.separator+"1.txt")));
				reader2 = new BufferedReader(new FileReader(new File(Constant.JavaProjPath+File.separator+"2.txt")));
				deviceList = new ArrayList<>();
				while ((deviceModel = reader1.readLine()) != null) {
					modelData = new DeviceModelData();
					modelData.setModelName(deviceModel);
					String countStr = reader2.readLine();
					String[] split = countStr.trim().split("\\s+");
//					System.out.println("deviceUsercount :"+split.length);
//					for (int i = 0; i < split.length; i++) {
//						System.out.println("deviceUsercount1 :"+split[i]);
//					}
					modelData.setUserCount1(Integer.parseInt(split[0]));
					modelData.setUserCount2(Integer.parseInt(split[1]));
					deviceList.add(modelData);
				}
			}
			Random random = new Random();
			int value = random.nextInt(deviceList.get(deviceList.size()-1).getUserCount2()+1);
			System.out.println("randow value: "+value);
			int ind = twoSpliteGetDevice(deviceList, value);
			System.out.println("randow ind: "+ind);
			deviceModel = deviceList.get(ind).getModelName();
			
			int osV1 = random.nextInt(10);
			String osV1Str = "";
			if (osV1 <1) {
				osV1Str = "2.2";
				int osV3 = random.nextInt(4);
				osVersion = osV1Str +"."+ osV3;
			}else if (osV1 <3) {
				osV1Str = "2.3";
				int osV3 = random.nextInt(8);
				osVersion = osV1Str +"."+ osV3;
			}else{
				int osV2 = random.nextInt(4);
				osV1Str = "4."+osV2;
				int osV3 = random.nextInt(5);
				osVersion = osV1Str +"."+ osV3;
			}
			
			// cpu armabi
			String armabis1[] = {"armeabi-v6","armeabi-v6a","armeabi-v7","armeabi-v7"};
			String armabis2[] = {"armeabi-v6a","armeabi-v6s","armeabi-v7a","armeabi-v7s"};
			int index = random.nextInt(armabis1.length);
			cpuArm1 = armabis1[index];
			cpuArm2 = armabis2[index];
			
			 StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < deviceModel.length(); i++) {
		            if ((deviceModel.charAt(i)+"").getBytes().length<=1) {
		                sb.append(deviceModel.charAt(i));
		            }else{
		            	sb.append(" ");
		            }
		        }
		        if (deviceModel.contains("红米")) {
		        	brand = "MI NOTE";
				}else if (deviceModel.contains("华为")) {
					brand = "HUAWEI";
				}else if (deviceModel.contains("三星")) {
					brand = "SAMSUNG";
				}else if (deviceModel.contains("魅族")) {
					brand = "MEIZU";
				}else if (deviceModel.contains("小米")) {
					brand = "MI";
				}else if (deviceModel.contains("酷派")) {
					brand = "CoolPad";
				}else if (deviceModel.contains("索尼")) {
					brand = "Sony";
				}else if (deviceModel.contains("努比亚")) {
					brand = "Nubia";
				}else if (deviceModel.contains("金立")) {
					brand = "Gionee";
				}else if (deviceModel.contains("移动")) {
					brand = "China Mobile";
				}else if (deviceModel.contains("海信")) {
					brand = "Hisense";
				}else if (deviceModel.contains("波导")) {
					brand = "BIRD";
				}else if (deviceModel.contains("酷比魔方")) {
					brand = "CUBE";
				}else if (deviceModel.contains("爱派尔")) {
					brand = "IPH";
				}else if (deviceModel.contains("小辣椒")) {
					brand = "XiaoLaJiao";
				}else if (deviceModel.contains("海尔")) {
					brand = "Haier";
				}else if (deviceModel.contains("黑米")) {
					brand = "HIMI";
				}else if (deviceModel.contains("欧奇")) {
					brand = "Eqi";
				}else if (deviceModel.contains("华硕")) {
					brand = "ASUS";
				}else if (deviceModel.contains("联想")) {
					brand = "Lenovo";
				}else if (deviceModel.contains("中兴")) {
					brand = "ZTE";
				}else if (deviceModel.contains("优购")) {
					brand = "Uoogou";
				}else if (deviceModel.contains("奥洛斯")) {
					brand = "szares";
				}else if (deviceModel.contains("爱我")) {
					brand = "loveme";
				}else if (deviceModel.contains("爱国者")) {
					brand = "aigo";
				}else if (deviceModel.contains("佳域 ")) {
					brand = "JIAYU";
				}else if (deviceModel.contains("明基")) {
					brand = "BenQ";
				}else if (deviceModel.contains("尼彩")) {
					brand = "NIECHE";
				}else if (deviceModel.contains("聆韵")) {
					brand = "LINGWIN";
				}else if (deviceModel.contains("康佳")) {
					brand = "KONKA";
				}else if (deviceModel.contains("先锋")) {
					brand = "Poineer";
				}else if (deviceModel.contains("大可乐")) {
					brand = "KELE";
				}else if (deviceModel.contains("摩托")) {
					brand = "MOTO";
				}else if (deviceModel.contains("贝尔丰")) {
					brand = "Bifer";
				}else if (deviceModel.contains("天语")) {
					brand = "Touch";
				}else if (deviceModel.contains("英特奇")) {
					brand = "InterQ";
				}else if (deviceModel.contains("乐丰")) {
					brand = "Lefeng";
				}else if (deviceModel.contains("赛博宇华")) {
					brand = "SOP";
				}else if (deviceModel.contains("时通伟业")) {
					brand = "TD";
				}else if (deviceModel.contains("埃立特")) {
					brand = "elitek";
				}else if (deviceModel.contains("驰为")) {
					brand = "CHIWEI";
				}else if (deviceModel.contains("誉品")) {
					brand = "YEPEN";
				}else if (deviceModel.contains("朵唯")) {
					brand = "doov";
				}else if (deviceModel.contains("E人E本")) {
					brand = "EBen";
				}else if (deviceModel.contains("亿通")) {
					brand = "ETON";
				}else if (deviceModel.contains("语信")) {
					brand = "YUSUN";
				}else if (deviceModel.contains("天时达")) {
					brand = "telsda";
				}else if (deviceModel.contains("卓酷")) {
					brand = "ZUOKU";
				}else if (deviceModel.contains("读书郎")) {
					brand = "RB";
				}else if (deviceModel.contains("百分之百")) {
					brand = "CNMO";
				}else{
					String[] split = deviceModel.split("\\s+");
					for (int i = 0; i < split.length; i++) {
						if (split[i]!="" && split[i] != " ") {
							brand = split[i];
							break;
						}
					}
				}
		        manufacture = brand;
		        product= device = deviceModel;
		        if(random.nextBoolean()){
		        	hardware = board = brand;
		        	display = osVersion+"."+(char)(random.nextInt(48)+65)+(char)(random.nextInt(26)+65)+brand;
		        }else{
		        	hardware = board = "unknow";
		        	display = sb.toString().trim()+"_"+osVersion+(char)(random.nextInt(26)+65)+brand;
		        }
		        softwareversion = random.nextInt(68)+31;
		        
		        StringBuilder sbBuildId = new StringBuilder();
		        for (int i = 0; i < random.nextInt(8)+5; i++) {
		        	int c = random.nextInt(75)+48;
		        	if ((c>=58 && c<=64) || (c>=91 && c<=96)) {
		        		sbBuildId.append(String.valueOf(random.nextInt(10)));
					}else{
						sbBuildId.append((char)c);
					}
				}
		       buildId = sbBuildId.toString();
		       
		       StringBuilder sbSerial = new StringBuilder();
		        for (int i = 0; i < 8; i++) {
		        	int c = random.nextInt(75)+48;
		        	if ((c>=53 && c<=69) || (c>=86 && c<=103)) {
		        		sbSerial.append(String.valueOf(random.nextInt(10)));
					}else{
						sbSerial.append((char)c);
					}
				}
		        serial = sbSerial.toString().toLowerCase();
		        
		        long begin = new Date(2011-1900, 3, 6).getTime();
		        long end = System.currentTimeMillis();
		        time = begin + (long)(Math.random()*(end - begin));
		        StringBuilder sbAndroidId = new StringBuilder();
		        for (int i = 0; i < 16; i++) {
		        	int c = random.nextInt(75)+48;
		        	if ((c>=53 && c<=69) || (c>=86 && c<=103)) {
		        		sbAndroidId.append(String.valueOf(random.nextInt(10)));
					}else{
						sbAndroidId.append((char)c);
					}
				}
		        androidId = sbAndroidId.toString().toLowerCase();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (reader1 != null) {
				try {
					reader1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader2 != null) {
				try {
					reader2.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public static void refresh(){
		generateIMSIAndNum();
		generateIMEI();
		generateIpAndMac();
//		generateProxyIpAndPort();
//		generateProxyIpAndPort2();
		generateProxyIpAndPort3();
		generateModel();
		generateHotword();
	}
	
	
	
	private static void generateHotword() {
		// TODO Auto-generated method stub
		hotword = "here 这里要生成热词";
		BufferedReader reader1 = null;
		try {
			reader1 = new BufferedReader(new FileReader(new File(Constant.JavaProjPath+File.separator+"hotword.txt")));
			String str = null;
			ArrayList<String> hotWords = new ArrayList<>();
			while ((str = reader1.readLine())!=null) {
				hotWords.add(str);
			}
			Random rd = new Random();
			int ind = rd.nextInt(hotWords.size());
			hotword = hotWords.get(ind);
		}catch(IOException exception){
			exception.printStackTrace();
		}finally{
			if (reader1!=null) {
				try {
					reader1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			
		System.out.println("=============================================================");
		refresh();
		System.out.println("imei:"+IMEI);
		System.out.println("imsi:"+IMSI);
		System.out.println("phonenum:"+phoneNum);
		System.out.println("IP:"+IP);
		System.out.println("MAC:"+MAC);
		System.out.println("proxyIp:"+proxyIP);
		System.out.println("proxyPort:"+proxyPort);
		System.out.println("netWorkOper:"+netWorkOper);
		System.out.println("netWorkOperName:"+netWorkOperName);
		System.out.println("simOper:"+simOper);	
		System.out.println("simSerialNo:"+simSerialNo);
		System.out.println("deviceModel:"+deviceModel);
		System.out.println("osVersion:"+osVersion);
		System.out.println("cpuArm1:"+cpuArm1);
		System.out.println("cpuArm2:"+cpuArm2);
		System.out.println("brand:"+brand);
		System.out.println("manufacture:"+manufacture);
		System.out.println("device:"+device);
		System.out.println("board:"+board);
		System.out.println("display:"+display);
		System.out.println("hardware:"+hardware);
		System.out.println("softwareversion:"+softwareversion);
		System.out.println("product:"+product);
		System.out.println("buildId:"+buildId);
		System.out.println("serial:"+serial);
		System.out.println("time:"+Math.abs(time));
		System.out.println("androidId:"+androidId);
		System.out.println("-================================================================");
		}
	}
	
}
