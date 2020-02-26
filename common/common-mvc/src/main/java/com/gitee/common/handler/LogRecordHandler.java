package com.gitee.common.handler;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.gitee.common.annotation.LogRecord;
import com.gitee.common.model.OperateLog;
import com.gitee.common.properties.LogProperties;
import com.gitee.common.security.util.SecurityUtils;
import com.gitee.common.security.vo.SsoUserVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 注解@within(com.gitee.common.annotation.LogRecord)
 * 表示拦截含有这个注解的类中所有方法
 * 注解@annotation(com.gitee.common.annotation.LogRecord)
 * 表示拦截含有这个注解的方法
 * -----------这里其实可以把注解写成内部类的形式,这样的话,只需要引入一个类,就可以直接使用了-----------
 *
 * @author xuhang
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
//@PropertySource("classpath:bootstarp.yml")
//@ConfigurationProperties(prefix = "spring.application")
//@EnableBinding(Processor.class)
public class LogRecordHandler {

//    private final Processor processor;

	/**
	 * 切入点签名,切入点表达式,可以指定包范围,可以指定注解,还可以两个一起用
	 */
//    @Pointcut(value = "@annotation(com.gitee.common.annotation.LogRecord)")
	@Pointcut(value = "execution(public * com.gitee.*.controller.*.*(..))")
	public void logRecordPointcut() {
	}

//    @Value("${abc}")
//    String name;
//    @Autowired
//    private LogProperties logProperties;

	/**
	 * 这里只用了环绕切的方式,如有需要,可另行添加其他的方式
	 *
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "logRecordPointcut()")
	@Transactional(rollbackFor = Exception.class)
	public Object logRecordAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println(1);
		//获取签名
		Signature signature = joinPoint.getSignature();
		//判断签名是否属性指定的签名类型--->这里我只处理方法的签名(注解也只允许在方法上注解)
		if (signature instanceof MethodSignature) {
			// 如果这里报错,由最终的拦截器拦截异常 统一推送至ELK或者别的日志记录方式.
			SsoUserVO user = SecurityUtils.getUser();
			//获取方法签名
			Method method = ((MethodSignature) signature).getMethod();
			//获取方法上的自定义注解
			LogRecord logRecord = method.getAnnotation(LogRecord.class);
			if (logRecord == null) {
				//获取类上的注解
				logRecord = method.getDeclaringClass().getAnnotation(LogRecord.class);
			}
			if (logRecord != null) {
				//获取注解的参数值
				//操作内容,操作模块
				String operateContent = logRecord.operateContent();
				String operateModule = logRecord.operateModule().getContent();

				//拷贝对应属性值到日志实体对象
				OperateLog operateLog = new OperateLog();
				operateLog.setFullName(user.getUsername());
				operateLog.setUsername(user.getUsername());
				operateLog.setUserId(user.getUserId());
				operateLog.setOperateContent(operateContent);
				operateLog.setOperateModule(operateModule);
				operateLog.setServiceName("123");

				operateLog.setOrgCode(null);

				//1.直接保存到数据库,其实这里应该是互斥的,有一种方式处理日志数据即可
				operateLog.insert();
			}

			Class<?> targetClass = joinPoint.getTarget().getClass();
			String methodPath = targetClass.getName() + "." + method.getName();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String uri = request.getRequestURI();

			System.out.println(user.getUsername());
			System.out.println(methodPath);
			System.out.println(uri);
			System.out.println(LogProperties.getServiceName());
//            System.out.println(context.getApplicationName());
//            System.out.println((String)SpringContextHolder.getBean("spring.application.name"));

			//2.推送到kafka等 中间件(有其他服务处理)
//            boolean send = processor.output()
//                    .send(MessageBuilder.withPayload(JSONObject.toJSONString(operateLog)).build());
			boolean send = false;
			//输出日志
			LogRecordHandler.log.info(send ? "推送操作日志到kafka成功" : "推送操作日志到kafka失败");
		}
		//继续执行下一个通知或目标方法调用,参数是Object[]类型的
		return joinPoint.proceed();
	}
}