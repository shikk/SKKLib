package com.shikk.data;

import java.io.File;
import java.lang.annotation.Target;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.portable.ApplicationException;

public class DBCenter {
	public final static String DB_NAME="remains";
	private final static String TABLE_BRUSHES="brushes";
	private final static String TABLE_APPS="apps";
	private final static String TABLE_WAVE="wave";
	private static final String TABLE_NEW_DEVICE = "newdevices";
	private static final String TABLE_MODEL_RATE = "model_rate";
	private static final String TABLE_DB_VERSION = "db_verison";
	private static String DB_URL = "jdbc:mysql://127.0.0.1:3306/";
	private static DBCenter instance;
	private Connection conn;
	private Statement statement;
	public static void main(String[] args)  {
		DBCenter dbCenter = DBCenter.getInstance();
		try {
			dbCenter.openDB(DB_NAME);
//			// 添加app信息
//			TargetApp app = new TargetApp();
//			app.setAppName("testapp1");
//			app.setPkgName("com.shikk.test1");
//			app.setVersionCode(12);
//			app.setVersionName("0.1.5");
//			ArrayList<Float> brushRate = new ArrayList<>();
//			float rate1 = 40f;
//			for (int i = 0; i < 7; i++) {
//				brushRate.add(rate1/100);
//				rate1 -= 4;
//			}
//			ArrayList<Integer> maxNewQuality = new ArrayList<>();
//			for (int i = 0; i < 7; i++) {
//				maxNewQuality.add(500);
//			}
//			app.setBrushRate(brushRate);
//			app.setMaxNewQuality(maxNewQuality);
//			dbCenter.addNewApp(app);
			
			//添加100个刷新增量记录
//			TargetApp app2 = new TargetApp();
//			app2.setAppId(17);
//			Date date2 = new Date();
//			Date qiantian = new Date(date2.getTime() - 3600*1000*24*1);
//			for (int i = 0; i < 800; i++) {
//				dbCenter.addBrushQuality(qiantian, app2,qiantian, 1, 0,"device1","imeixxx");
//			}
			
			//添加100个留存记录
//			TargetApp app2 = new TargetApp();
//			app2.setAppId(17);
//			int[] quality = {400};
//			Date date2 = new Date();
//			// 第一次安装时间
//			int day = 1;
//			Date qiantian = new Date(date2.getTime() - 3600*1000*24*day);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			for (int i = day-1; i >= 0; i--) {
//				// 操作日期
//				Date date = new Date(date2.getTime() - 3600*1000*24*i);
//				System.out.println("time:"+df.format(date)+"   quality:"+quality[quality.length-i-1]);
//				for (int j = 0; j < quality[quality.length-i-1]; j++) {
//					dbCenter.addBrushQuality(date, app2,qiantian, 0, 1,"device1","imeixxx");
//				}
//			}
			
//			
			// 查询今日应刷某日的留存是多少
//			TargetApp app3 = new TargetApp();
//			app3.setAppId(17);
//			ArrayList<Float> brushRate = new ArrayList<>();
//			float rate1[] = {0.5f,0.4f,0.3f,0.25f,0.20f,0.15f,0.10f,0.10f,0.10f,0.10f};
//			for (int i = 0; i < 7; i++) {
//				brushRate.add(rate1[i]);
//			}
//			app3.setBrushRate(brushRate);
//			Date date2 = new Date();
//			Date qiantian = new Date(date2.getTime() - 3600*1000*24*5);
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			for (int i = 10; i >=0; i--) {
//				Date calTime = new Date(date2.getTime() - 3600*1000*24*i);
//				int calculateRemain = dbCenter.calculateRemain(qiantian, calTime, app3);
//				System.out.println("day:"+df.format(calTime)+"    calculateRemain: "+calculateRemain);
//			}
			
			
			// 查询时间段的实际新增量和时间段的实际已刷的留存量记录
//			TargetApp app2 = new TargetApp();
//			app2.setAppId(4);
//			Date date = new Date();
//			date = dbCenter.convertToBeginOfDay(new Date(date.getTime()-3600*1000*24));
//			int newQualityByDay = dbCenter.getNewQualityByDay(date, new Date(date.getTime()+3600*1000*24-1), app2);
//			System.out.println("newQualityByDay:"+newQualityByDay);
//			int remainByDay = dbCenter.getRemainByDay(date,  new Date(), app2);
//			System.out.println("remainByDay:"+remainByDay);
//			
//			// 查询今日应新增的量是多少
//			dbCenter.calculateTodayNewQuality(app2);
			
//			TargetApp app = new TargetApp();
//			app.setAppId(17);
//			for (int i = 0; i < 50; i++) {
//				dbCenter.addWave(app, new Date());
//			}
			
			Date today = new Date();
			Date firstInstTime = new Date(today.getTime()-3600*1000*24*1);
			Date beginOfDay = dbCenter.convertToBeginOfDay(firstInstTime);
			Date endOfDay = new Date(beginOfDay.getTime()+3600*1000*24-1);
			TargetApp app = new  TargetApp();
			app.setAppId(1);
//			int remainByDay = dbCenter.getRemainByDay(beginOfDay, endOfDay, firstInstTime, app);
			int remainByDay1 = dbCenter.getModelRemainQualityByDay(beginOfDay, endOfDay, firstInstTime, app, 1);
			System.out.println("reainbyday:"+remainByDay1);
			ArrayList<String> noModelDeviceNames = dbCenter.getNoModelDeviceNames(new Date(), app);
			System.out.println("namescount:"+noModelDeviceNames.size());
			dbCenter.closeDB();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			dbCenter.closeDB();
		}
	}
	
	private DBCenter(){
		initDB();
	}
	
	public static DBCenter getInstance(){
		if (instance == null) {
			instance = new DBCenter();
		}
		return instance;
	}
	
	private void initDB() {
		try {
			Connection connection = openDB(null);
			if (connection != null) {
				// statement用来执行SQL语句
				Statement statement = connection.createStatement();
				// 要执行的SQL语句
				String sql = "create database if not exists "+ DB_NAME;
				  statement.executeUpdate(sql);
			}
			 closeDB();
			connection = openDB(DB_NAME);
			if (connection != null) {
				Statement statement = connection.createStatement();
				String sqlCreateTable1 = "create table if not exists "+ TABLE_APPS+" "
						+ "(app_id int auto_increment not null,"
						+ "name varchar(30) not null,"
						+ "pkg_name varchar(40) not null,"
						+ "version_name varchar(10),"
						+ "version_code int not null,"
						+ "brush_rate varchar(800),"
						+ "max_new_quality varchar(400),"
						+ "inject_file_path varchar(200),"
						+ "base_vm_name varchar(200),"
						+ "model_ids varchar(50),"
						+ "primary key(app_id,name))";
				String sqlCreateTable2 = "create table if not exists "+ TABLE_BRUSHES+" "
						+ "(id int PRIMARY KEY auto_increment not null,"
						+ "time DATETIME,"
						+ "app_id int not null,"
						+ "new_quality bigint DEFAULT 0,"
						+ "remain_quality bigint DEFAULT 0,"
						+ "vir_device_name varchar(200),"
						+ "first_inst_time DATETIME not null,"
						+ "device_imei varchar(30) not null,"
						+ "brushed_models VARCHAR(50),"
						+ "foreign key(app_id) references "+TABLE_APPS+"(app_id) "
						+ "ON DELETE CASCADE "
						+ "ON UPDATE CASCADE)";
				String sqlCreateTable3 = "create table if not exists "+ TABLE_WAVE+" "
						+ "(id int PRIMARY KEY auto_increment not null,"
						+ "time DATETIME,"
						+ "app_id int not null,"
						+ "wave float DEFAULT 0,"
						+ "foreign key(app_id) references "+TABLE_APPS+"(app_id) "
						+ "ON DELETE CASCADE "
						+ "ON UPDATE CASCADE)";
				String sqlCreateTable4 = "create table if not exists "+ TABLE_NEW_DEVICE+" "
						+ "(id int PRIMARY KEY auto_increment not null,"
						+ "app_id int not null,"
						+ "new_device varchar(200),"
						+ "foreign key(app_id) references "+TABLE_APPS+"(app_id) "
						+ "ON DELETE CASCADE "
						+ "ON UPDATE CASCADE)";
				String sqlCreateTable5 = "create table if not exists "+ TABLE_MODEL_RATE+" "
						+ "(id int PRIMARY KEY auto_increment not null,"
						+ "app_id int not null,"
						+ "name varchar(100),"
						+ "checked boolean,"
						+ "brush_rate varchar(800),"
						+ "inject_file_path varchar(200),"
						+ "foreign key(app_id) references "+TABLE_APPS+"(app_id) "
						+ "ON DELETE CASCADE "
						+ "ON UPDATE CASCADE)";
				String sqlCreateTable6 = "create table if not exists "+ TABLE_DB_VERSION+" "
						+ "(version_code int)";
				  statement.executeUpdate(sqlCreateTable1);
				  statement.executeUpdate(sqlCreateTable2);
				  statement.executeUpdate(sqlCreateTable3);
				  statement.executeUpdate(sqlCreateTable4);
				  statement.executeUpdate(sqlCreateTable5);
				  statement.executeUpdate(sqlCreateTable6);
				 ResultSet executeQuery = statement.executeQuery("select * from "+TABLE_DB_VERSION);
				 if (!executeQuery.next()) {
					 statement.executeUpdate("insert into "+TABLE_DB_VERSION+"(version_code) values(1)");
				}
				updateDB();
			}
			
			
			// 结果集
//			ResultSet rs = statement.executeQuery(sql);
			
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
//			rs.close();  
			closeDB();
		}
	}
	
	public void updateDB() throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		ResultSet executeQuery = statement.executeQuery("select * from "+TABLE_DB_VERSION);
		int versionCode = 0;
		if (executeQuery.next()) {
			versionCode = executeQuery.getInt(1);
		}
		switch (versionCode) {
		case 1:
			System.out.println("updating db to version 2");
			String sqlUpdate1 = "alter table "+ TABLE_APPS +" add column uid_percent float default 0";
			String sqlUpdateDBto2 = "update "+TABLE_DB_VERSION +" set version_code=2";
			statement.executeUpdate(sqlUpdate1);
			statement.executeUpdate(sqlUpdateDBto2);
		}
		
	}
	
