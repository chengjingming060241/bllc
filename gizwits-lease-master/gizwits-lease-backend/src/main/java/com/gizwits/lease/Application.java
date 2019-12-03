package com.gizwits.lease;

import com.gizwits.boot.base.Constants;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.listener.ApplicationListenerReady;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.HttpRequestUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.config.bean.ProductUpdateNettyConfigEvent;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan("com.gizwits")
@EnableAutoConfiguration
@MapperScan(basePackages = {"com.gizwits.lease.*.dao"})
@RestController()
public class Application  extends SpringBootServletInitializer {

	protected final static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.addListeners(new ApplicationListenerReady());
		app.setBannerMode(Banner.Mode.OFF);
			app.run(args);
		logger.info("机智云租赁平台'运营系统服务端'启动成功,自由的玩耍吧!");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	@ApiImplicitParam(paramType = "header", name = Constants.TOKEN_HEADER_NAME)
	@ApiOperation(value = "Netty重启", notes = "Netty重启", consumes = "application/json")
	@GetMapping(value = "/noti/restart")
	public ResponseObject restartNetty(){
		String host = SysConfigUtils.get(CommonSystemConfig.class).getNettyHostWithPort();
		if (!host.endsWith("/")){
			host += "/";
		}
		host += "restart";
		logger.info(HttpRequestUtils.get(host,new HashMap<>()));
		return new ResponseObject("200","请求成功,正在重启中....");
	}
}