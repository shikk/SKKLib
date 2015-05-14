package com.lonever.skklib;
/**
 * Copyright 2009-2012 Wap3.cn, Ltd. All rights reserved.
 */


/**
 * Singleton全局服务类的生成工厂类<br/>
 * 必须在应用的第一个Activity的onCreate()方法的最前面初始化，如下 <blockquote>
 * 
 * <pre>
 * SingletonAppContext singletonAppContext = SingletonAppContextBuilder.getInstance();
 * singletonAppContext.init(getApplicationContext());
 * </pre>
 * 
 * </blockquote> 如果需要数据库，请接下来初始化一下数据库 <blockquote>
 * 
 * <pre>
 * {@link com.lonever.skklib.db.DbInfo DbInfo} dbInfo = new {@link com.lonever.skklib.db.DbInfo DbInfo}();
 * ...... 设置数据库相关信息 ......
 * {@link com.lonever.skklib.db.DbProvider DbProvider} dbProvider = new {@link com.lonever.skklib.db.DbProvider DbProvider}(singletonAppContext.getApplicationContext(), dbInfo);
 * singletonAppContext.initDb(dbProvider);
 * </pre>
 * 
 * </blockquote> 在自己的应用中，请用继承自{@link com.lonever.skklib.db.DbProvider DbProvider}
 * 的类替换上面的DbProvider<br/>
 * <br/>
 * 在自己的应用中，如果已扩展类{@link cn.wap3.SingletonAppContext SingletonAppContext}，<br/>
 * 请同时添加一个类，继承自本类，并重写方法{@link #getInstance getInstance}，返回上面的扩展类 <blockquote>
 * 
 * <pre>
 * public static SingletonAppContext getInstance() {
 * 	if (singletonAppContext == null) {
 * 		singletonAppContext = new MySingletonAppContext();
 * 	}
 * 	return singletonAppContext;
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * @author Gary Fu
 */
public class SingletonAppContextBuilder {
	protected static SingletonAppContext singletonAppContext;

	/**
	 * 获得{@link cn.wap3.base.SingletonAppContext SingletonAppContext}服务类的唯一实例
	 * 
	 * @return 返回唯一实例
	 */
	public static SingletonAppContext getInstance() {
		if (singletonAppContext == null) {
			singletonAppContext = new SingletonAppContext();
		}

		return singletonAppContext;
	}
}