	public Connection openDB(String databaseName) throws ClassNotFoundException, SQLException {
		if (conn != null ) {
			return conn;
		}
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名
		// MySQL配置时的用户名
		String user = "root";
		// Java连接MySQL配置时的密码
		String password = "root";
		String dbUrl = DB_URL;
		if (databaseName != null) {
			dbUrl = DB_URL + databaseName;
		}
		conn = null;
			// 加载驱动程序
			Class.forName(driver);
			// 连续数据库
			conn = DriverManager.getConnection(dbUrl, user, password);
			if (!conn.isClosed()){
				System.out.println("Succeeded connecting to the Database!");
				return conn;
			}else
				return null;
		
	}
	
	public void closeDB(){
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
				statement = null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 
	/**
	 * 数据库读取留存量  读取在时间（dateBegin~dateEnd）之间，某app的留存量是多少
	 * @param dateBegin
	 * @param dateEnd
	 * @param app
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getRemainByDay(Date dateBegin,Date dateEnd , TargetApp app) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateBeginStr = df.format(dateBegin);
		String dateEndStr   = df.format(dateEnd);
		String sql = "select sum(remain_quality) from "+TABLE_BRUSHES+" where time between '"+dateBeginStr+"' and '"+dateEndStr+"' and app_id = "+app.getAppId() ;
//		System.out.println("getNewQualityByDay sql: "+sql);
		ResultSet result = statement.executeQuery(sql);
		if (result.next()) {
			return result.getInt(1);
//			System.out.println("xxx"+array);
		}
		return -1;
	
	}
	
	
	
	// 
	/**
	 * 数据库读取新增量 ,读取在时间（dateBegin~dateEnd）之间，某app的新增量是多少
	 * @param dateBegin
	 * @param dateEnd
	 * @param app
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int getNewQualityByDay(Date dateBegin,Date dateEnd ,TargetApp app) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateBeginStr = df.format(dateBegin);
		String dateEndStr   = df.format(dateEnd);
		String sql = "select sum(new_quality) from "+TABLE_BRUSHES+" where time between '"+dateBeginStr+"' and '"+dateEndStr+"' and app_id = "+app.getAppId() ;
//		System.out.println("getNewQualityByDay sql: "+sql);
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			return result.getInt(1);
//			System.out.println("xxx"+array);
		}
		return 0;
	}
	
	
	/**
	 * 数据库读取某时间的 某日留存量，即该日（firstInstTime）的新增在某时间（dateBegin~Date dateEnd）的留存量
	 * @param dateBegin 统计开始的日期
	 * @param dateEnd   统计结束的日期
	 * @param firstInstTime 第一次激活的日期，
	 * @param app       统计的app
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
		public int getRemainByDay(Date dateBegin,Date dateEnd ,Date firstInstTime, TargetApp app) throws SQLException, ClassNotFoundException{
			if (conn == null || conn.isClosed()) {
				openDB(DB_NAME);
			}
			if (statement == null || statement.isClosed()) {
				statement = conn.createStatement();
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateBeginStr = df.format(dateBegin);
			String dateEndStr   = df.format(dateEnd);
			try {
				firstInstTime = convertToBeginOfDay(firstInstTime);
				String firstInstTimeStr = df.format(firstInstTime);
				Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
				String firstInstTimeStr2 = df.format(firstInstTime2);
				String sql = "select sum(remain_quality) from "+TABLE_BRUSHES+" where time between '"+dateBeginStr+"' and '"+dateEndStr
						+"' and app_id = "+app.getAppId()
						+" and first_inst_time between '"+firstInstTimeStr+"' and '"+firstInstTimeStr2+"'" ;
				System.out.println("getNewQualityByDay sql: "+sql);
				ResultSet result = statement.executeQuery(sql);
				if (result.next()) {
					return result.getInt(1);
//					System.out.println("xxx"+array);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		
		}

	public Date convertToBeginOfDay(Date firstInstTime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String firstInstTimeStr = df.format(firstInstTime);
		firstInstTimeStr = firstInstTimeStr.substring(0, 10)+" 00:00:00";
		return df.parse(firstInstTimeStr);
	}
	
	
		/**
		 * 数据库计算今日应刷某日（date）的留存量      从date开始算的之后的24小时
		 * @param date   要刷的日期
		 * @param app
		 * @return
		 * @throws SQLException
		 * @throws ClassNotFoundException
		 * @throws ParseException 
		 */
	public int calculateRemain(Date date,TargetApp app) throws SQLException, ClassNotFoundException, ParseException{
		// 计算间隔天数
		return calculateRemain(date, new Date(), app);
	}
	
	// 
	/**
	 * 数据库计算某日（calTime）应刷某日（date）的留存量     
	 * @param date   要刷的日期
	 * @param calTime   计算的日期
	 * @param app
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ParseException 
	 */
	public int calculateRemain(Date date,Date calTime,TargetApp app) throws SQLException, ClassNotFoundException, ParseException{
		calTime = convertToBeginOfDay(calTime);
		date = convertToBeginOfDay(date);
		// 计算间隔天数
		int day = (int) ((calTime.getTime() - date.getTime())/(1000*3600*24));
		if (day>0) {
			// 那天的新增数*那天到这天应该的留存率
			Date dateEnd = new Date(date.getTime()+1000*3600*24);
			int quality = getNewQualityByDay(date, dateEnd, app);
			ArrayList<Float> brushRate = app.getBrushRate();
			System.out.println(brushRate);
			float rate = brushRate.get(day-1);
			float wave = getWave(app, date);
			float newRate = (rate+wave);
			if (rate>-0.000001 && rate<0.000001) {
				newRate = 0.0f;
			}
//			System.out.println("old rate:"+rate);
//			System.out.println("new rate:"+newRate);
			return (int) (quality*newRate+0.5);
		}
		return 0;
	}
	
	// 数据库计算今日应增加的新增量
	public int calculateTodayNewQuality(TargetApp app) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			String sql = "select max_new_quality from "+ TABLE_APPS +" where app_id="+app.getAppId();
					   
			ResultSet resualt = statement.executeQuery(sql);
			if (resualt.next()) {
				String string = resualt.getString(1);
				String[] split = string.split(",");
				int day = new Date().getDay();
				if (day == 0) { // 周日
					return  Integer.parseInt(split[6].trim());
				}else{
					return Integer.parseInt(split[day-1].trim());
				}
			}
		return 0;
	}
	
	
	
