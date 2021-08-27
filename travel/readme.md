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
    
# 7 从数据库中获取导航栏分类内容
    1、使用MySQL数据库
        1、后端：将从数据库中查询的数据序列化为json文件
        2、前端：ajax异步请求，将后端获取的json文件，以字符串的形式拼接
    2、优化，使用redis缓存，减少MySQL数据库的访问次数
        1、redis中没有数据，则从数据库中查询，并将数据保存到redis中
        2、如果有有数据，则直接返回
            此处使用sortset存储的结果，方法返回的是list，因此需要将set集合中的数据遍历存到list中
# 8、分页查询
	1、PageBean 类
	    private int totalCount; //总记录数 通过数据库查询
	    private int totalPage; //总记页数 计算
	    private int currentPage; //当前页码 ； 客户端提交
	    private int pageSize; //每页显示的条数 ；客户端提交
		private List<T> list; //每页显示的数据集合
	2、分页查询 limit start, pageSize
		start 分页的起始位置 = (currentPage - 1)*pageSize
	3、location.search; 获取连接中?后面的值  ?cid=4
	4、每次展示的页码前5后4
	5、ajax 异步获取后台封装的json数据
	6、request.getParameter("参数名"); 获取前端传入参数
	7、window.scrollTo(0,0); 定位到页面的位置
	
# 9、搜索查询
    1、获取用户输入的关键词进行模糊查询
    2、拼接地址
    3、Dao中，通过多条件拼接sql查询
    4、修改分页查询
    5、逻辑判断修改导航栏激活状态
    6、location.pathname，获取URL路径，通过字符串分割获取当前页面
# 10、路线详细信息
    1、获取rid，客户端获取
    2、根据rid，查询tab_route表，获取一条路线的信息，返回route对象
    3、根据rid, 查询tab_route_img表，获取一条路线的图片，返回图片列表
    4、根据sid，查询tab_seller表，获取商家信息，返回seller对象
        1-4可以封装为一个route对象。
    5、根据cid（从route对象中获取），查询tab_categroy表，获取分类名字cname，返回字符串
        单独一个方法
    6、RouteServlet中定义findOne方法,将两个对象存储在map中，然后序列化为json数据，回写给客户端
    
# 11、实现按enter键搜索
    //实现按enter时搜索
    $(document).keydown(function (event) {
       if(event.keyCode == 13){
           $('#search-button').triggerHandler('click');
       }
    });