import com.codegen.generator.CodeGenerator;
import com.codegen.jdbc.DBConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GenCode {

	public static void main(String[] args) throws UnknownHostException {
		CodeGenerator codegen = new CodeGenerator();

		DBConfiguration dbConfig = new DBConfiguration();
		dbConfig.setDatabaseType("mysql");
		dbConfig.setDriverClass("com.mysql.jdbc.Driver");
		//本地库
//		dbConfig.setDbUrl("jdbc:mysql://localhost:3306/auth-cert?useUnicode=true&characterEncoding=UTF-8");
//		dbConfig.setUsername("root");
//		dbConfig.setPassword("password11");
		//体验库
		dbConfig.setDbUrl("jdbc:mysql://192.168.147.18:3306/auth-cert?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		dbConfig.setUsername("root");
		dbConfig.setPassword("123456");


		codegen.setDbConfiguration(dbConfig);
		
		//生成代码本地存放路径
		codegen.getConfig().setTargetBasePath("e:\\gencode");
		//代码生成作者
		codegen.getConfig().setAuthor(InetAddress.getLocalHost().getHostName().toString());
		//代码生成版本
		codegen.getConfig().setCodeVersion("0.0.1");
		//数据库表名前缀, 设置后生成的Java对象会忽略该前缀; 
		//采用三段式表名时(项目简称_模块名_表名), 可以去掉项目简称部分;非三段式表名可不用设置
		codegen.getConfig().setTablePrefix("sgai");
		//服务端Java代码包基础路径, 配置到facade目录前一级
		codegen.getConfig().setBasePackage("com.sgai.services.auth");
		//服务端Java代码模块名, 如果设置，则生成服务端代码会在 controller、dao、model、service下建立以该模块名命名的子目录
		codegen.getConfig().setServiceMoudleName("su");
		//webapp端Java代码基础路径, 配置到controller目录前一级
		codegen.getConfig().setWebappBasePackage("com.sgai.webapp.auth");
		//webapp端Java代码模块名, 如果设置，则生成服务端代码会在 controller下建立以该模块名命名的子目录
		codegen.getConfig().setWebappMoudleName("su");
		//FeignClient 服务名变量
		codegen.getConfig().setFeignClientVariable("service.name.auth.cert");
		
		//调用生成代码方法, 参数: 表名, 表空间
		codegen.generateCode("SGAI_SU_SYNC_LOG", "auth-cert");
	}

}