	/**
	 * 数据库读取留存量 ,该日（firstInstTime）的新增 某模块（modelid）在某时间（dateBegin~Date dateEnd）的留存量
	 * @param dateBegin
	 * @param dateEnd
	 * @param app
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws ParseException 
	 */
	public int getModelRemainQualityByDay(Date dateBegin,Date dateEnd ,Date firstInstTime,TargetApp app,int modelID) throws SQLException, ClassNotFoundException, ParseException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateBeginStr = df.format(dateBegin);
		String dateEndStr   = df.format(dateEnd);
		firstInstTime = convertToBeginOfDay(firstInstTime);
		String firstInstTimeStr = df.format(firstInstTime);
		Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
		String firstInstTimeStr2 = df.format(firstInstTime2);
		String sql = "select count(*) from "+TABLE_BRUSHES+" where time between '"+dateBeginStr+"' and '"+dateEndStr+"' and app_id="+app.getAppId()+" and brushed_models like '%"+modelID+"%' and first_inst_time between '"+firstInstTimeStr+"' and '"+firstInstTimeStr2+"'" ;
//		System.out.println("getNewQualityByDay sql: "+sql);
		ResultSet result = statement.executeQuery(sql);
		if(result.next()) {
			return result.getInt(1);
//			System.out.println("xxx"+array);
		}
		return 0;
	}
	
	
	/**
	 * 数据库计算某日（calTime）应刷某日（date）某模块（modelID）的留存量     
	 * @param date
	 * @param calTime
	 * @param app
	 * @param modelId
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public int calculateModelRemain(Date date,Date calTime,TargetApp app,int modelId) throws ParseException, ClassNotFoundException, SQLException{
		calTime = convertToBeginOfDay(calTime);
		date = convertToBeginOfDay(date);
		// 计算间隔天数
		int day = (int) ((calTime.getTime() - date.getTime())/(1000*3600*24));
		if (day>=0) {
			// 那天的新增数*那天到这天应该的留存率
			Date dateEnd = new Date(date.getTime()+1000*3600*24-1);
			// 如果是今天，则按今天的新增算模块的新增
			int quality = 0;
			if (day==0) {
				quality = getNewQualityByDay(date, dateEnd, app);
			}else{
				// 如果不是今天，则按那天模块的实际新增算
				quality = getModelRemainQualityByDay(date, dateEnd, calTime, app, modelId);
			}
			List<Float> brushRate = DBCenter.getInstance().getModelRate(app, modelId);
			System.out.println(brushRate);
			//当间隔超过预设的留存天数， 预设为最小留存率+随机值
			Random rd = new Random();
			float rate = 0;
			float newRate = 0;
			if (brushRate.size()<=0) {
				rate = 1;
				newRate = 1;
			}else{
				rate = brushRate.get(brushRate.size()-1)+rd.nextInt(100)/10000.0f;
				if (day < brushRate.size()) {
					rate = brushRate.get(day);
				}
				float wave = getWave(app, date);
				newRate = (rate+wave);
				if (rate>-0.000001 && rate<0.000001) {
					newRate = 0.0f;
				}
			}
			
			
			
//			System.out.println("quality:"+quality);
//			System.out.println("old rate:"+rate);
//			System.out.println("new rate:"+newRate);
			return (int) (quality*newRate+0.5);
		}
		return 0;
	}
	
	
	
	public void addNewApp(TargetApp app) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			String brushRateStr = app.getBrushRate().toString();
			String maxNewQualityStr = app.getMaxNewQuality().toString();
			String sql = "insert into "+TABLE_APPS+"(name,pkg_name,version_name,version_code,brush_rate,max_new_quality,inject_file_path,base_vm_name,uid_percent) "
					+ "values('"+app.getAppName()+"',"
							+ "'"+app.getPkgName()+"',"
							+ "'"+app.getVersionName()+"',"
							+     app.getVersionCode()+","
							+ "'"+brushRateStr.substring(1, brushRateStr.length()-1)+"',"
							+ "'"+maxNewQualityStr.substring(1,maxNewQualityStr.length()-1)+"',"
							+ "'"+app.getInjectFile().getAbsolutePath().replaceAll("\\\\", "\\\\\\\\")+"',"
							+ "'"+app.getBaseVmName()+"',"
							+     app.getUIDPercent()+")";
					   
			statement.executeUpdate(sql);
	}
	
	public void deleteApp(TargetApp app) throws ClassNotFoundException, SQLException{
		if (conn != null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			String sql = "delete from "+TABLE_APPS+" where app_id="+app.getAppId();
					   
			statement.executeUpdate(sql);
	}
	
	/**
	 * 
	 * @param date 操作日期
	 * @param app  目标app
	 * @param firstInstTime  app第一次安装的时间
	 * @param newQuality     新增量
	 * @param remainQuality  留存量
	 * @param deviceName     虚拟机名称
	 * @param deviceImei    虚拟机imei
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void addBrushQuality(Date date,TargetApp app,Date firstInstTime,long newQuality, long remainQuality,String deviceName,String deviceImei,String brushedId) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			System.out.println(df.format(date));
			String sql = "insert into "+TABLE_BRUSHES+"(time,app_id,new_quality,remain_quality,vir_device_name,first_inst_time,device_imei,brushed_models) "
					+ "values('"+df.format(date)+"',"
							+ "'"+app.getAppId()+"',"
							+     newQuality+","
							+     remainQuality +","
							+ "'"+deviceName    +"',"
							+ "'"+df.format(firstInstTime)+"',"
							+ "'"+deviceImei+"',"
							+ "'"+brushedId+"')";
					   
			statement.executeUpdate(sql);
	}
	
	
	public ArrayList<TargetApp> getTargetAppList() throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		ArrayList<TargetApp> list = new ArrayList<>();
		String sql ="select * from "+TABLE_APPS;
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			TargetApp app = new TargetApp();
			app.setAppId(result.getLong(1));
			app.setAppName(result.getString(2));
			app.setPkgName(result.getString(3));
			app.setVersionName(result.getString(4));
			app.setVersionCode(result.getInt(5));
			String brushRateStr = result.getString(6);
			String[] split = brushRateStr.split(",");
			ArrayList<Float> brushRate = new ArrayList<>();
			for (int i = 0; i < split.length; i++) {
				brushRate.add(Float.parseFloat(split[i].trim()));
			}
			app.setBrushRate(brushRate);
			
			ArrayList<Integer> maxNewQuality = new ArrayList<>();
			String maxNewQualityStr = result.getString(7);
			String[] split2 = maxNewQualityStr.split(",");
			for (int i = 0; i < split2.length; i++) {
				maxNewQuality.add(Integer.parseInt(split2[i].trim()));
			}
			app.setMaxNewQuality(maxNewQuality);
			String injectFilePath = result.getString(8);
			
			try {
				File file = new File(injectFilePath);
				app.setInjectFile(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String baseVmName = result.getString(9);
			app.setBaseVmName(baseVmName);
			app.setModelIds(result.getString(10));
			app.setUIDPercent(result.getFloat(11));
			list.add(app);			
		}
		return list;
	}
	
	
	public void updateTargetApp(TargetApp app) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		// UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
		String brushRateStr = app.getBrushRate().toString();
		String maxNewQualityStr = app.getMaxNewQuality().toString();
		String sql = "update "+TABLE_APPS+" set "
					+ "name='"+app.getAppName()+"',"
					+ "pkg_name='"+app.getPkgName()+"',"
					+ "version_name='"+app.getVersionName()+"',"
					+ "version_code="+app.getVersionCode()+","
					+ "brush_rate='"+brushRateStr.substring(1, brushRateStr.length()-1)+"',"
					+ "max_new_quality='"+maxNewQualityStr.substring(1, maxNewQualityStr.length()-1)+"',"
					+ "inject_file_path='"+app.getInjectFile().getAbsolutePath().replaceAll("\\\\", "\\\\\\\\")+"',"
					+ "base_vm_name='"+app.getBaseVmName()+"',"
					+ "uid_percent="+app.getUIDPercent()+","
					+ "model_ids='"+app.getModelIds()+"' "
					+ "where app_id="+app.getAppId();
		System.out.println("updateTargetApp sql:"+sql);
		statement.executeUpdate(sql);
		
	}
	
	//------------------------------------------
	// table waves
	private void addWave(TargetApp app,Date date) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		Random random = new Random();
		int nextInt = random.nextInt(201)-100; // -100~100
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String sql = "insert into "+TABLE_WAVE+"(time,app_id,wave) "
					+ "values('"+df.format(date)+"',"
							+     app.getAppId()+","
							+     (nextInt*1.0f)/10000+")";
					   
			statement.executeUpdate(sql);
	}
	
	public float getWave(TargetApp app, Date date)
			throws ClassNotFoundException, SQLException {
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "select wave from " + TABLE_WAVE + " " + "where time='"
				+ df.format(date) + "' and app_id=" + app.getAppId();

		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			return resultSet.getFloat(1);
		}
		addWave(app, date);
		 resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			return resultSet.getFloat(1);
		}
		return 0.0f;
	}
	
	
	public BrushData isVirDeviceExist(String virDeviceName) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "select * from "+TABLE_BRUSHES+" where vir_device_name='"+virDeviceName+"' && new_quality=1";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			BrushData brushData = new BrushData();
			brushData.setFirstInstTime(resultSet.getTimestamp(7));
			brushData.setDeviceName(resultSet.getString(6));
			brushData.setDeviceImei(resultSet.getString(8));
			brushData.setBrushed_models(resultSet.getString(9));
			return brushData;
		}
		return null;
	}

	/**
	 * 从 firstInstTime这天的新增中取出所有不为空的deviceName
	 * @param firstInstTime
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public ArrayList<String> getUseableDeviceNames(Date firstInstTime,TargetApp app) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(firstInstTime);
		firstInstTime = convertToBeginOfDay(firstInstTime);
		Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
		String sql = "select vir_device_name from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and time=first_inst_time and vir_device_name is not null and "
				+ "time between '"+df.format(firstInstTime)+"' and '"+df.format(firstInstTime2)+ "'";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
		}
//		System.out.println("sql:"+sql);
//		System.out.println("getDeviceNames:"+list);
		return list;
	}
	
	/**
	 * 从 firstInstTime这天的新增中取出所有不为空的deviceName
	 * @param firstInstTime
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public ArrayList<BrushData> getUseableDevices(Date firstInstTime,TargetApp app) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<BrushData> list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(firstInstTime);
		firstInstTime = convertToBeginOfDay(firstInstTime);
		Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
		String sql = "select vir_device_name,brushed_models from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and time=first_inst_time and vir_device_name is not null and "
				+ "time between '"+df.format(firstInstTime)+"' and '"+df.format(firstInstTime2)+ "'";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			BrushData brushData = new BrushData();
			brushData.setDeviceName(resultSet.getString(1));
			brushData.setBrushed_models(resultSet.getString(2));
			list.add(brushData);
		}
//		System.out.println("sql:"+sql);
//		System.out.println("getDeviceNames:"+list);
		return list;
	}
	
	/**
	 * 从 firstInstTime这天的新增中取出所有刷过modelID 的deviceName
	 * @param firstInstTime
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public ArrayList<String> getModelDeviceNames(Date firstInstTime,TargetApp app,int modelID) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(firstInstTime);
		firstInstTime = convertToBeginOfDay(firstInstTime);
		Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
		String sql = "select vir_device_name from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and time=first_inst_time and vir_device_name is not null and "
				+ "time between '"+df.format(firstInstTime)+"' and '"+df.format(firstInstTime2)+ "' and brushed_models like '%"+modelID+"%'";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
		}
//		System.out.println("sql:"+sql);
//		System.out.println("getDeviceNames:"+list);
		return list;
	}
	
	/**
	 * 从 firstInstTime这天的新增中取出所有没刷过模块留存  的deviceName
	 * @param firstInstTime
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 */
	public ArrayList<String> getNoModelDeviceNames(Date firstInstTime,TargetApp app) throws ClassNotFoundException, SQLException, ParseException {
		ArrayList<String> list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(firstInstTime);
		firstInstTime = convertToBeginOfDay(firstInstTime);
		Date firstInstTime2 = new Date(firstInstTime.getTime()+3600*1000*24-1);
		String sql = "select vir_device_name from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and time=first_inst_time and vir_device_name is not null and "
				+ "time between '"+df.format(firstInstTime)+"' and '"+df.format(firstInstTime2)+ "' and brushed_models like '%-1%'";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			list.add(resultSet.getString(1));
		}
