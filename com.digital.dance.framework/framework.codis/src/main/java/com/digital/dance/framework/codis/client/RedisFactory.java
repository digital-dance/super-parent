package com.digital.dance.framework.codis.client;

import com.digital.dance.framework.codis.Log;
import com.digital.dance.base.exception.ApplicationException;

import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisFactory implements InitializingBean, DisposableBean {
	private static final Log logger = new Log(RedisFactory.class);
	private JedisPool jedisPool;
	private String proxyHost;
	private Integer port;
	private JedisPoolConfig config;
	private String authpassword;
	private String subSysName;
	private int timeout;
	private static final int DEFAULT_TIMEOUT = 1000;

	//private int expireTime;

	//private JedisPool jedisPool;

	private String nodes;

	public RedisFactory() {
	}

	public RedisFactory(String host, String subSysName) {
		this.proxyHost = host;
		this.subSysName = subSysName;
	}

	public JedisPool getPool() {
		if (this.jedisPool == null) {
			synchronized (RedisFactory.class) {
				if (this.jedisPool == null) {
					initPool();
				}
			}
		}
		return this.jedisPool;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Jedis getResource() {
//		Jedis jedis = null;
//		try {
//			jedis = getPool().getResource();
//		} catch (Exception ex) {
//			logger.error("codis getResource() error.", ex);
//		}
		return getJedis();
	}

	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = getPool().getResource();
		} catch (JedisConnectionException e) {
			String message = StringUtils.trimWhitespace(e.getMessage());
			if("Could not get a resource from the pool".equalsIgnoreCase(message)){
				System.out.println("++++++++++请检查你的redis服务++++++++");
				System.out.println("|①.请检查是否安装redis服务，如果没安装，Windos 请参考Blog：http://www.sojson.com/blog/110.html|");
				System.out.println("|②.请检查redis 服务是否启动。启动口诀[安装目录中的redis-server.exe，双击即可，如果有错误，请用CMD方式启动，怎么启动百度，或者加QQ群。]|");
				System.out.println("|③.请检查redis启动是否带配置文件启动，也就是是否有密码，是否端口有变化（默认6379）。解决方案，参考第二点。如果需要配置密码和改变端口，请修改spring-cache.xml配置。|");
				System.out.println("|④.QQ群：259217951，目前需要付费，免费的方案请参考链接：http://www.sojson.com/shiro");

				System.out.println("|PS.如果对Redis表示排斥，请使用Ehcache版本：http://www.sojson.com/jc_shiro_ssm_ehcache.html");
				System.out.println("项目退出中....生产环境中，请删除这些东西。我来自。RedisFactory.java line:72");
				System.exit(0);//停止项目
			}
			//throw new JedisConnectionException(e);
			logger.error("codis getResource() error.", e);
		} catch (Exception e) {
			//throw new RuntimeException(e);
			logger.error("codis getResource() error.", e);
		}
		return jedis;
	}

	public void returnResource(Jedis redis) {
		try {

//			if (redis != null)
//				getPool().returnResource(redis);
			if (redis != null) {
				redis.close();
			}
		} catch (Exception ex) {
			logger.error("codis release error.", ex);

//			if (redis != null)
//				getPool().returnBrokenResource(redis);
			if (redis != null) {
				redis.close();
			}
		}
	}

	/**
	 * 释放
	 * @param jedis
	 * @param isBroken
	 */
	public void returnResource(Jedis jedis, boolean isBroken) {
		if (jedis == null)
			return;
//        if (isBroken)
//            J.getJedisPool().returnBrokenResource(jedis);
//        else
//        	J.getJedisPool().returnResource(jedis);
//        版本问题
		jedis.close();
	}

	private void initPool() {

		if (this.jedisPool == null) {
			if ( StringUtils.isEmpty(this.authpassword) ) {
				jedisPool = new JedisPool(config, proxyHost, port, timeout == 0 ? DEFAULT_TIMEOUT : timeout);
			} else if( "".equals(StringUtils.trimAllWhitespace(this.authpassword)) ) {
				jedisPool = new JedisPool(config, proxyHost, port, timeout == 0 ? DEFAULT_TIMEOUT : timeout);
			} else {
				jedisPool = new JedisPool(config, proxyHost, port, timeout == 0 ? DEFAULT_TIMEOUT : timeout, authpassword);
			}

		}
	}

	public void afterPropertiesSet() throws Exception {
		if ((this.proxyHost == null) || (this.port == null) || config == null) {
			throw new ApplicationException("this.host == null || this.port == null || this.port == config");
		}
		initPool();
		logger.debug("init codis connection jedisPool success after properties set.");
	}

	/**
	 * 集群模式
	 * @return
	 */
	public JedisCluster getJedisCluster() {

		String nodeListStr = getNodes();
		if(StringUtils.isEmpty(nodeListStr)){
			return null;
		} else if ("".equals(StringUtils.trimAllWhitespace(nodeListStr))){
			return null;
		}

		List<String> nodeList= Arrays.asList(nodeListStr.split(","));

		Set<HostAndPort> nodes = new HashSet<HostAndPort>();

		for (String n : nodeList){
			String[] ip = n.split(":");
			HostAndPort hostAndPort = new HostAndPort(ip[0], Integer.parseInt(ip[1]));
			nodes.add(hostAndPort);
		}

		if( nodes.size() < 1 ){
			return null;
		}
		JedisCluster jedisCluster = new JedisCluster(nodes, config);
		return jedisCluster;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setAuthpassword(String authpassword) {
		this.authpassword = authpassword;
	}

	public void setSubSysName(String subSysName) {
		this.subSysName = subSysName;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
	}

	@Override
	public void destroy() throws Exception {
		if (this.jedisPool != null) {
			if (!this.jedisPool.isClosed()) {
				this.jedisPool.close();
			}
			this.jedisPool.destroy();
		}
	}
}