# 1	技术选型
## 1.1	Web层
    a)	Servlet：前端控制器
    b)	html：视图
    c)	Filter：过滤器
    d)	BeanUtils：数据封装
    e)	Jackson：json序列化工具
## 1.2	Service层
    f)	Javamail：java发送邮件工具
    g)	Redis：nosql内存数据库
    h)	Jedis：java的redis客户端
## 1.3	Dao层
    i)	Mysql：数据库
    j)	Druid：数据库连接池
    k)	JdbcTemplate：jdbc的工具

# 2	创建数据库
    -- 创建数据库
    CREATE DATABASE travel;
    -- 使用数据库
    USE travel;
    --创建表
        复制提供好的sql

# 3 注册
## 3.1 前端
	表单校验
		register.html
	ajax 异步提交数据
		1、("#表单id").serialize() 方法获取表单数据，将表单中的数据转为（字符串格式）
## 3.2 后端
	RegistUserServlet
		1、校验验证码
		2、获取用户输入的数据 request.getParameterMap()
		3、封装对象
		4、调用service 查询
		5、响应结果，序列化为json
	UserService以及UserServiceImpl
		1、regist(User)
			a、根据用户名查询数据
			b、保存用户信息
	UserDao以及UserDaoImpl
		1、findByUsername(String username) 根据用户名查询数据
		2、void save(User user)
## 3.3 邮件激活
	1、邮件工具类：MailUtils，调用其中sendMail方法可以完成邮件发送
	2、修改保存Dao代码，加上存储status和code 的代码逻辑
	3、激活代码实现：ActiveUserServlet
	4、UserService：active
	5、UserDao：findByCode	updateStatus

# 4 登录
## 4.1 前端
	1、给登录按钮绑定单击事件
	2、发送ajax异步请求，提交表单数据
## 4.2 后端
	1、LoginServlet
		a、验证码校验
		b、用户信息校验
		c、将用户信息存入session缓存 session.setAttribute("user", u);用于修改index中欢迎回来部分
		d、响应信息
	2、UserService
		根据用户名和密码查询
	3、UserDao
## 4.3 index页面中用户姓名的提示信息功能
	从session缓存中获取user对象信心，替换span标签内容$("#spanid").text("替换的内容")

# 5 退出
	什么叫做登录了？session中有user对象。
	实现步骤：
		1.	访问servlet，将session销毁
		2.	跳转到登录页面
		
		//1.销毁session
		request.getSession().invalidate();
		
		//2.跳转登录页面(重定向)
		response.sendRedirect(request.getContextPath()+"/login.html");
# 6 封装Servlet
    获取访问路径的方法名，反射字节码；见 BaseServlet