//		System.out.println("sql:"+sql);
//		System.out.println("getDeviceNames:"+list);
		return list;
	}
	
	/**
	 * 将留存数据库中的虚拟机名称设为null  代表该虚拟机已经不存在，已经从硬盘删除，只留下刷量记录做统计用
	 * @param virDeviceName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void setBrushDeviceToNull(String virDeviceName) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "update "+TABLE_BRUSHES+" set vir_device_name=null where vir_device_name='"+virDeviceName+"'";
		System.out.println("sql;"+sql);
		statement.executeUpdate(sql);
	}
	
	/**
	 * 计算某天（date）新增的app 中，虚拟机已经被删除的记录个数
	 * @param date
	 * @param app
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException 
	 */
	public int getDeletedDevices(Date date,TargetApp app) throws ClassNotFoundException, SQLException, ParseException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		Date beginOfDay = convertToBeginOfDay(date);
		Date endOfDay = new Date(beginOfDay.getTime()+3600*1000*24-1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select count(*) from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and first_inst_time between '"+df.format(beginOfDay)+"' and '"+df.format(endOfDay)+"' and vir_device_name is null";
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return 0;
	}

	/**
	 * 某天date  虚拟机是否启动过，新增或者留存
	 * @param curDeviceName
	 * @param date
	 * @param app
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean isVirDeviceBrushed(String curDeviceName, Date date,TargetApp app) throws ClassNotFoundException, SQLException, ParseException {
		// TODO Auto-generated method stub
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		Date beginOfDay = convertToBeginOfDay(date);
		Date endOfDay = new Date(beginOfDay.getTime()+3600*1000*24-1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select * from "+TABLE_BRUSHES+" where app_id="+app.getAppId()+" and time between '"+df.format(beginOfDay)+"' and '"+df.format(endOfDay)+"' and vir_device_name='"+curDeviceName+"'";
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			return true;
		}
		return false;
	}
	
	//=====================db newdevices
	/**
	 * 获取当前app的最新虚拟机名称
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public String getCurDeviceName(TargetApp app) throws SQLException, ClassNotFoundException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		
		String sql = "select * from "+TABLE_NEW_DEVICE+" where app_id="+app.getAppId();
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			return resultSet.getString(3);
		}
		return null;
	}
	
	/**
	 * 添加当前app最新的虚拟机名称
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	public void addCurDeviceName(TargetApp app,String deviceName) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		if (deviceName!=null) {
			String sql = "insert into "+TABLE_NEW_DEVICE+"(app_id,new_device) values("+app.getAppId()+",'"+deviceName+"')";
			statement.executeUpdate(sql);
		}
		
	}
	
	public void updateCurDeviceName(TargetApp app,String deviceName) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			String sql = "update "+TABLE_NEW_DEVICE+" set new_device='"+deviceName+"' where app_id="+app.getAppId();
			statement.executeUpdate(sql);
		
	}
	
	public void deleteCurDeviceName(TargetApp app) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
			String sql = "delete from "+TABLE_NEW_DEVICE+" where app_id="+app.getAppId();
			statement.executeUpdate(sql);
	}
	
	//=======================================brushmodel
	
	public List<BrushModel> getAllModels(TargetApp app) throws ClassNotFoundException, SQLException{
		ArrayList<BrushModel> list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "select * from "+TABLE_MODEL_RATE+" where app_id="+app.getAppId();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			BrushModel brushModel = new BrushModel();
			brushModel.setId(resultSet.getInt(1));
			brushModel.setChecked(resultSet.getBoolean(4));
			brushModel.setName(resultSet.getString(3));
			String rateStr = resultSet.getString(5);
			String[] split = rateStr.split(",");
			ArrayList<Float> rate = new ArrayList<>();
			for (int i = 0; i < split.length; i++) {
				if (split[i]!=null && !split[i].equals("")) {
					rate.add(Float.parseFloat(split[i]));
				}
			}
			brushModel.setBrushRate(rate);
			brushModel.setInjectFilePath(resultSet.getString(6));
			list.add(brushModel);
		}
		return list;
	}
	
	public List<Float> getModelRate(TargetApp app,int modelId) throws ClassNotFoundException, SQLException{
		ArrayList<Float > list = new ArrayList<>();
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "select brush_rate from "+TABLE_MODEL_RATE+" where app_id="+app.getAppId()+" and id="+modelId;
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			String rateStr = resultSet.getString(1);
			String[] split = rateStr.split(",");
			for (int i = 0; i < split.length; i++) {
				if (split[i]!=null && !"".equals(split[i])) {
					list.add(Float.parseFloat(split[i].trim()));
				}
			}
		}
		return list;
	}
	
	
	public String getModelInjectPath(TargetApp app,int modelId) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "select inject_file_path from "+TABLE_MODEL_RATE+" where app_id="+app.getAppId()+" and id="+modelId;
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			return resultSet.getString(1);
		}
		return null;
	}
	
	public void addNewModel(BrushModel brushModel,TargetApp app) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String brushRate = brushModel.getBrushRate().toString();
		brushRate = brushRate.replace("[", "").replace("]", "");
		String sql = "insert into "+TABLE_MODEL_RATE+"(app_id,name,checked,brush_rate,inject_file_path) "
				+"values("+app.getAppId()+","
						+ "'"+brushModel.getName()+"',"
						+ "0,"
						+ "'"+brushRate+"',"
						+ "'"+brushModel.getInjectFilePath()+"'"
						+ ")";
		statement.executeUpdate(sql);
	}

	public void deleteModel(BrushModel model, TargetApp app) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		String sql = "delete from "+TABLE_MODEL_RATE+" where app_id="+app.getAppId()+" and id="+model.getId();
		statement.executeUpdate(sql);
	}
	
	public void updateModel(BrushModel brushModel) throws ClassNotFoundException, SQLException{
		if (conn == null || conn.isClosed()) {
			openDB(DB_NAME);
		}
		if (statement == null || statement.isClosed()) {
			statement = conn.createStatement();
		}
		int isChecked = brushModel.getChecked() ? 1:0;
		String injectFilePath = brushModel.getInjectFilePath();
		String path = null;
		if (injectFilePath!=null) {
			path = injectFilePath.replaceAll("\\\\", "\\\\\\\\");
		}
		
		String sql = "update "+TABLE_MODEL_RATE+" set "
				+ "name='"+brushModel.getName()+"',"
				+ "checked="+isChecked + ","
				+ "brush_rate='"+brushModel.getBrushRate().toString().replace("[", "").replace("]", "")+"',"
				+ "inject_file_path='"+path+"'"
				+ " where id="+brushModel.getId();
		System.out.println("sql:"+sql);
		statement.executeUpdate(sql);
	}
}	
