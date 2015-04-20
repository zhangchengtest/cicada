package com.cicada.web.velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.tools.view.ViewToolContext;

/**
 * Velocity模板上用于控制缓存的指令
 * 
 * @author zhangcheng
 */
public class CacheDirective extends Directive {

	@Override
	public String getName() {
		return "cache";
	} // 指定指令的名称

	@Override
	public int getType() {
		return BLOCK;
	} // 指定指令类型为块指令

	@Override
	public boolean render(InternalContextAdapter context, Writer writer,
			Node node) throws IOException, ResourceNotFoundException,
			ParseErrorException, MethodInvocationException {
		ViewToolContext viewToolContext = (ViewToolContext) context
				.getInternalUserContext();
		HttpServletRequest request = viewToolContext.getRequest();
		HttpSession session = request.getSession();

		// 获得缓存信息
		SimpleNode sn_key = (SimpleNode) node.jjtGetChild(0);
		String key = (String) sn_key.value(context);
		
		SimpleNode sn_isUpdate = (SimpleNode) node.jjtGetChild(1);
		int isUpdate = 0;
		if(sn_isUpdate != null)
		{
			isUpdate = NumberUtils.toInt(sn_isUpdate.value(context)+ "");
		}

		Node body = node.jjtGetChild(2);
		String cache_html = (String) session.getAttribute(key);
		if (cache_html == null || isUpdate == 1) {
			StringWriter sw = new StringWriter();
			boolean isSuccess = body.render(context, sw);
			if (isSuccess) {
				cache_html = sw.toString();
				session.setAttribute(key, cache_html);
			}
		}
		writer.write(cache_html);
		return true;
	}
}