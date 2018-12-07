package com.digital.dance.service.starter;

import org.springframework.context.support.ClassPathXmlApplicationContext;




/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 
 * </pre>
 * @author 开发者
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DubboStart {
    
    public static void init() throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath*:**/framework-identity-context.xml" });
        
        context.start();
    }
}
